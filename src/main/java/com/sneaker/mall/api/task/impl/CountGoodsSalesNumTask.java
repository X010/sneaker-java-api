package com.sneaker.mall.api.task.impl;

import com.google.common.collect.Maps;
import com.sneaker.mall.api.dao.admin.GoodsDao;
import com.sneaker.mall.api.model.Order;
import com.sneaker.mall.api.model.OrderItem;
import com.sneaker.mall.api.service.OrderService;
import com.sneaker.mall.api.task.Task;
import com.sneaker.mall.api.util.CONST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * 统计最近一个小时的商品销售情况并，并将出更新到商品列表中去
 */
public class CountGoodsSalesNumTask extends Task {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountGoodsSalesNumTask.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsDao goodsDao;


    @Override
    public void run() {
        LOGGER.info("count sales goods job start");
        Date end = new Date();
        Date start = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
        int count = this.orderService.countOrderByTimeAndStatusAndCompany(start, end, CONST.ORDER_STATUS_ERP_CHECK_STOUCKOUT);
        if (count > 0) {
            LOGGER.info("count orders count:" + count);
            int pages = 0;
            if (count % CONST.DEFAULT_PAGE_SIZE == 0) pages = count / CONST.DEFAULT_PAGE_SIZE;
            else pages = count / CONST.DEFAULT_PAGE_SIZE + 1;

            Map<Long, Integer> gidCounts = Maps.newHashMap();
            //读取订单数据
            for (int i = 0; i <= pages; i++) {
                List<Order> orders = this.orderService.getOrderByTimeAndStatus(start, end, CONST.ORDER_STATUS_ERP_CHECK_STOUCKOUT, i + 1, CONST.DEFAULT_PAGE_SIZE);
                if (orders != null) {
                    orders.stream().forEach(
                            order -> {
                                List<OrderItem> orderItems = order.getItems();
                                if (orderItems != null) {
                                    orderItems.forEach(orderItem -> {
                                        if (gidCounts.get(orderItem.getMgid()) == null) {
                                            gidCounts.put(orderItem.getMgid(), 0);
                                        }
                                        gidCounts.put(orderItem.getMgid(), gidCounts.get(orderItem.getMgid()) + 1);
                                    });
                                }
                            });
                }//end if
            }//end for

            //获取Key
            Set<Long> keys = gidCounts.keySet();
            for (long key : keys) {
                int saleNum = gidCounts.get(key);
                this.goodsDao.updateGoodsSalesNum(key, saleNum);
                LOGGER.info("update mgid:" + key + "  saleNum:" + saleNum);
            }
        }
        LOGGER.info("count sales goods job end");
    }
}
