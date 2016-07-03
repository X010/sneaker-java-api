package com.sneaker.mall.api.task.impl;

import com.google.common.base.Strings;
import com.sneaker.mall.api.dao.info.GeoLocationDao;
import com.sneaker.mall.api.model.GeoLocation;
import com.sneaker.mall.api.task.Task;
import com.sneaker.mall.api.util.BaiduLocationUtil;
import com.sneaker.mall.api.util.Gps2BaiDu;
import com.sneaker.mall.api.util.geo.GeoBaidu;
import com.sneaker.mall.api.util.geo.GeoPoint;
import org.springframework.beans.factory.annotation.Autowired;

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
 * 将经维图信息转换成地图位置信息
 */
public class AnalysisGeoLocation extends Task {

    @Autowired
    private GeoLocationDao geoLocationDao;

    @Autowired
    private BaiduLocationUtil baiduLocationUtil;

    @Override
    public void run() {
        List<GeoLocation> geoLocations = this.geoLocationDao.findNoBaiduAddress();
        if (geoLocations != null && geoLocations.size() > 0) {
            //更新地址信息
            geoLocations.stream().forEach(geoLocation -> {
                String x = null;
                String y = null;
                String address = null;
                //转换成获取百度地址
                if (!Strings.isNullOrEmpty(geoLocation.getLatitude()) && !Strings.isNullOrEmpty(geoLocation.getLonggitude())) {
                    x = geoLocation.getLatitude();
                    y = geoLocation.getLonggitude();
                    if (x.length() > 9) x = x.substring(0, 9);
                    if (y.length() > 10) y = y.substring(0, 10);

                    GeoPoint geoPoint = Gps2BaiDu.getBaiduGeoPoint(x, y);
                    if (geoPoint != null) {
                        x = String.valueOf(geoPoint.getX());
                        y = String.valueOf(geoPoint.getY());
                        GeoBaidu geoBaidu = this.baiduLocationUtil.analysis(y, x);
                        if (geoBaidu == null) {
                            address = "服务器无法解析地址";
                        } else {
                            address = geoBaidu.getResult().getFormatted_address();
                        }
                    } else {
                        address = "无法将GPS数据转换为百度坐标";
                    }
                } else {
                    address = "地址无法解析";
                }

                this.geoLocationDao.updateBaiduAddress(address, geoLocation.getId(), x, y);
            });
        }
    }
}
