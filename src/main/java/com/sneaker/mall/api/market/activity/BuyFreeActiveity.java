package com.sneaker.mall.api.market.activity;

import com.google.common.base.Strings;
import com.sneaker.mall.api.dao.admin.GoodsDao;
import com.sneaker.mall.api.model.*;
import com.sneaker.mall.api.service.GoodsService;
import com.sneaker.mall.api.util.CONST;
import com.sneaker.mall.api.util.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
 * 买赠策略
 */
@Component("buyFreeActiveity")
public class BuyFreeActiveity implements Activeity {

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private GoodsService goodsService;

    private final static Logger logger = LoggerFactory.getLogger(BuyFreeActiveity.class);

    @Override
    public void activeity(final Order order, Market market, User user, Customer customer, Goods goods, OrderItem orderItem) {

        if (order != null && market != null
                && user != null && customer != null
                && goods != null) {
            //判断活动是否还在有效范围内
            Date currentTime = new Date();
            if (market.getStart_time().getTime() <= currentTime.getTime() && market.getEnd_time().getTime() >= currentTime.getTime()) {
                String strategyJson = market.getStrategy();
                logger.info("stratey:" + strategyJson);
                if (!Strings.isNullOrEmpty(strategyJson)) {
                    if (order.getItems() == null) {
                        order.setItems(new ArrayList<>());
                    }
                    //从保存的数据中获取买赠的商品
                    List<GiveGoods> giveGoodses = JsonParser.parseArray(strategyJson, GiveGoods.class);
                    if (giveGoodses == null)
                        logger.info("market buyfree goods is null");
                    if (giveGoodses != null && giveGoodses.size() > 0) {
                        giveGoodses.stream().forEach(giveGoods -> {
                            try {
                                //将赠送商品转换成OrderItem
                                logger.info("add market goods:" + giveGoods.getGname());
                                Goods znGood = this.goodsDao.findGoodsByIdAndNoPublish(Long.valueOf(giveGoods.getMgid()));
                                if (znGood != null) {
                                    logger.info("buyFree:" + znGood.getGcode() + "," + znGood.getGname());
                                    OrderItem mainOrderItem = new OrderItem();
                                    mainOrderItem.setGbarcode(znGood.getBarcode());
                                    mainOrderItem.setGcode(znGood.getGcode());
                                    mainOrderItem.setGname(znGood.getGname());
                                    mainOrderItem.setGid(znGood.getGid());
                                    mainOrderItem.setGiveaway(CONST.MARKET_GIVEWAYA_GIVE);
                                    this.goodsService.genartePhoto(znGood);
                                    mainOrderItem.setGphoto(znGood.getGphoto());
                                    mainOrderItem.setTotal(Integer.valueOf(giveGoods.getTotal()) * orderItem.getTotal());
                                    mainOrderItem.setPrice(0);
                                    mainOrderItem.setTotal_amount(0);
                                    mainOrderItem.setMarket_id(market.getId());
                                    mainOrderItem.setMemo(goods.getGcode());
                                    mainOrderItem.setBindTotal(Integer.valueOf(giveGoods.getTotal()));
                                    mainOrderItem.setMgid(znGood.getId());
                                    mainOrderItem.setGid(znGood.getGid());
                                    order.getItems().add(mainOrderItem);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            }
        }
    }


    /**
     * 赠送商品对像列表
     */
    public static class GiveGoods {
        /**
         * 商品名称
         */
        private String gname;

        /**
         * 数量
         */
        private String total;

        /**
         * 商品ID
         */
        private String mgid;

        /**
         * 商品编码
         */
        private String gcode;

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getMgid() {
            return mgid;
        }

        public void setMgid(String mgid) {
            this.mgid = mgid;
        }

        public String getGcode() {
            return gcode;
        }

        public void setGcode(String gcode) {
            this.gcode = gcode;
        }
    }
}
