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
public class Customer extends Company {
    /**
     * 业务员
     */
    private long suid;

    /**
     * 业务员名称
     */
    private String suname;

    /**
     * 客户类型
     */
    private String cctype;

    /**
     * 客户类型 展示用
     */
    private int type;

    private String name;
    /**
     * 仓库ID
     */
    private String sid;

    /**
     * 客户名称
     */
    private String ccname;

    /**
     * 客户id
     */
    private long ccid;

    /**
     * 客户拼音 展示用
     */
    private String pinyin;

    private String ccpyname;

    public String getCctype() {
        return cctype;
    }

    public void setCctype(String cctype) {
        this.cctype = cctype;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSuname() {
        return suname;
    }

    public void setSuname(String suname) {
        this.suname = suname;
    }

    public long getSuid() {
        return suid;
    }

    public void setSuid(long suid) {
        this.suid = suid;
    }

    public void setCcname(String ccname) {
        this.ccname = ccname;
    }

    public String getCcname() {
        return ccname;
    }

    public void setCcid(long ccid) {
        this.ccid = ccid;
    }

    public long getCcid() {
        return ccid;
    }

    public void setPinyin(String ccpyname) {
        this.pinyin = ccpyname;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCcpyname(String ccpyname) {
        this.ccpyname = ccpyname;
    }

    public String getCcpyname() {
        return ccpyname;
    }
}
