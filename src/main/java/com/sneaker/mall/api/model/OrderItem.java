package com.sneaker.mall.api.model;

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
public class OrderItem extends BaseModel<Long> {

    /**
     * 数量
     */
    private int total;

    /**
     * 商城商品ID
     */
    private long mgid;

    /**
     * 销营活动ID
     */
    private long market_id;

    /**
     * 活动名称
     */
    private String market_name;

    /**
     * 价格
     */
    private float price;

    /**
     * 商品条型码
     */
    private String gbarcode;

    /**
     * 商品
     */
    private String gname;

    /**
     * 商品编码
     */
    private String gcode;

    /**
     * 图片地址
     */
    private String gphoto;

    /**
     * 商品基础资料ID
     */
    private long gid;

    /**
     * 供应商ID
     */
    private long scid;

    /**
     * 订单ID
     */
    private String order_id;

    /**
     * 业务员ID
     */
    private long suid;

    /**
     * 备注
     */
    private String memo;

    /**
     * 总金额
     */
    private float total_amount;

    /**
     * 是否是赠送项
     */
    private int giveaway;

    /**
     * 打包商品的新商品ID
     */
    private long bindId;

    /**
     * 打包商品数量
     */
    private int bindTotal;

    public int getBindTotal() {
        return bindTotal;
    }

    public void setBindTotal(int bindTotal) {
        this.bindTotal = bindTotal;
    }

    public long getBindId() {
        return bindId;
    }

    public void setBindId(long bindId) {
        this.bindId = bindId;
    }

    public int getGiveaway() {
        return giveaway;
    }

    public void setGiveaway(int giveaway) {
        this.giveaway = giveaway;
    }

    public String getGphoto() {
        return gphoto;
    }

    public void setGphoto(String gphoto) {
        this.gphoto = gphoto;
    }

    public float getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(float total_amount) {
        this.total_amount = total_amount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public long getSuid() {
        return suid;
    }

    public void setSuid(long suid) {
        this.suid = suid;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public long getMarket_id() {
        return market_id;
    }

    public void setMarket_id(long market_id) {
        this.market_id = market_id;
    }

    public String getMarket_name() {
        return market_name;
    }

    public void setMarket_name(String market_name) {
        this.market_name = market_name;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGbarcode() {
        return gbarcode;
    }

    public void setGbarcode(String gbarcode) {
        this.gbarcode = gbarcode;
    }

    public String getGcode() {
        return gcode;
    }

    public void setGcode(String gcode) {
        this.gcode = gcode;
    }

    public long getGid() {
        return gid;
    }

    public void setGid(long gid) {
        this.gid = gid;
    }

    public long getScid() {
        return scid;
    }

    public void setScid(long scid) {
        this.scid = scid;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public long getMgid() {
        return mgid;
    }

    public void setMgid(long mgid) {
        this.mgid = mgid;
    }
}
