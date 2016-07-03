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
public class Company extends BaseModel<Long> {

    private String pinyin;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 地址
     */
    private String address;

    /**
     * 电话
     */
    private String phone;

    /**
     * 联系人
     */
    private String contactor;

    /**
     * 联系电话
     */
    private String contactor_phone;

    /**
     * 公司状态
     */
    private int status;

    /**
     * 省
     */
    private String areapro;

    /**
     * 市
     */
    private String areacity;

    /**
     * 区或县
     */
    private String areazone;

    /**
     * 是否开通商成
     */
    private int ismall;

    private int type;

    private String typeStr;

    public int getIsmall() {
        return ismall;
    }

    public void setIsmall(int ismall) {
        this.ismall = ismall;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContactor() {
        return contactor;
    }

    public void setContactor(String contactor) {
        this.contactor = contactor;
    }

    public String getContactor_phone() {
        return contactor_phone;
    }

    public void setContactor_phone(String contactor_phone) {
        this.contactor_phone = contactor_phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public void setTypeStr(String str) {
        this.typeStr = str;
    }

    public String getTypeStr() {
        return this.typeStr;
    }
}
