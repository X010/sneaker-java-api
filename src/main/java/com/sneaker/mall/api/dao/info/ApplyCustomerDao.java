package com.sneaker.mall.api.dao.info;

import com.sneaker.mall.api.model.ApplyCustomer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
 * 申请客户
 */
public interface ApplyCustomerDao {


    /**
     * 添加申请客户的信息
     *
     * @param applyCustomer
     */
    @Insert("insert into o_customer_tmp(cid,suid,period,cname,province,country,city,street,address,contractor,phone,account,password," +
            "ctype,areapro,areacity,areazone,gtids,status,urgent,createtime,sid)values(#{cid},#{suid},#{period},#{cname},#{province},#{country},#{city}," +
            "#{street},#{address},#{contractor},#{phone},#{account},#{password},#{ctype},#{areapro},#{areacity},#{areazone},#{gtids},#{status},#{urgent},#{createtime},#{sid})")
    public void addApplyCustomer(ApplyCustomer applyCustomer);

    /**
     * 根据联系人电话查询对应的申请帐号信息
     *
     * @param phone
     * @return
     */
    @Select("select * from o_customer_tmp where phone=#{phone} order by id desc limit 1")
    public ApplyCustomer findApplyCustomerByCidAndPhone(@Param("phone") String phone);

    /**
     * 根据状态获取与业务ID获取申请客户的信息
     *
     * @return
     */
    @Select("select * from  o_customer_tmp where suid=#{suid} and (status=0 or status=2) order by id desc")
    public List<ApplyCustomer> findApplyCustomerByStatus(@Param("suid") long suid);

}
