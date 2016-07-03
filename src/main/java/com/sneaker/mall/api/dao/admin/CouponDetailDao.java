package com.sneaker.mall.api.dao.admin;

import com.sneaker.mall.api.model.Coupon;
import com.sneaker.mall.api.model.CouponDetail;
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
public interface CouponDetailDao {

    /**
     * 根据ID 获取 红包信息
     *
     * @param id
     * @return
     */
    @Select("select * from db_coupon where id=#{id}")
    public Coupon getCouponById(@Param("id") long id);

    /**
     * 根据订单号查询红包详情
     *
     * @param order_no
     * @return
     */
    @Select("select * from db_coupon_detail where coupon_order_no=#{order_no}")
    public CouponDetail getCouponDetailByOrderNo(@Param("order_no") String order_no);

    /**
     * 更新详情红包
     *
     * @param couponDetail
     */
    @Update("update  db_coupon_detail set status=#{status}  where id=#{id}")
    public void updateCouponDetail(CouponDetail couponDetail);

    /**
     * 统计 锁定或未使用的记录数
     *
     * @return
     */
    @Select("select count(1) from db_coupon_detail where status=2 or status=3")
    public int countCouponDetail();

    /**
     * 分页获取红包详情列表
     * @param start
     * @param limit
     * @return
     */
    @Select("select * from db_coupon_detail where status=2 or status=3 limit #{start},#{limit}")
    public List<CouponDetail> getCouponDetailByPage(@Param("start") int start, @Param("limit") int limit);
}
