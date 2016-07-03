package com.sneaker.mall.api.model;

import com.alibaba.fastjson.annotation.JSONField;

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
public class User extends BaseModel<Long> {
    /**
     * 编码
     */
    private String code;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    @JSONField(serialize = false)
    private String password;

    /**
     * 名称
     */
    private String name;

    /**
     * 有权限的仓库ID
     */
    private String sids;

    /**
     * 权限组ID
     */
    private String rids;

    /**
     * 工种
     */
    private String worktype;

    /**
     * 电话
     */
    private String phone;


    /**
     * 是否是管理员
     */
    @JSONField(deserialize = true)
    private int admin;

    /**
     * 状态
     */
    @JSONField(deserialize = true)
    private int status;

    /**
     * 公司ID
     */
    private long cid;

    /**
     * 公司信息
     */
    private Company com;

    /**
     * 是否是业务员
     */
    private boolean saleman = false;

    /**
     * 用户登录ticket
     */
    private String ticket;

    /**
     * 图像地址
     */
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isSaleman() {
        return saleman;
    }

    public void setSaleman(boolean saleman) {
        this.saleman = saleman;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public Company getCom() {
        return com;
    }

    public void setCom(Company com) {
        this.com = com;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSids() {
        return sids;
    }

    public void setSids(String sids) {
        this.sids = sids;
    }

    public String getRids() {
        return rids;
    }

    public void setRids(String rids) {
        this.rids = rids;
    }

    public String getWorktype() {
        return worktype;
    }

    public void setWorktype(String worktype) {
        this.worktype = worktype;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getTicket() {
        return this.ticket;
    }
}
