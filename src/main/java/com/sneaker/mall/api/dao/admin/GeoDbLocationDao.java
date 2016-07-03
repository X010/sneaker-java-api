package com.sneaker.mall.api.dao.admin;

import com.sneaker.mall.api.model.GeoLocation;
import org.apache.ibatis.annotations.Insert;

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
 * 地理位置信息
 */
public interface GeoDbLocationDao {

    /**
     * 添加地址位置信息
     * @param geoLocation
     */
    @Insert("insert into db_geolocation(uid,cid,ccid,latitude,longgitude,altitude,accuracy,altitudeAccuracy,heading,speed,timestamp,createtime)" +
            "values(#{uid},#{cid},#{ccid},#{latitude},#{longgitude},#{altitude},#{accuracy},#{altitudeAccuracy},#{heading},#{speed},#{timestamp},#{createtime})")
    public void insertGeoLocation(GeoLocation geoLocation);
}
