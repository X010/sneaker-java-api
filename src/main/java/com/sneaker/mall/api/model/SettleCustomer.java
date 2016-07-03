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
 * ERP结算单
 */
public class SettleCustomer extends BaseModel<Long> {

    /**
     * 公司ID
     */
    private long cid;

    /**
     * 公司名称
     */
    private String cname;

    /**
     * 客户ID
     */
    private long ccid;

    /**
     * 客户名称
     */
    private String ccname;

    /**
     * 单据列表，以逗号分隔
     */
    private String stock_list;

    /**
     * 操作员ID
     */
    private long uid;

    /**
     * 操作员名称
     */
    private String uname;

    /**
     * 审核员ID
     */
    private long cuid;

    /**
     * 审核员名称
     */
    private String cuname;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 最近更新时间
     */
    private Date updatetime;

    /**
     * 审核时间
     */
    private Date checktime;

    /**
     * 状态1-未审核 2-已审核 9-已取消 10-已冲正 11-冲正单
     */
    private int status;

    /**
     * 总金额
     */
    private long amount_price;

    /**
     * 总税额
     */
    private long tax_price;

    /**
     * 负单单号
     */
    private long negative_id;

    /**
     * 备注
     */
    private String memo;

    /**
     * 付款方式
     */
    private int pay_type;

    /**
     * 折扣金额
     */
    private long discount_price;

    /**
     * 1-线下支付 2-线上支付
     */
    private int settle_type;

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public long getCcid() {
        return ccid;
    }

    public void setCcid(long ccid) {
        this.ccid = ccid;
    }

    public String getCcname() {
        return ccname;
    }

    public void setCcname(String ccname) {
        this.ccname = ccname;
    }

    public String getStock_list() {
        return stock_list;
    }

    public void setStock_list(String stock_list) {
        this.stock_list = stock_list;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public long getCuid() {
        return cuid;
    }

    public void setCuid(long cuid) {
        this.cuid = cuid;
    }

    public String getCuname() {
        return cuname;
    }

    public void setCuname(String cuname) {
        this.cuname = cuname;
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

    public Date getChecktime() {
        return checktime;
    }

    public void setChecktime(Date checktime) {
        this.checktime = checktime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getAmount_price() {
        return amount_price;
    }

    public void setAmount_price(long amount_price) {
        this.amount_price = amount_price;
    }

    public long getTax_price() {
        return tax_price;
    }

    public void setTax_price(long tax_price) {
        this.tax_price = tax_price;
    }

    public long getNegative_id() {
        return negative_id;
    }

    public void setNegative_id(long negative_id) {
        this.negative_id = negative_id;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public long getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(long discount_price) {
        this.discount_price = discount_price;
    }

    public int getSettle_type() {
        return settle_type;
    }

    public void setSettle_type(int settle_type) {
        this.settle_type = settle_type;
    }
}
