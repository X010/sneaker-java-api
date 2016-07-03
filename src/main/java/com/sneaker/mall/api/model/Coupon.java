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
public class Coupon extends BaseModel<Long> {
    /**
     * 红包名称
     */
    private String coupon_name;

    /**
     * 红包金额
     */
    private int coupon_money;

    /**
     * 最小订单金额
     */
    private int coupon_small_money;

    /**
     * 红包类型
     */
    private int coupon_type;

    /**
     * 红包开始放发时间
     */
    private Date coupon_send_start;

    /**
     * 红包开始结束时间
     */
    private Date coupon_send_end;

    /**
     * 红包使用开始时间
     */
    private Date coupon_use_start;

    /**
     * 红包使用结束时间
     */
    private Date coupon_use_end;

    /**
     * 公司ID
     */
    private long company_id;

    /**
     * 红包状态
     */
    private int coupon_status;

    public String getCoupon_name() {
        return coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

    public int getCoupon_money() {
        return coupon_money;
    }

    public void setCoupon_money(int coupon_money) {
        this.coupon_money = coupon_money;
    }

    public int getCoupon_small_money() {
        return coupon_small_money;
    }

    public void setCoupon_small_money(int coupon_small_money) {
        this.coupon_small_money = coupon_small_money;
    }

    public int getCoupon_type() {
        return coupon_type;
    }

    public void setCoupon_type(int coupon_type) {
        this.coupon_type = coupon_type;
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

    public long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(long company_id) {
        this.company_id = company_id;
    }

    public int getCoupon_status() {
        return coupon_status;
    }

    public void setCoupon_status(int coupon_status) {
        this.coupon_status = coupon_status;
    }

}
