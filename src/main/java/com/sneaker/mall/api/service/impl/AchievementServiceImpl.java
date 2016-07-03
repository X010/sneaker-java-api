package com.sneaker.mall.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.sneaker.mall.api.model.AchievementInfo;
import com.sneaker.mall.api.service.AchievementService;
import com.sneaker.mall.api.util.HttpClientUtil;
import com.sneaker.mall.api.util.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

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
public class AchievementServiceImpl implements AchievementService {

    private final static Logger logger = LoggerFactory.getLogger(AchievementServiceImpl.class);

    @Autowired
    @Qualifier("achievement_url")
    private String achievementUrl;

    @Override
    public AchievementInfo getSalemanAchievementInfo(long uid) {
        Preconditions.checkArgument(uid > 0);
        HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();
        Map<String, String> params = Maps.newHashMap();
        params.put("uid", String.valueOf(uid));

        try {
            logger.info("params:" + params);

            String res = httpClientUtil.getResponseBodyAsString(achievementUrl, params);
            logger.info("res:" + res);
            if (!Strings.isNullOrEmpty(res)) {
                JSONObject jsonObject = JSON.parseObject(res);
                if (jsonObject != null && jsonObject.getString("msg") != null) {
                    return (AchievementInfo) JsonParser.StringToJsonVideo(jsonObject.getString("msg"), AchievementInfo.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
