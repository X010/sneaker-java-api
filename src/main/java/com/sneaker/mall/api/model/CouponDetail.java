package com.sneaker.mall.api.model;

import java.util.Date;

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
 * 红包
 */
public class CouponDetail extends BaseModel<Long> {

    /**
     * 公司ID
     */
    private long company_id;

    /**
     * 红包父级ID
     */
    private long coupon_id;

    /**
     * 红包名称
     */
    private String coupon_name;

    /**
     * 领取订单ID
     */
    private String coupon_order_id;

    /**
     * 领取时间
     */
    private Date create_time;

    /**
     * 开始发放时间
     */
    private Date coupon_send_start;

    /**
     * 结束发放时间
     */
    private Date coupon_send_end;

    /**
     * 可使用开始时间
     */
    private Date coupon_use_start;

    /**
     * 可使用结束时间
     */
    private Date coupon_use_end;

    /**
     * 状态
     */
    private int status;

    public long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(long company_id) {
        this.company_id = company_id;
    }

    public long getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(long coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getCoupon_name() {
        return coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

    public String getCoupon_order_id() {
        return coupon_order_id;
    }

    public void setCoupon_order_id(String coupon_order_id) {
        this.coupon_order_id = coupon_order_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getCoupon_send_start() {
        return coupon_send_start;
    }

    public void setCoupon_send_start(Date coupon_send_start) {
        this.coupon_send_start = coupon_send_start;
    }

    public Date getCoupon_send_end() {
        return coupon_send_end;
    }

    public void setCoupon_send_end(Date coupon_send_end) {
        this.coupon_send_end = coupon_send_end;
    }

    public Date getCoupon_use_start() {
        return coupon_use_start;
    }

    public void setCoupon_use_start(Date coupon_use_start) {
        this.coupon_use_start = coupon_use_start;
    }

    public Date getCoupon_use_end() {
        return coupon_use_end;
    }

    public void setCoupon_use_end(Date coupon_use_end) {
        this.coupon_use_end = coupon_use_end;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
