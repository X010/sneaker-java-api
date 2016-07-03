package com.sneaker.mall.api.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.sneaker.mall.api.dao.info.GeoLocationDao;
import com.sneaker.mall.api.model.GeoLocation;
import com.sneaker.mall.api.service.GeoLocationService;
import com.sneaker.mall.api.util.Gps2BaiDu;
import com.sneaker.mall.api.util.geo.GeoPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class GeoLocationServiceImpl implements GeoLocationService {

    @Autowired
    private GeoLocationDao geoLocationDao;

    @Override
    public void saveGeoLocation(GeoLocation geoLocation) {
        Preconditions.checkNotNull(geoLocation);
        //转换坐标系

        String x = geoLocation.getLatitude();
        String y = geoLocation.getLonggitude();
        if (!Strings.isNullOrEmpty(x) && !Strings.isNullOrEmpty(y)) {
            if (x.length() > 9) x = x.substring(0, 9);
            if (y.length() > 10) y = y.substring(0, 10);

            GeoPoint geoPoint = Gps2BaiDu.getBaiduGeoPoint(x, y);

            if (geoPoint != null) {
                x = String.valueOf(geoPoint.getX());
                y = String.valueOf(geoPoint.getY());
                geoLocation.setBaidu_longgitude(x);
                geoLocation.setBaidu_latitude(y);
            }
        }
        this.geoLocationDao.insertGeoLocation(geoLocation);
    }

    @Override
    public List<GeoLocation> findGeoLocationByUid(long uid) {
        Preconditions.checkArgument(uid > 0);
        return this.geoLocationDao.findGeoLocationByUid(uid);
    }
}
