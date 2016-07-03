package com.sneaker.mall.api.service.impl;

import com.google.common.base.Preconditions;
import com.sneaker.mall.api.dao.admin.CompanySettingDao;
import com.sneaker.mall.api.model.CompanySetting;
import com.sneaker.mall.api.service.CompanySettingService;
import com.sneaker.mall.api.util.CONST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class B2CompanyServiceImpl implements CompanySettingService {

    @Autowired
    private CompanySettingDao companySettingDao;

    @Override
    public int getCompanyCateShowLayout(long cid) {
        Preconditions.checkArgument(cid > 0);
        int showLayout = 1;
        CompanySetting companySetting = this.companySettingDao.findComapnySettingByCidAndCkey(cid, CONST.COMPANY_CATE_SHOW_LAYOUT);
        if (companySetting != null) {
            showLayout = Integer.valueOf(companySetting.getCvalue());
        }
        return showLayout;
    }

    /**
     * 根据KEY获取VALUE
     *
     * @param key
     * @return
     */
    @Override
    public CompanySetting getCompanySettingByKey(String key) {
        Preconditions.checkNotNull(key);
        return this.companySettingDao.findCompanySettingByKey(key);
    }
}
