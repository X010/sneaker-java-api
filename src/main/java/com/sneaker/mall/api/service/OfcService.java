package com.sneaker.mall.api.service;

import com.sneaker.mall.api.model.Order;
import com.sneaker.mall.api.model.OrderItem;

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
 * 订单拆分服务
 */
public interface OfcService {
    /**
     * 对订单进行分离操作
     *
     * @param orderItems
     * @return
     */
    public Order separateOrder(List<OrderItem> orderItems);


}
