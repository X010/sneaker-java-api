package com.sneaker.mall.api.dao.info;

import com.sneaker.mall.api.model.GeoLocation;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
 * 地理位置信息
 */
public interface GeoLocationDao {

    /**
     * 添加地址位置信息
     *
     * @param geoLocation
     */
    @Insert("insert into o_geolocation(uid,cid,ccid,latitude,longgitude,altitude,accuracy,altitudeAccuracy,heading,speed,timestamp,createtime,source,memo,baidu_latitude,baidu_longgitude)" +
            "values(#{uid},#{cid},#{ccid},#{latitude},#{longgitude},#{altitude},#{accuracy},#{altitudeAccuracy},#{heading},#{speed},#{timestamp},#{createtime},#{source},#{memo},#{baidu_latitude},#{baidu_longgitude})")
    public void insertGeoLocation(GeoLocation geoLocation);


    /**
     * 获取没有解析为百度
     * @return
     */
    @Select("select * from o_geolocation where latitude  IS NOT NULL and longgitude  IS  NOT NULL and baidu_address IS NULL")
    public List<GeoLocation> findNoBaiduAddress();

    /**
     * 根据UID获取地置信息
     * @param uid
     * @return
     */
    @Select("select * from o_geolocation where uid=#{uid}")
    public List<GeoLocation> findGeoLocationByUid(@Param("uid") long uid);

    /**
     * 更新百度地图提供的地址
     * @param baiduAddress
     * @param id
     */
    @Update("update o_geolocation set baidu_address=#{baidu_address},baidu_latitude=#{x},baidu_longgitude=#{y} where id=#{id}")
    public void updateBaiduAddress(@Param("baidu_address")String baiduAddress,@Param("id") long id,@Param("x")String x,@Param("y")String y);
}
