package com.sneaker.mall.api.dao.admin;

import com.sneaker.mall.api.model.PayGateway;
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
 * 支付网关记录
 */
public interface PayGatewayDao {


    /**
     * 根据支付返回的PayGeteway
     *
     * @param erp_id
     * @return
     */
    @Select("select * from db_gathering where erp_id=#{erp_id}")
    public PayGateway findPayGatewayByPayId(@Param("erp_id") String erp_id);

    /**
     * 添加PayGateway对象
     *
     * @param payGateway
     */
    @Insert("insert into db_gathering(erp_id,gateway_id,btype,createtime,updatetime,status,uid,cid,amount,suid,ccid,ccname,cname)" +
            "values(#{erp_id},#{gateway_id},#{btype},#{createtime},#{updatetime},#{status},#{uid},#{cid},#{amount},#{suid},#{ccid},#{ccname},#{cname})")
    public void insertPayGateway(PayGateway payGateway);

    /**
     * 更新PayGateWay对象
     *
     * @param payGateway
     */
    @Update("update db_gathering set gateway_id=#{gateway_id},btype=#{btype},updatetime=#{updatetime},status=#{status} where erp_id=#{erp_id}")
    public void updatePayGateway(PayGateway payGateway);


    /**
     * 根据UID获取PayGateway对像
     *
     * @param uid
     */
    @Select("select * from db_gathering where uid=#{uid} and status<>1 order by updatetime desc limit #{start},#{size}")
    public List<PayGateway> findPayGatewayByUid(@Param("uid") long uid, @Param("start") int start, @Param("size") int size);
}
