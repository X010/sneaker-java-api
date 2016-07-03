package com.sneaker.mall.api.api.saleman;

import com.google.common.base.Strings;
import com.sneaker.mall.api.model.CompanySetting;
import com.sneaker.mall.api.model.ResponseMessage;
import com.sneaker.mall.api.service.CompanySettingService;
import com.sneaker.mall.api.util.CONST;
import com.sneaker.mall.api.util.JsonParser;
import com.sneaker.mall.api.util.RequestUtil;
import com.sneaker.mall.api.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@Controller("salemancheckapi")
@RequestMapping(value = "/saleman/check")
public class CheckApi {

    @Autowired
    private CompanySettingService companySettingService;


    /**
     * 自动更新检测
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "update.action")
    public void autoUpdate(HttpServletRequest request, HttpServletResponse response) {
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        ResponseMessage<String> responseMessage = new ResponseMessage<>(500, "success");
        try {
            String v = RequestUtil.getString(request, "v", null);
            if (!Strings.isNullOrEmpty(v)) {
                CompanySetting currV = this.companySettingService.getCompanySettingByKey(CONST.SALEMAN_AUTO_UPDATE);
                if (currV != null && !v.equals(currV.getCvalue())) {
                    CompanySetting downUrl = this.companySettingService.getCompanySettingByKey(CONST.SALEMAN_AUTO_UPDATE_DOWNLOAD);
                    if (downUrl != null) {
                        responseMessage.setStatus(200);
                        responseMessage.setData(downUrl.getCvalue());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }

    /**
     * 获取最新APP版本与
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "appversion.action")
    public void getAppVersion(HttpServletRequest request, HttpServletResponse response) {
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        ResponseMessage<String> responseMessage = new ResponseMessage<>(200, "success");
        CompanySetting currV = this.companySettingService.getCompanySettingByKey(CONST.SALEMAN_AUTO_UPDATE);
        if (currV != null) {
            responseMessage.setData(currV.getCvalue());
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }
}
