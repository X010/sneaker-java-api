package com.sneaker.mall.api;

import com.sneaker.mall.api.util.HttpClientUtil;
import com.sneaker.mall.api.util.JsonParser;
import com.sneaker.mall.api.util.PayUtil;
import org.junit.Test;

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
public class HttpClientUntilUnitTest {


    @Test
    public void getWxResponseBodyJsonStringUnitTest() {
        HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();

        PayUtil.WxPostParam wxPostParam = new PayUtil.WxPostParam();
        wxPostParam.setOrderMoney("1");
        wxPostParam.setSpbill_create_ip("127.0.0.1");
        wxPostParam.setProduct_id("2020320303030");
        wxPostParam.setBody("出库单");

        try {
            String responseString = httpClientUtil.getResponseJsonBodyAsString("http://pay.ms9d.com/pay/wx", JsonParser.simpleJson(wxPostParam));
            System.out.println(responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAliResponseBodyJsonStringUnitTest() {
        HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();

        PayUtil.AliPostParam aliPostParam = new PayUtil.AliPostParam();
        aliPostParam.setBody("出库单");
        aliPostParam.setSpbill_create_ip("127.0.0.1");
        aliPostParam.setTotal_fee("1");
        aliPostParam.setProduct_id("2020320303030");
        aliPostParam.setSubject("出库单");

        try
        {
            String responseString=httpClientUtil.getResponseJsonBodyAsString("http://pay.ms9d.com/pay/ali",JsonParser.simpleJson(aliPostParam));
            System.out.println(responseString);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
