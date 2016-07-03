package com.sneaker.mall.api.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.sneaker.mall.api.dao.admin.GoodsDao;
import com.sneaker.mall.api.dao.info.CompanyDao;
import com.sneaker.mall.api.model.Customer;
import com.sneaker.mall.api.model.Goods;
import com.sneaker.mall.api.service.GoodsService;
import com.sneaker.mall.api.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class B2RecommendServiceImpl implements RecommendService {

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CompanyDao companyDao;

    @Override
    public List<Goods> recommendGoodsByGoodsId(long mgid, long cid, long ccid) {
        Preconditions.checkArgument(mgid > 0);
        final List<Goods> res = Lists.newArrayList();
        Goods goods = this.goodsDao.findGoodsById(mgid);
        if (goods != null) {
            List<Goods> goodses = null;
            Customer customer = this.companyDao.findCustomerByCompanyIdAndCoustomerCompanyId(goods.getCompany_id(), cid);
            if (customer != null) {
                goodses = this.goodsDao.findGoodsByCidAndCateid(goods.getCompany_id(), goods.getCateid(), customer.getCctype(), customer.getSid());
                goodses.stream().forEach(cur -> {
                    if (cur.getId() != mgid) {
                        res.add(cur);
                    }
                });
            }
        }
        if (res.size() > 0) {
            this.goodsService.paddingMarket(res, cid, ccid);
        }
        return res;
    }
}
