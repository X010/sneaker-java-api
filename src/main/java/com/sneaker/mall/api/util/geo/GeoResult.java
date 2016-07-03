package com.sneaker.mall.api.util.geo;

import java.util.List;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class GeoResult {
    /**
     * 经度
     */
    private GeoLocation location;

    /**
     * 相关地址信息
     */
    private List<GeoPois> pois;

    /**
     * 详细信息
     */
    private String sematic_description;

    /**
     * 城市编码
     */
    private int cityCode;

    /**
     * 具体地置
     */
    private GeoAddressComponent addressComponent;

    /**
     * 商业
     */
    private String business;

    /**
     * 具体地址
     */
    private String formatted_address;

    public GeoLocation getLocation() {
        return location;
    }

    public void setLocation(GeoLocation location) {
        this.location = location;
    }

    public List<GeoPois> getPois() {
        return pois;
    }

    public void setPois(List<GeoPois> pois) {
        this.pois = pois;
    }

    public String getSematic_description() {
        return sematic_description;
    }

    public void setSematic_description(String sematic_description) {
        this.sematic_description = sematic_description;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public GeoAddressComponent getAddressComponent() {
        return addressComponent;
    }

    public void setAddressComponent(GeoAddressComponent addressComponent) {
        this.addressComponent = addressComponent;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }
}
