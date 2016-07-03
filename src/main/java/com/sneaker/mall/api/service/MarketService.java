package com.sneaker.mall.api.service;

import com.sneaker.mall.api.model.Market;

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
public interface MarketService {


    /**
     * 根据商品ID获取营销活动
     *
     * @param mgid
     * @param ccid
     * @param cid
     * @return
     */
    public List<Market> getGoodsMarketByMgid(long mgid, long ccid, long cid, String mids);
}
