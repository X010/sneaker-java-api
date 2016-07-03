package com.sneaker.mall.api.service.impl;

import com.google.common.base.Preconditions;
import com.sneaker.mall.api.model.Order;
import com.sneaker.mall.api.model.OrderItem;
import com.sneaker.mall.api.service.OfcService;
import com.sneaker.mall.api.util.OrderUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
 */
@Service
public class OfcServiceImpl implements OfcService {

    @Override
    public Order separateOrder(List<OrderItem> orderItems) {
        Preconditions.checkNotNull(orderItems);
        String super_order_id = OrderUtil.generateSuperOrderId();

        Order order = new Order();
        order.setSuper_order_id(super_order_id);
        order.setOrder_id(OrderUtil.generateOrderId());
        order.setItems(new ArrayList<>());
        order.setScid(orderItems.get(0).getScid());
        order.setSuid(orderItems.get(0).getSuid());


        return order;
    }

}
