package com.sneaker.mall.api.dao.admin;

import com.sneaker.mall.api.model.Addressee;
import org.apache.ibatis.annotations.Insert;
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
public interface AddresseeDao {

    /**
     * 添加收件人信息
     *
     * @param addressee
     */
    @Insert("insert into db_addressee(cid,province,city,county,street,contacts,phone,memo,status,def,uid,ccid,cfrom)values(#{cid},#{province},#{city},#{county},#{street},#{contacts}," +
            "#{phone},#{memo},#{status},#{def},#{uid},#{ccid},#{cfrom})")
    public void addAddress(Addressee addressee);

    /**
     * 更新收件人信息
     *
     * @param addressee
     */
    @Update("update db_addressee set province=#{province},city=#{city},county=#{county},street=#{street},contacts=#{contacts},phone=#{phone},memo=#{memo},status=#{status},def=#{def} where id=#{id}")
    public void updateAddress(Addressee addressee);

    /**
     * 根据公司查询收件人信息
     *
     * @param cid
     * @return
     */
    @Select("select * from db_addressee where status=1 and  cid=#{cid}")
    public List<Addressee> findAddressByCid(@Param("cid") long cid);

    /**
     * 根据用户查询收件人信息
     *
     * @param uid
     * @return
     */
    @Select("select * from db_addressee where status=1 and  uid=#{uid}")
    public List<Addressee> findAddresseeByUid(@Param("uid") long uid);

    /**
     * 根据用户和客户ID查询收件人信息
     *
     * @return
     */
    @Select("select * from db_addressee where status=1 and  uid=#{uid} and ccid=#{ccid}")
    public List<Addressee> findAddresseeByUidAndCcid(@Param("uid") long uid, @Param("ccid") long ccid);

    /**
     * 根据公司查询收件人信息
     *
     * @param cid
     * @return
     */
    @Select("select * from db_addressee where status=1  and def=1 and  cid=#{cid}")
    public List<Addressee> findAddressByCidAndDef(@Param("cid") long cid);

    /**
     * 根据ID获取收信息
     *
     * @param id
     * @return
     */
    @Select("select * from db_addressee where id=#{id}")
    public Addressee findAddresseeByid(@Param("id") long id);

    /**
     * 批量设置为非默认收货地址
     *
     * @param cid
     */
    @Update("update db_addressee set def=0 where cid=#{cid}")
    public void disableDefAddressee(@Param("cid") long cid);

}
