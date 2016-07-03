package com.sneaker.mall.api.api.dispatch;

import com.sneaker.mall.api.api.saleman.BaseController;
import com.sneaker.mall.api.exception.ParameterException;
import com.sneaker.mall.api.exception.SessionException;
import com.sneaker.mall.api.model.Company;
import com.sneaker.mall.api.model.ResponseMessage;
import com.sneaker.mall.api.model.User;
import com.sneaker.mall.api.service.UserService;
import com.sneaker.mall.api.service.impl.B2CompanyService;
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
@Controller("dispatchuserapi")
@RequestMapping(value = "/dispatch/user")
public class UserApi extends BaseController {

    @Autowired
    private UserService userService;


    @Autowired
    private B2CompanyService companyService;

    /**
     * 退出系统
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "loginout.action")
    public void loginout(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<String> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            clearLoginUser(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }

    /**
     * 登陆
     * username 用户名
     * password 密码
     * usertype
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "login.action")
    public void login(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<User> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            String username = RequestUtil.validateParam(request, "username", responseMessage);
            String password = RequestUtil.validateParam(request, "password", responseMessage);
            User user = this.userService.loginForDispatch(username, password);
            if (user != null) {
                //将部为信息放到Session中去,业务员的信息
                //获取该用户的公司信息
                Company company = this.companyService.getCompanyById(user.getCid());
                user.setCom(company);
                setLoginUser(request, user);
                responseMessage.setData(user);
            } else {
                responseMessage.setStatus(300);
                responseMessage.setMessage("login fail");
            }
        } catch (ParameterException pe) {
            pe.printStackTrace();
        } catch (Exception e) {
            responseMessage.setStatus(400);
            responseMessage.setMessage(e.getMessage());
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }

    /**
     * 获取用户信息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "get.action")
    public void get(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<User> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            User user = getLoginUser(request);
            responseMessage.setData(user);
        } catch (ParameterException pe) {
            pe.printStackTrace();
        } catch (SessionException e1) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("login fail");
        } catch (Exception e) {
            responseMessage.setStatus(400);
            responseMessage.setMessage(e.getMessage());
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }
}
