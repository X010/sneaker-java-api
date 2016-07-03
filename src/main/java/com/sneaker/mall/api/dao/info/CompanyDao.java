package com.sneaker.mall.api.dao.info;

import com.sneaker.mall.api.model.Company;
import com.sneaker.mall.api.model.Customer;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
public interface CompanyDao {

    /**
     * 根据公司ID获取公司信息
     *
     * @param id
     * @return
     */
    @Select("select * from o_company where id=#{id}")
    public Company findCompanyById(@Param("id") long id);

    /**
     * 分页读取公司数据
     *
     * @param start
     * @param size
     * @return
     */
    @Select("select * from o_company where status=1 limit #{start},#{size}")
    public List<Company> findCompanyByPage(@Param("start") int start, @Param("size") int size);

    /**
     * 统计正常公司数
     *
     * @return
     */
    @Select("select count(1) from o_company where status=1")
    public int countCompany();

    /**
     * 根据ID获取供应商信息
     *
     * @param id
     * @return
     */
    @Select("select * from o_company where id in(select scid from r_supplier where  cid=#{id})")
    public List<Company> findSupplierByCompanyId(@Param("id") long id);

    /**
     * 根据公司ID获取客户列表信息
     *
     * @param id
     * @return
     */
    @Select("select * from o_company where id in(select ccid from r_customer where  cid=#{id})")
    public List<Company> findComstomerByCompanyId(@Param("id") long id);

    /**
     * 根据ID获取供应商信息
     *
     * @param id
     * @return
     */
    @Select("select * from o_company where id in(select cid from r_customer where  ccid=#{scid})")
    public List<Company> findSupllierByScid(@Param("scid") long id);

    /**
     * 根据公司ID与业务员ID获取客户列表信息
     *
     * @param company_id
     * @param user_id
     * @return
     */
    @Select("select * from o_company where id in(select ccid from r_customer_salesman where  cid=#{company_id} and suid=#{user_id})")
    public List<Company> findComstomerByCompanyIdAndSalemanId(@Param("company_id") long company_id, @Param("user_id") long user_id);

    /**
     * 根据公司ID与业务员ID获取客户列表信息 从关系中获取
     * @param company_id
     * @param user_id
     * @return
     */
    @Select("select * from r_customer where ccid in(select ccid from r_customer_salesman where  cid=#{company_id} and suid=#{user_id})")
    public List<Customer> findRComstomerByCompanyIdAndSalemanId(@Param("company_id") long company_id, @Param("user_id") long user_id);

    /**
     * 根据公司ID与业务员ID还有关键字搜索客户列表
     *
     * @param company_id
     * @param user_id
     * @param k
     * @return
     */
    @Select("select * from o_company where id in(select ccid from r_customer_salesman where  cid=#{company_id} and suid=#{user_id}) and name like #{k}")
    public List<Company> findComstomerByCompanyIdAndSalemanIdAndKeyword(@Param("company_id") long company_id
            , @Param("user_id") long user_id, @Param("k") String k);

    /**
     * 根据公司ID与业务员ID还有关键字搜索客户列表 从关系中获取
     *
     * @param company_id
     * @param user_id
     * @param k
     * @return
     */
    @Select("select * from r_customer where ccid in(select ccid from r_customer_salesman where  cid=#{company_id} and suid=#{user_id}) and ccname like #{k}")
    public List<Customer> findRComstomerByCompanyIdAndSalemanIdAndKeyword(@Param("company_id") long company_id
            , @Param("user_id") long user_id, @Param("k") String k);

    /**
     * 读取客户公司信息
     *
     * @param company_id
     * @param cus_com_id
     * @return
     */
    @Select("select * from r_customer where cid=#{cid} and ccid=#{cus_com_id}")
    public Customer findCustomerByCompanyIdAndCoustomerCompanyId(@Param("cid") long company_id, @Param("cus_com_id") long cus_com_id);

    /**
     * 更新公司信息
     *
     * @param company
     */
    @Update("update o_company set address=#{address},contactor=#{contactor},contactor_phone=#{contactor_phone}  where id=#{id}")
    public void updateCompany(Company company);
}
