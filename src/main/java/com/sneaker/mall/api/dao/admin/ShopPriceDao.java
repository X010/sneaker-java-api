package com.sneaker.mall.api.dao.admin;

import com.sneaker.mall.api.model.ShopPrice;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
public interface ShopPriceDao {


    /**
     * 根据客户类型,仓库ID,商品ID读取价格
     *
     * @param cctype
     * @param sid
     * @param mgid
     * @return
     */
    @Select("select * from db_shop_price where company_id=#{cid} and cctype=#{cctype} and sid=#{sid} and mgid=#{mgid}")
    public ShopPrice findShopPriceByCctypeAndSid(@Param("cid") long cid, @Param("cctype") int cctype, @Param("sid") long sid, @Param("mgid") long mgid);

}
