package com.sneaker.mall.api.api.b2;

import com.sneaker.mall.api.exception.ParameterException;
import com.sneaker.mall.api.exception.SessionException;
import com.sneaker.mall.api.model.*;
import com.sneaker.mall.api.service.*;
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
@Controller
@RequestMapping(value = "/b2/user")
public class UserApi extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private B2CompanyService companyService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 用户统计信息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "total.action")
    public void total(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<UserTotal> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            long scid = RequestUtil.getLong(request, "scid", 0);
            List<Company> supllier = this.companyService.getSupplierForCompany(getLoginUser(request).getCid()); //获取供应商
            int countSupplier = 0;
            if (supllier != null) countSupplier = supllier.size();
            int countFav = 0;
            List<Goods> goodses = this.goodsService.getFavoriteGoodsCidAndScid(scid, getLoginUser(request).getCid(), 1, Integer.MAX_VALUE);
            if (goodses != null) countFav = goodses.size();
            int countOrder = this.orderService.countOrderByCompany(getLoginUser(request).getCid());
            UserTotal userTotal = new UserTotal();
            userTotal.setFavCount(countFav);
            userTotal.setSupplierCount(countSupplier);
            userTotal.setOrderCount(countOrder);
            responseMessage.setData(userTotal);
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

    /**
     * 退出登陆
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
            User user = getLoginUser(request);
            if (user != null) {
                clearLoginUser(request);
            }
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

    /**
     * 登陆
     * username 用户名
     * password 密码
     * usertype 用户类型  1 普通用户  2 业务员
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
            String usertype = RequestUtil.validateParam(request, "usertype", responseMessage);
            User user = null;
            switch (usertype) {
                case "1": //普通小B用户
                    user = this.userService.loginForUserNameAndPassword(username, password);
                    break;

                case "2": //业务员登陆
                    user = this.userService.loginForSalesman(username, password);
                    break;
            }
            if (user != null) {
                //将部为信息放到Session中去
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
}
