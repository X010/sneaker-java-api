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
 * ERP出库单
 */
public class StockOut extends BaseModel<Long> {

    /**
     * ERP订单号
     */
    private long order_id;

    /**
     * 出库公司ID
     */
    private long cid;

    /**
     * 出库公司名称
     */
    private String cname;

    /**
     * 仓库ID
     */
    private long sid;

    /**
     * 出库仓库名称
     */
    private String sname;

    /**
     * 客户公司ID
     */
    private long in_cid;

    /**
     * 客户公司名称
     */
    private String in_cname;

    /**
     * 总金额
     */
    private long amount;

    /**
     * 总税额
     */
    private long tax_amount;

    /**
     * 成本额
     */
    private long cost_amount;

    /**
     * 状态\n1-未审核\n2-已预审\n3-缺货待配\n4-已审核\n9-已取消\n10-已冲正\n11-冲正单\n12-已修正\n13-修正单
     */
    private int status;

    /**
     * 类型
     */
    private int type;

    /**
     * 操作员工ID
     */
    private long uid;

    /**
     * 操作员工名称
     */
    private String uname;

    /**
     * 预审员工ID
     */
    private long puid;

    /**
     * 预审员工名称
     */
    private String puname;

    /**
     * 审核员工ID
     */
    private long cuid;

    /**
     * 审核员工名称
     */
    private String cuname;

    /**
     * 复核员工ID
     */
    private long ruid;

    /**
     * 复核员工名称
     */
    private String runame;


    /**
     * 业务员ID
     */
    private long suid;

    /**
     * 业务员名称
     */
    private String suname;

    /**
     * 负单单号
     */
    private long negative_id;

    /**
     * 被修正单号
     */
    private long repaired_id;

    /**
     * 结算单号
     */
    private long settle_id;

    /**
     * 结算状态
     */
    private long settle_status;

    /**
     * 结算
     */
    private String memo;

    /**
     * 付款方式
     */
    private int pay_type;

    /**
     * 最近更新时间
     */
    private Date updatetime;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 审核时间
     */
    private Date checktime;

    /**
     * 结算时间
     */
    private Date settletime;

    /**
     * 最终结算日期
     */
    private Date lastdate;

    /**
     * 经营方式 1-经销 2-代销 3-联营 4-租赁
     */
    private int business;

    /**
     * 订单哈希，依据该值判断是否修改过
     */
    private String hash;

    /**
     * 分拣单ID
     */
    private long sorting_id;

    /**
     * 派车的车牌号
     */
    private String car_license;

    /**
     * 商城订单号
     */
    private String mall_orderno;

    /**
     * 商城收获地址
     */
    private String receipt;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 联系电话
     */
    private String phone;


    private List<PayGateway.Glist> goodsList;

    public List<PayGateway.Glist> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<PayGateway.Glist> goodsList) {
        this.goodsList = goodsList;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

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

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

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

    public long getCost_amount() {
        return cost_amount;
    }

    public void setCost_amount(long cost_amount) {
        this.cost_amount = cost_amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public long getPuid() {
        return puid;
    }

    public void setPuid(long puid) {
        this.puid = puid;
    }

    public String getPuname() {
        return puname;
    }

    public void setPuname(String puname) {
        this.puname = puname;
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

    public long getRuid() {
        return ruid;
    }

    public void setRuid(long ruid) {
        this.ruid = ruid;
    }

    public String getRuname() {
        return runame;
    }

    public void setRuname(String runame) {
        this.runame = runame;
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

    public long getNegative_id() {
        return negative_id;
    }

    public void setNegative_id(long negative_id) {
        this.negative_id = negative_id;
    }

    public long getRepaired_id() {
        return repaired_id;
    }

    public void setRepaired_id(long repaired_id) {
        this.repaired_id = repaired_id;
    }

    public long getSettle_id() {
        return settle_id;
    }

    public void setSettle_id(long settle_id) {
        this.settle_id = settle_id;
    }

    public long getSettle_status() {
        return settle_status;
    }

    public void setSettle_status(long settle_status) {
        this.settle_status = settle_status;
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

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getChecktime() {
        return checktime;
    }

    public void setChecktime(Date checktime) {
        this.checktime = checktime;
    }

    public Date getSettletime() {
        return settletime;
    }

    public void setSettletime(Date settletime) {
        this.settletime = settletime;
    }

    public Date getLastdate() {
        return lastdate;
    }

    public void setLastdate(Date lastdate) {
        this.lastdate = lastdate;
    }

    public int getBusiness() {
        return business;
    }

    public void setBusiness(int business) {
        this.business = business;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public long getSorting_id() {
        return sorting_id;
    }

    public void setSorting_id(long sorting_id) {
        this.sorting_id = sorting_id;
    }

    public String getCar_license() {
        return car_license;
    }

    public void setCar_license(String car_license) {
        this.car_license = car_license;
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
}
