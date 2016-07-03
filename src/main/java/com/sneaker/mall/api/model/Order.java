package com.sneaker.mall.api.model;

import java.util.Date;
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
public class Order extends BaseModel<Long> {

    /**
     * 订单项
     */
    private List<OrderItem> items;

    /**
     * 总订单号ID
     */
    private String super_order_id;

    /**
     * ERP订单号
     */
    private String erp_order_id;

    /**
     * 订单号
     */
    private String order_id;

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 总金额
     */
    private float total_amount;

    /**
     * 收货地址
     */
    private String receipt;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 县
     */
    private String county;

    /**
     * 状态
     */
    private int status;

    /**
     * 公司ID
     */
    private long cid;

    /**
     * 公司名称
     */
    private String company_name;

    /**
     * 更新时间
     */
    private Date update_time;

    /**
     * 供应商ID
     */
    private long scid;


    /**
     * 下单人ID
     */
    private long uid;

    /**
     * 备注信息
     */
    private String memo;

    /**
     * 是否支付
     */
    private int ispay;

    /**
     * 优惠金额
     */
    private float favorable;


    /**
     * 付款方式
     */
    private int pt;

    /**
     * 商品清单
     */
    private List<PayGateway.Glist> goodsList;

    public List<PayGateway.Glist> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<PayGateway.Glist> goodsList) {
        this.goodsList = goodsList;
    }

    public int getPt() {
        return pt;
    }

    public void setPt(int pt) {
        this.pt = pt;
    }

    public float getFavorable() {
        return favorable;
    }

    public void setFavorable(float favorable) {
        this.favorable = favorable;
    }

    public int getIspay() {
        return ispay;
    }

    public void setIspay(int ispay) {
        this.ispay = ispay;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    /**
     * 供应商公司名
     */
    private String supplier_company_name;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 送货紧急程序
     */
    private String delivery;

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 业务员 ID
     */
    private long suid;

    public long getSuid() {
        return suid;
    }

    public void setSuid(long suid) {
        this.suid = suid;
    }

    public String getErp_order_id() {
        return erp_order_id;
    }

    public void setErp_order_id(String erp_order_id) {
        this.erp_order_id = erp_order_id;
    }

    public String getSuper_order_id() {
        return super_order_id;
    }

    public void setSuper_order_id(String super_order_id) {
        this.super_order_id = super_order_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getSupplier_company_name() {
        return supplier_company_name;
    }

    public void setSupplier_company_name(String supplier_company_name) {
        this.supplier_company_name = supplier_company_name;
    }

    public long getScid() {
        return scid;
    }

    public void setScid(long scid) {
        this.scid = scid;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public float getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(float total_amount) {
        this.total_amount = total_amount;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
