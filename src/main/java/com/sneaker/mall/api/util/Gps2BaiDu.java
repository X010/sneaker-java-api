package com.sneaker.mall.api.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.sneaker.mall.api.util.geo.GeoPoint;

import java.io.IOException;
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
public class Gps2BaiDu {

    private static String URL = "http://api.map.baidu.com/geoconv/v1/?coords=%s,%s&from=1&to=5&ak=6f7c8b781a1ede44b6b4fa27078c6dd0";

    /**
     * 将GPSl转换为百度坐标系
     *
     * @param x
     * @param y
     * @return
     */
    public static GeoPoint getBaiduGeoPoint(String x, String y) {
        Preconditions.checkNotNull(x);
        Preconditions.checkNotNull(y);

        String requestUrl = String.format(URL, y, x);
        HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();
        try {
            String response = httpClientUtil.getGetResponseAsString(requestUrl);
            if (!Strings.isNullOrEmpty(response)) {
                JSONObject jsonObject = JSON.parseObject(response);
                if (jsonObject != null) {
                    String result = jsonObject.getString("result");
                    if (!Strings.isNullOrEmpty(result)) {
                        List<GeoPoint> geoPointList = JsonParser.parseArray(result, GeoPoint.class);
                        if (geoPointList != null && geoPointList.size() > 0) return geoPointList.get(0);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
