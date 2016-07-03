package com.sneaker.mall.api.service;

import com.sneaker.mall.api.model.Goods;
import com.sneaker.mall.api.model.ShopPrice;

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
public interface PriceService {

    /**
     * 补充商品价格信息
     *
     * @param goodses
     */
    public void supplementGoodsPrice(List<Goods> goodses, long cid);

    /**
     * 根据补充商品价格信息
     *
     * @param goodses
     */
    public void supplementGoodsPrice(List<Goods> goodses, long cid, long cctype);

    /**
     * 补充价格信息
     *
     * @param goodses
     * @param cid
     * @return
     */
    public Map<String, Float> supplementGoodsPriceReturnMap(List<Goods> goodses, long cid);

    /**
     * 根据客户类型,仓库ID,商品ID获取价格信息
     *
     * @param cctype
     * @param sid
     * @param mgid
     * @return
     */
    public ShopPrice getShopPriceByCcTypeAndSidAndMgid(long cid,int cctype, long sid, long mgid);
}
