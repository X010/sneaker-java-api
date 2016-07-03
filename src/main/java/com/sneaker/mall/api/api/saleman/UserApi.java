package com.sneaker.mall.api.api.saleman;

import com.sneaker.mall.api.exception.ParameterException;
import com.sneaker.mall.api.exception.SessionException;
import com.sneaker.mall.api.model.Company;
import com.sneaker.mall.api.model.ResponseMessage;
import com.sneaker.mall.api.model.User;
import com.sneaker.mall.api.model.UserTotal;
import com.sneaker.mall.api.model.Store;
import com.sneaker.mall.api.service.FavoriteService;
import com.sneaker.mall.api.service.OrderService;
import com.sneaker.mall.api.service.StoreService;
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
import java.util.ArrayList;
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
@Controller("salemanuserapi")
@RequestMapping(value = "/saleman/user")
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
    private StoreService storeService;

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
            //List<Company> supllier = this.companyService.getSupplierForCompany(getLoginUser(request).getCid()); //获取供应商
            //int countSupplier = 0;
            //if (supllier != null) countSupplier = supllier.size();
            int countFav = this.favoriteService.countFavoriteForUser(getLoginUser(request).getId());
            int countOrder = this.orderService.countOrderBySaleman(getLoginUser(request).getId());
            UserTotal userTotal = new UserTotal();
            userTotal.setFavCount(countFav);
            //userTotal.setSupplierCount(countSupplier);
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
        int pwdLength = 0;
        try {
            String username = RequestUtil.validateParam(request, "username", responseMessage);
            String oldpass  = RequestUtil.validateParam(request, "password", responseMessage);
            String password = "";
            if (oldpass.length() > 32) {
                password = oldpass.replaceAll("_","+");
            } else {
                password = oldpass;
            }
            User user = this.userService.loginForSalesman(username, password);
            if (user != null) {
                //验证业务员的客户数是否为0
                List<Company> companies = this.companyService.getCustomForCompanyAndUser(user);
                Company company = this.companyService.getCompanyById(user.getCid());
                user.setCom(company);
                setLoginUser(request, user);
                responseMessage.setData(user);
                if (companies != null && companies.size() > 0) {
                    //将部为信息放到Session中去,业务员的信息
                    //获取该用户的公司信息
                } else {
                    responseMessage.setStatus(500);
                    responseMessage.setMessage("该业务员没有客户,不允许登陆!");
                }
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

    /**
     * 获取用户有权限的仓库列表
     * @param request
     * @param response
     */
    @RequestMapping(value = "store.action")
    public void store(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<List<Store>> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            User user = getLoginUser(request);
            String sids = user.getSids();
            String[] sidArr = sids.split(",");
            List<Store> storeList = new ArrayList();
            for(int i =0; i< sidArr.length; i++) {
                Store store = this.storeService.getStoreByid(Long.valueOf(sidArr[i]));
                if (store != null) {
                    storeList.add(store);
                }
            }
            responseMessage.setData(storeList);
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
