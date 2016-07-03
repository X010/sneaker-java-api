package com.sneaker.mall.api.service;

import com.sneaker.mall.api.model.GeoLocation;

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
 * 地理位置信息服务
 */
public interface GeoLocationService {

    /**
     * 保存地址服务信息
     *
     * @param geoLocation
     */
    public void saveGeoLocation(GeoLocation geoLocation);

    /**
     * 根据UID获取用忘掉足迹点
     * @param uid
     * @return
     */
    public List<GeoLocation> findGeoLocationByUid(long uid);
}
