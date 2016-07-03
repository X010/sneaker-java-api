package com.sneaker.mall.api.model;

import com.alibaba.fastjson.annotation.JSONField;

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
 * 申请开通客户对象
 */
public class ApplyCustomer extends BaseModel<Long> {
    /**
     * 公司ID
     */
    private long cid;

    /**
     * 业务员ID
     */
    private long suid;


    /**
     * 帐期
     */
    private int period;

    /**
     * 公司名称
     */
    private String cname;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String country;

    /**
     * 县
     */
    private String city;

    /**
     * 街道
     */
    private String street;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 联系人
     */
    private String contractor;

    /**
     * 联系人电话
     */
    private String phone;

    /**
     * 帐号
     */
    private String account;

    /**
     * 密码
     */
    @JSONField(deserialize = true)
    private String password;

    /**
     * 公司类型
     */
    private int ctype;

    /**
     * 经营省
     */
    private String areapro;

    /**
     * 经营市
     */
    private String areacity;

    /**
     * 的营县
     */
    private String areazone;

    /**
     * 经营范围
     */
    private String gtids;

    /**
     * 状态
     */
    private int status;

    /**
     * 是否加急
     */
    private int urgent;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 仓库ID
     */
    private long sid;

    /**
     * 备注
     */
    private String memo;

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public int getUrgent() {
        return urgent;
    }

    public void setUrgent(int urgent) {
        this.urgent = urgent;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getSuid() {
        return suid;
    }

    public void setSuid(long suid) {
        this.suid = suid;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCtype() {
        return ctype;
    }

    public void setCtype(int ctype) {
        this.ctype = ctype;
    }

    public String getAreapro() {
        return areapro;
    }

    public void setAreapro(String areapro) {
        this.areapro = areapro;
    }

    public String getAreacity() {
        return areacity;
    }

    public void setAreacity(String areacity) {
        this.areacity = areacity;
    }

    public String getAreazone() {
        return areazone;
    }

    public void setAreazone(String areazone) {
        this.areazone = areazone;
    }

    public String getGtids() {
        return gtids;
    }

    public void setGtids(String gtids) {
        this.gtids = gtids;
    }
}
