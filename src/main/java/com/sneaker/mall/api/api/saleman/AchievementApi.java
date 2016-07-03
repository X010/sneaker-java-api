package com.sneaker.mall.api.api.saleman;

import com.sneaker.mall.api.exception.ParameterException;
import com.sneaker.mall.api.exception.SessionException;
import com.sneaker.mall.api.model.AchievementInfo;
import com.sneaker.mall.api.model.ResponseMessage;
import com.sneaker.mall.api.service.AchievementService;
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
@Controller("salemanachievement")
@RequestMapping(value = "/saleman/achievement")
public class AchievementApi extends BaseController {

    @Autowired
    private AchievementService achievementService;

    /**
     * 获取业务统计信息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "info.action")
    public void info(HttpServletRequest request, HttpServletResponse response) {
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        ResponseMessage<AchievementInfo> responseMessage = new ResponseMessage<>(200, "success");
        try {
            long uid = getLoginUser(request).getId();
            AchievementInfo achievementInfo = this.achievementService.getSalemanAchievementInfo(uid);
            if (achievementInfo != null) {
                responseMessage.setData(achievementInfo);
            }
        } catch (ParameterException pe) {
            pe.printStackTrace();
        } catch (SessionException se) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("login fail");
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }
}
