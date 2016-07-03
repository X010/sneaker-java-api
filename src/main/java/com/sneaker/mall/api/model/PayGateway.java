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
 * 支付网关记录
 */
public class PayGateway extends BaseModel<Long> {
    /**
     * ERP中的单据号ID
     */
    private String erp_id;

    /**
     * 网关访问ID
     */
    private String gateway_id;

    /**
     * 调用网关方式,1是微信,2是支付宝,3是现金
     */
    private int btype;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 状态 1,表示创建,2表示支付成功,3表示通知ERP成功
     */
    private int status;

    /**
     * 用户ID
     */
    private long uid;

    /**
     * 公司ID
     */
    private long cid;

    /**
     * 金额
     */
    private long amount;

    /**
     * 业务员ID
     */
    private long suid;

    /**
     * 客户ID
     */
    private long ccid;

    /**
     * 客户名称
     */
    private String ccname;

    /**
     * 公司名称
     */
    private String cname;

    /**
     * ERP返回的错误编码
     */
    private String error;

    /**
     * 打零
     */
    private String small_amount;

    public String getSmall_amount() {
        return small_amount;
    }

    public void setSmall_amount(String small_amount) {
        this.small_amount = small_amount;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getSuid() {
        return suid;
    }

    public void setSuid(long suid) {
        this.suid = suid;
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

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public String getErp_id() {
        return erp_id;
    }

    public void setErp_id(String erp_id) {
        this.erp_id = erp_id;
    }

    public String getGateway_id() {
        return gateway_id;
    }

    public void setGateway_id(String gateway_id) {
        this.gateway_id = gateway_id;
    }

    public int getBtype() {
        return btype;
    }

    public void setBtype(int btype) {
        this.btype = btype;
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

    /**
     * 商品清单
     */
    private List<Glist> goodsList;

    public List<Glist> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Glist> goodsList) {
        this.goodsList = goodsList;
    }

    /**
     * 商品清单
     */
    public static class Glist {


        /**
         * 商品名称
         */
        private String gname;

        /**
         * 总价
         */
        private int total;

        /**
         * 总价
         */
        private float amount_price;

        /**
         * 单位
         */
        private String gunit;

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public float getAmount_price() {
            return amount_price;
        }

        public void setAmount_price(float amount_price) {
            this.amount_price = amount_price;
        }

        public String getGunit() {
            return gunit;
        }

        public void setGunit(String gunit) {
            this.gunit = gunit;
        }
    }
}
