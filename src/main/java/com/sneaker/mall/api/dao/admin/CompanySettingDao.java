package com.sneaker.mall.api.dao.admin;

import com.sneaker.mall.api.model.CompanySetting;
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
public interface CompanySettingDao {

    /**
     * 根据公司ID与Ckey获取Cvalue
     *
     * @return
     */
    @Select("select  * from db_company_setting where company_id=#{company_id} and ckey=#{ckey}")
    public CompanySetting findComapnySettingByCidAndCkey(@Param("company_id") long cid, @Param("ckey") String ckey);


    /**
     * 根据KEY获取Value
     * @return
     */
    @Select("select * from db_company_setting where ckey=#{ckey} limit 1")
    public CompanySetting findCompanySettingByKey(@Param("ckey") String key);
}
