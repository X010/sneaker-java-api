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
 */
public class GeoLocation extends BaseModel<BaseModel> {
    /**
     * 用户ID
     */
    private long uid;

    /**
     * 公司ID
     */
    private long cid;

    /**
     * 客户公司ID
     */
    private long ccid;

    /**
     * 维度
     */
    private String latitude;

    /**
     * 经度
     */
    private String longgitude;

    /**
     * 高度
     */
    private String altitude;

    /**
     * 精准度
     */
    private String accuracy;

    /**
     * 垂直高度
     */
    private String altitudeAccuracy;

    /**
     * 水平高度
     */
    private String heading;

    /**
     * 速度
     */
    private String speed;

    /**
     * 时间截
     */
    private String timestamp;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 来源
     */
    private int source;

    /**
     * 客户意见反馈
     */
    private String memo;

    /**
     * 百度返回的地址信息
     */
    private String baidu_address;

    /**
     * 百度经度
     */
    private String baidu_latitude;

    /**
     * 百度维度
     */
    private String baidu_longgitude;

    public String getBaidu_latitude() {
        return baidu_latitude;
    }

    public void setBaidu_latitude(String baidu_latitude) {
        this.baidu_latitude = baidu_latitude;
    }

    public String getBaidu_longgitude() {
        return baidu_longgitude;
    }

    public void setBaidu_longgitude(String baidu_longgitude) {
        this.baidu_longgitude = baidu_longgitude;
    }

    public String getBaidu_address() {
        return baidu_address;
    }

    public void setBaidu_address(String baidu_address) {
        this.baidu_address = baidu_address;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
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

    public long getCcid() {
        return ccid;
    }

    public void setCcid(long ccid) {
        this.ccid = ccid;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLonggitude() {
        return longgitude;
    }

    public void setLonggitude(String longgitude) {
        this.longgitude = longgitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getAltitudeAccuracy() {
        return altitudeAccuracy;
    }

    public void setAltitudeAccuracy(String altitudeAccuracy) {
        this.altitudeAccuracy = altitudeAccuracy;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
