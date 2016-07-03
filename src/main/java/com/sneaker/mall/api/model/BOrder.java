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
 * ERP系统的订单对像
 */
public class BOrder extends BaseModel<Long> {
    /**
     * 进货方公司ID
     */
    private long in_cid;

    /**
     * 进货方公司名
     */
    private String in_cname;

    /**
     * 进货方仓库ID
     */
    private long in_sid;

    /**
     * 进货方仓库名
     */
    private String in_sname;

    /**
     * 出货方公司ID
     */
    private long out_cid;

    /**
     * 出货方公司ID
     */
    private String out_cname;

    /**
     * 出货方仓库ID
     */
    private long out_sid;

    /**
     * 出货方仓库名
     */
    private String out_sname;

    /**
     * 订单类型\n1-采购订单\n2-退货订单\n3-调拨订单
     */
    private int type;

    /**
     * 订单总金额，单位分
     */
    private long amount;

    /**
     * 订单中税额，单位分
     */
    private long tax_amount;

    /**
     * 操作员工ID
     */
    private long uid;

    /**
     * 操作员名字
     */
    private String uname;

    /**
     * 审核员工ID
     */
    private long cuid;

    /**
     * 审核员名字
     */
    private String cuname;

    /**
     * 出库员工ID
     */
    private long ouid;

    /**
     * 出库员工名称
     */
    private String ouname;

    /**
     * 入库员工ID
     */
    private long iuid;

    /**
     * 入库员工名称
     */
    private String iuname;

    /**
     * 采购员ID
     */
    private long buid;

    /**
     * 采购员名称
     */
    private String buname;

    /**
     * 业务员ID
     */
    private long suid;

    /**
     * 业务员名称
     */
    private String suname;

    /**
     * 紧急程度（具体值参考系统配置）
     */
    private int rank;

    /**
     * 订单状态\n1-未审核\n2-已审核\n3-已结账\n9-已取消
     */
    private int status;

    /**
     * 备注
     */
    private String memo;

    /**
     * 支付方式
     */
    private int pay_type;

    /**
     * 是否已经支付 0-未支付 1-已支付
     */
    private int ispaid;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 最近更新日期
     */
    private Date updatetime;

    /**
     * 审核日期
     */
    private Date checktime;

    /**
     * 商城订单号
     */
    private String mall_orderno;

    /**
     * 商城收货地址
     */
    private String receipt;

    /**
     * 商城联系人姓名
     */
    private  String contacts;

    /**
     *商城联系人电话
     */
    private String phone;

    /**
     * 自动作废日期
     */
    private Date auto_delete_date;

    /**
     *经营方式 1-经销 2-代销 3-联营 4-租赁
     */
    private int business;

    /**
     * 客服回访备注
     */
    private String visit_memo;

    public long getIn_cid() {
        return in_cid;
    }

    public void setIn_cid(long in_cid) {
        this.in_cid = in_cid;
    }

    public String getIn_cname() {
        return in_cname;
    }

    public void setIn_cname(String in_cname) {
        this.in_cname = in_cname;
    }

    public long getIn_sid() {
        return in_sid;
    }

    public void setIn_sid(long in_sid) {
        this.in_sid = in_sid;
    }

    public String getIn_sname() {
        return in_sname;
    }

    public void setIn_sname(String in_sname) {
        this.in_sname = in_sname;
    }

    public long getOut_cid() {
        return out_cid;
    }

    public void setOut_cid(long out_cid) {
        this.out_cid = out_cid;
    }

    public String getOut_cname() {
        return out_cname;
    }

    public void setOut_cname(String out_cname) {
        this.out_cname = out_cname;
    }

    public long getOut_sid() {
        return out_sid;
    }

    public void setOut_sid(long out_sid) {
        this.out_sid = out_sid;
    }

    public String getOut_sname() {
        return out_sname;
    }

    public void setOut_sname(String out_sname) {
        this.out_sname = out_sname;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getTax_amount() {
        return tax_amount;
    }

    public void setTax_amount(long tax_amount) {
        this.tax_amount = tax_amount;
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

    public long getOuid() {
        return ouid;
    }

    public void setOuid(long ouid) {
        this.ouid = ouid;
    }

    public String getOuname() {
        return ouname;
    }

    public void setOuname(String ouname) {
        this.ouname = ouname;
    }

    public long getIuid() {
        return iuid;
    }

    public void setIuid(long iuid) {
        this.iuid = iuid;
    }

    public String getIuname() {
        return iuname;
    }

    public void setIuname(String iuname) {
        this.iuname = iuname;
    }

    public long getBuid() {
        return buid;
    }

    public void setBuid(long buid) {
        this.buid = buid;
    }

    public String getBuname() {
        return buname;
    }

    public void setBuname(String buname) {
        this.buname = buname;
    }

    public long getSuid() {
        return suid;
    }

    public void setSuid(long suid) {
        this.suid = suid;
    }

    public String getSuname() {
        return suname;
    }

    public void setSuname(String suname) {
        this.suname = suname;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public int getIspaid() {
        return ispaid;
    }

    public void setIspaid(int ispaid) {
        this.ispaid = ispaid;
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

    public String getMall_orderno() {
        return mall_orderno;
    }

    public void setMall_orderno(String mall_orderno) {
        this.mall_orderno = mall_orderno;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
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

    public Date getAuto_delete_date() {
        return auto_delete_date;
    }

    public void setAuto_delete_date(Date auto_delete_date) {
        this.auto_delete_date = auto_delete_date;
    }

    public int getBusiness() {
        return business;
    }

    public void setBusiness(int business) {
        this.business = business;
    }

    public String getVisit_memo() {
        return visit_memo;
    }

    public void setVisit_memo(String visit_memo) {
        this.visit_memo = visit_memo;
    }
}
