package com.sneaker.mall.api.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.sneaker.mall.api.dao.admin.MarketDao;
import com.sneaker.mall.api.model.Market;
import com.sneaker.mall.api.service.MarketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 */
@Service
public class B2MarketServiceImpl implements MarketService {

    @Autowired
    private MarketDao marketDao;

    @Autowired
    private B2CompanyService companyService;

    private static final Logger logger = LoggerFactory.getLogger(B2MarketServiceImpl.class);

    @Override
    public List<Market> getGoodsMarketByMgid(long mgid, long ccid, long cid, String mids) {
        Preconditions.checkArgument(mgid > 0);
        Preconditions.checkArgument(ccid > 0);
        Preconditions.checkArgument(cid > 0);
        if (!Strings.isNullOrEmpty(mids)) {
            String[] list = mids.split("\\,");
            List<String> ids = Lists.newArrayList();
            for (int i = 0; i < list.length; i++) {
                ids.add(list[i]);
            }
            //读取策略列表
            List<Market> markets = this.marketDao.getMarketByIds(ids, new Date());
            if (markets != null) {
                logger.info("search markets result:" + markets.size());
                return markets;
            }
        }
        return null;
    }
}
