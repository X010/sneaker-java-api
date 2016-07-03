package com.sneaker.mall.api.market.goods;

import com.google.common.collect.Lists;
import com.sneaker.mall.api.market.activity.Activeity;
import com.sneaker.mall.api.market.activity.ActiveityFactory;
import com.sneaker.mall.api.model.*;
import com.sneaker.mall.api.service.GoodsService;
import com.sneaker.mall.api.service.MarketService;
import com.sneaker.mall.api.service.PriceService;
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
@Component("originalGoods")
public class OriginalGoods implements Strategy {

    @Autowired
    private PriceService priceService;


    @Autowired
    private MarketService marketService;

    @Autowired
    private GoodsService goodsService;


    @Autowired
    private ActiveityFactory activeityFactory;

    private final static Logger logger = LoggerFactory.getLogger(BindGoods.class);

    @Override
    public Order perfect(long cid, long ccid, User user, Goods goods, Customer customer, OrderItem oldOrderItem, String memo, boolean issaleman) {
        List<OrderItem> orderItem = Lists.newArrayList();
        Order order = new Order();

        OrderItem mainOrderItem = new OrderItem();
        mainOrderItem.setGbarcode(goods.getBarcode());
        mainOrderItem.setGcode(goods.getGcode());
        mainOrderItem.setGname(goods.getGname());
        mainOrderItem.setGid(goods.getGid());
        mainOrderItem.setGiveaway(CONST.MARKET_GIVEWAYA_MAIN);
        //this.goodsService.genartePhoto(goods);
        mainOrderItem.setGphoto(goods.getGphoto());
        mainOrderItem.setMemo(goods.getGcode());
        //mainOrderItem.setMemo(memo);
        mainOrderItem.setTotal(oldOrderItem.getTotal());
        if (issaleman) {
            mainOrderItem.setSuid(user.getId());
        } else {
            mainOrderItem.setSuid(customer.getSuid());
        }
        mainOrderItem.setMgid(goods.getId());
        mainOrderItem.setGid(goods.getGid());
        mainOrderItem.setBindTotal(oldOrderItem.getTotal());
        //获取价格
        List<Goods> goodses = Lists.newArrayList();
        goodses.add(goods);
        Map<String, Float> prices = this.priceService.supplementGoodsPriceReturnMap(goodses, ccid);
        if (prices != null) {
            //检测是否有营销策略
            float price = prices.get(goods.getGcode());
            if (price > 0) {
                mainOrderItem.setPrice(price);
                mainOrderItem.setTotal_amount(price * mainOrderItem.getTotal());
                //将不是打包商品的订单号添加列表中去
                orderItem.add(mainOrderItem);
                order.setTotal_amount(price * mainOrderItem.getTotal());
            }
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

        order.getItems().addAll(orderItem);
        return order;
    }
}
