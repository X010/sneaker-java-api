package com.sneaker.mall.api.model;

import com.google.common.collect.Lists;
import com.sneaker.mall.api.annotation.NODBFeild;

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
public class Goods extends BaseModel<Long> {
    /**
     * 商品基础资料ID
     */
    private long gid;

    /**
     * 条型码
     */
    private String barcode;

    /**
     * 商品名称
     */
    private String gname;

    /**
     * 规格
     */
    private int spec;

    /**
     * 包装规格
     */
    private String pkgspec;

    /**
     * 瓶
     */
    private String unit;

    /**
     * 品牌ID
     */
    private long bid;

    /**
     * 品牌名称
     */
    private String bname;

    /**
     * 分类ID
     */
    private long tid;

    /**
     * 分类名称
     */
    private String tname;

    /**
     * 产地
     */
    private String place;

    /**
     * 厂家
     */
    private String factory;

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 更新时间
     */
    private Date update_time;

    /**
     * 商品编码
     */
    private String gcode;

    /**
     * 商品图片
     */
    private String gphoto;

    /**
     * 商品索引
     */
    private int gphoto_index;

    /**
     * 分类ID
     */
    private long cateid;

    /**
     * 公司ID
     */
    private long company_id;

    /**
     * 公司名称
     */
    private String company_name;

    /**
     * 价格
     */
    private float price;

    /**
     * 封面
     */
    private String cover;

    /**
     * 副标题
     */
    private String sale_name;

    /**
     * 描述
     */
    private String content;

    /**
     * 绑定商品,目前主要用于解决市场营销活动
     */
    private int isbind;

    /**
     * 是否为赠品
     */
    private int giveaway;

    /**
     * 个数
     */
    private int num;

    /**
     * 商城设定的价格
     */
    private float cprice;

    /**
     * 主商品
     */
    private List<Goods> mainGoods = Lists.newArrayList();

    /**
     * 赠品
     */
    private List<Goods> giveGoods = Lists.newArrayList();

    /**
     * 是否发布 0表示下线，1表示发布
     */
    private int publish;

    /**
     * 仓库ID，多个以逗号分开
     */
    private String sids;

    /**
     * 客户类型，多个以逗号分开
     */
    private String cctype;

    /**
     * 够买限制数
     */
    private int restrict;

    /**
     * 是否置顶
     */
    private int istop;

    /**
     * 销量
     */
    private int salesNum;

    /**
     * 置顶时间
     */
    private int top;

    /**
     * 是否显示 0表示显示,1表示不显示
     */
    private int flagdel;

    /**
     * 赠品修改的号码
     */
    private String title;

    /**
     * 是否大小包装
     */
    private int pkgSize;

    /**
     * 商品价格
     */
    private int shop_price;

    /**
     * 排序字段
     */
    private int order;

    /**
     * 营销活动ID
     */
    private String marketid;

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public String getMarketid() {
        return marketid;
    }

    public void setMarketid(String marketid) {
        this.marketid = marketid;
    }

    public int getPkgSize() {
        return pkgSize;
    }

    public void setPkgSize(int pkgSize) {
        this.pkgSize = pkgSize;
    }

    public int getShop_price() {
        return shop_price;
    }

    public void setShop_price(int shop_price) {
        this.shop_price = shop_price;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFlagdel() {
        return flagdel;
    }

    public void setFlagdel(int flagdel) {
        this.flagdel = flagdel;
    }

    public int getIstop() {
        return istop;
    }

    public void setIstop(int istop) {
        this.istop = istop;
    }

    public int getSalesNum() {
        return salesNum;
    }

    public void setSalesNum(int salesNum) {
        this.salesNum = salesNum;
    }

    public int getRestrict() {
        return restrict;
    }

    public void setRestrict(int restrict) {
        this.restrict = restrict;
    }

    /**
     * 营销相关信息文字
     */
    @NODBFeild
    private String marketText;

    public String getSids() {
        return sids;
    }

    public void setSids(String sids) {
        this.sids = sids;
    }

    public String getCctype() {
        return cctype;
    }

    public void setCctype(String cctype) {
        this.cctype = cctype;
    }

    public int getPublish() {
        return publish;
    }

    public void setPublish(int publish) {
        this.publish = publish;
    }

    public float getCprice() {
        return cprice;
    }

    public void setCprice(float cprice) {
        this.cprice = cprice;
    }

    public List<Goods> getMainGoods() {
        return mainGoods;
    }

    public void setMainGoods(List<Goods> mainGoods) {
        this.mainGoods = mainGoods;
    }

    public List<Goods> getGiveGoods() {
        return giveGoods;
    }

    public void setGiveGoods(List<Goods> giveGoods) {
        this.giveGoods = giveGoods;
    }

    public String getMarketText() {
        return marketText;
    }

    public void setMarketText(String marketText) {
        this.marketText = marketText;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getGiveaway() {
        return giveaway;
    }

    public void setGiveaway(int giveaway) {
        this.giveaway = giveaway;
    }

    public int getIsbind() {
        return isbind;
    }

    public void setIsbind(int isbind) {
        this.isbind = isbind;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSale_name() {
        return sale_name;
    }

    public void setSale_name(String sale_name) {
        this.sale_name = sale_name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public long getGid() {
        return gid;
    }

    public void setGid(long gid) {
        this.gid = gid;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public int getSpec() {
        return spec;
    }

    public void setSpec(int spec) {
        this.spec = spec;
    }

    public String getPkgspec() {
        return pkgspec;
    }

    public void setPkgspec(String pkgspec) {
        this.pkgspec = pkgspec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public long getBid() {
        return bid;
    }

    public void setBid(long bid) {
        this.bid = bid;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getGcode() {
        return gcode;
    }

    public void setGcode(String gcode) {
        this.gcode = gcode;
    }

    public String getGphoto() {
        return gphoto;
    }

    public void setGphoto(String gphoto) {
        this.gphoto = gphoto;
    }

    public int getGphoto_index() {
        return gphoto_index;
    }

    public void setGphoto_index(int gphoto_index) {
        this.gphoto_index = gphoto_index;
    }

    public long getCateid() {
        return cateid;
    }

    public void setCateid(long cateid) {
        this.cateid = cateid;
    }

    public long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(long company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
}
