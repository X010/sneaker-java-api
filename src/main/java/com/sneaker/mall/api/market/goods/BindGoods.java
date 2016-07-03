package com.sneaker.mall.api.market.goods;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.sneaker.mall.api.exception.ParameterException;
import com.sneaker.mall.api.market.activity.Activeity;
import com.sneaker.mall.api.market.activity.ActiveityFactory;
import com.sneaker.mall.api.model.*;
import com.sneaker.mall.api.service.GoodsService;
import com.sneaker.mall.api.service.MarketService;
import com.sneaker.mall.api.service.PriceService;
import com.sneaker.mall.api.service.impl.B2CompanyService;
import com.sneaker.mall.api.util.CONST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@Component("bindGoods")
public class BindGoods implements Strategy {

    @Autowired
    private PriceService priceService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private B2CompanyService companyService;

    @Autowired
    private MarketService marketService;

    @Autowired
    private ActiveityFactory activeityFactory;

    private final static Logger logger = LoggerFactory.getLogger(BindGoods.class);

    @Override
    public Order perfect(long cid, long ccid, User user, Goods goods, Customer customer, OrderItem oldOrderItem, String memo, boolean issaleman) {
        Order order = new Order();
        List<OrderItem> orderItems = Lists.newArrayList();

        //绑定商口
        //如果是打包商品
        String cctype = goods.getCctype();
        String sids = goods.getSids();
        boolean isCanShop = false;
        if (!Strings.isNullOrEmpty(cctype)) {
            if (("," + cctype + ",").indexOf("," + customer.getCctype() + ",") >= 0) {
                isCanShop = true;
            }
        }

        if (isCanShop) {


            //检测打包商品可以购买,检测里面的商品是否有下架了的,如果有下架的,则可以购买该商品
            List<Goods> giveGoods = this.goodsService.getChlidGoodsByMainGoodsId(goods.getId(), user.getCid(), ccid);

            Map<String, Float> oldPrice = this.priceService.supplementGoodsPriceReturnMap(giveGoods, ccid);

            //确认都能获取到原价
            if (giveGoods != null && oldPrice != null) {

                //进行限购判断
                oldOrderItem.setTotal(goods.getRestrict() <= 0
                        ? oldOrderItem.getTotal()
                        : (goods.getRestrict() > oldOrderItem.getTotal() ? oldOrderItem.getTotal() : goods.getRestrict()));


                if (giveGoods != null) {
                    //存在打包商品，开始换算打包商品
                    giveGoods.stream().forEach(bindGoods -> {
                        //将这些打包商品细化将转成订单项
                        OrderItem bindOrderItem = new OrderItem();
                        bindOrderItem.setScid(oldOrderItem.getScid());
                        bindOrderItem.setMgid(bindGoods.getId());
                        bindOrderItem.setBindId(oldOrderItem.getMgid());
                        int totalNum = bindGoods.getNum() * oldOrderItem.getTotal();
                        bindOrderItem.setBindTotal(oldOrderItem.getTotal());
                        bindOrderItem.setTotal(totalNum);
                        if (issaleman) {
                            bindOrderItem.setSuid(user.getId());
                        } else {
                            bindOrderItem.setSuid(customer.getSuid());
                        }
                        bindOrderItem.setGbarcode(bindGoods.getBarcode());
                        bindOrderItem.setGcode(bindGoods.getGcode());
                        bindOrderItem.setGname(bindGoods.getGname());
                        bindOrderItem.setGid(bindGoods.getGid());
                        bindOrderItem.setMemo(goods.getGcode());
                        bindOrderItem.setGiveaway(CONST.MARKET_GIVEWAYA_MAIN);
                        //this.goodsService.genartePhoto(bindGoods);
                        bindOrderItem.setGphoto(bindGoods.getGphoto());
                        bindOrderItem.setTotal_amount(oldPrice.get(bindOrderItem.getGcode()) * totalNum);
                        //bindOrderItem.setMemo(memo);
                        orderItems.add(bindOrderItem);

                        order.setTotal_amount(order.getTotal_amount() + bindOrderItem.getTotal_amount());
                    });
                }

                //读取总价
                //从Shop_Price里面读取价格
                ShopPrice shopPrice = this.priceService.getShopPriceByCcTypeAndSidAndMgid(cid
                        , Integer.valueOf(customer.getCctype())
                        , Long.valueOf(customer.getSid())
                        , oldOrderItem.getMgid());

                float totalPrice = shopPrice.getPrice() * oldOrderItem.getTotal();

                //分担总价格
                float preItemsAmount = 0; //防止分担总价后金额核对不上(分钱级别)
                float allAmount = totalPrice;
                int itemsSize = orderItems.size() - 1;
                int current = 0;
                for(OrderItem item : orderItems) {
                    Float price = ((item.getTotal_amount() / order.getTotal_amount() * totalPrice) * 100);
                    if (itemsSize == current) {
                        price = (allAmount - preItemsAmount);
                        item.setTotal_amount(price);
                    } else {
                        preItemsAmount += (price.intValue() / 100f);
                        item.setTotal_amount(price.intValue() / 100f);
                    }
                    orderItems.set(current, item);
                    current += 1;
                }

//                orderItems.stream().forEach(orderItem -> {
//                    Float price = ((orderItem.getTotal_amount() / order.getTotal_amount() * totalPrice) * 100);
//                    orderItem.setTotal_amount(price.intValue() / 100f);
//                });

                order.setTotal_amount(totalPrice);
            }
        } else {
            throw new ParameterException("该打包商品主商品已下架");
        }

        //针对商品类营销活动
        //是否有营销活动,如果有营销活动,则将商品投放营销活动策略中去
        logger.info("select market id:" + goods.getMarketid());
        List<Market> markets = this.marketService.getGoodsMarketByMgid(goods.getId(), ccid, cid, goods.getMarketid());
        if (markets != null) {
            //应用营销活动
            markets.stream().forEach(market -> {
                Activeity activeity = this.activeityFactory.getInstance(market.getMtype());

                activeity.activeity(order, market, user, customer, goods, oldOrderItem);
            });
        }


        if (order.getItems() == null) {
            order.setItems(new ArrayList<>());
        }

        order.getItems().addAll(orderItems);
        return order;
    }
}
