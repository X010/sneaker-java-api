package com.sneaker.mall.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.sneaker.mall.api.dao.info.CompanyDao;
import com.sneaker.mall.api.dao.info.PowerDao;
import com.sneaker.mall.api.dao.info.UserDao;
import com.sneaker.mall.api.model.AchievementInfo;
import com.sneaker.mall.api.model.Company;
import com.sneaker.mall.api.model.PowerRole;
import com.sneaker.mall.api.model.User;
import com.sneaker.mall.api.service.UserService;
import com.sneaker.mall.api.util.HttpClientUtil;
import com.sneaker.mall.api.util.JsonParser;
import com.sneaker.mall.api.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
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
public class B2UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(B2UserServiceImpl.class);

    @Autowired
    @Qualifier("salemanFlag")
    private Integer salemanFlag;

    @Autowired
    @Qualifier("dispathFlag")
    private Integer dispathFlag;

    @Autowired
    @Qualifier("cgFlag")
    private Integer cgFlag;

    @Autowired
    @Qualifier("salemanAdminFlag")
    private Integer salemanAdminFlag;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private PowerDao powerDao;

    @Autowired
    @Qualifier("login_url")
    private String loginUrl;

    @Override
    public User loginForUserNameAndPassword(String username, String password) throws UnsupportedEncodingException {
        Preconditions.checkNotNull(username);
        Preconditions.checkNotNull(password);
        password = MD5Util.toMD5(MD5Util.toMD5(MD5Util.toMD5(("Z8#x@2" + password + "^7t5c").getBytes("GBK")).getBytes("GBK")).getBytes("GBK")).toLowerCase();
        User user = this.userDao.findUserByUserNameAndPassword(username, password);
        if (user != null) {
            if (!Strings.isNullOrEmpty(user.getRids()) && this.isFlag(user.getCid(), cgFlag, user.getRids())) {
                //用户登陆成功，补充公司信息
                Company company = this.companyDao.findCompanyById(user.getCid());
                if (company != null) {
                    user.setCom(company);
                }
                return user;
            }
        }
        return null;
    }

    @Override
    public User loginForSalesman(String phone, String password) throws UnsupportedEncodingException {
        Preconditions.checkNotNull(phone);
        Preconditions.checkNotNull(password);
        User user = this.findUserByUserNameAndPassword(phone, password, "salesman");
        //password = MD5Util.toMD5(MD5Util.toMD5(MD5Util.toMD5(("Z8#x@2" + password + "^7t5c").getBytes("GBK")).getBytes("GBK")).getBytes("GBK")).toLowerCase();
        //User user = this.userDao.findUserByUserNameAndPassword(phone, password);
        if (user != null) {
            //判断用户是不是有登陆下单商城中的角色
            if (!Strings.isNullOrEmpty(user.getRids()) && this.isFlag(user.getCid(), salemanFlag, user.getRids())) {
                user.setSaleman(true);
                return user;
            }
        }
        return null;
    }

    @Override
    public User loginForDispatch(String username, String password) throws UnsupportedEncodingException {
        Preconditions.checkNotNull(username);
        Preconditions.checkNotNull(password);
        password = MD5Util.toMD5(MD5Util.toMD5(MD5Util.toMD5(("Z8#x@2" + password + "^7t5c").getBytes("GBK")).getBytes("GBK")).getBytes("GBK")).toLowerCase();
        User user = this.userDao.findUserByUserNameAndPassword(username, password);
        if (user != null) {
            //判断用户是不是有登陆支付配送的角色
            if (!Strings.isNullOrEmpty(user.getRids()) && this.isFlag(user.getCid(), dispathFlag, user.getRids())) {
                user.setSaleman(true);
                return user;
            }
        }
        return null;
    }

    @Override
    public User loginForSalesAdminMan(String username, String password) throws UnsupportedEncodingException {
        Preconditions.checkNotNull(username);
        Preconditions.checkNotNull(password);
        //User user = this.findUserByUserNameAndPassword(username, password, "salesman");
        password = MD5Util.toMD5(MD5Util.toMD5(MD5Util.toMD5(("Z8#x@2" + password + "^7t5c").getBytes("GBK")).getBytes("GBK")).getBytes("GBK")).toLowerCase();
        User user = this.userDao.findUserByUserNameAndPassword(username, password);
        if (user != null) {
            //判断该用户所在公司的下单商城管理权限是否开启
            Company company = this.companyDao.findCompanyById(user.getCid());
            if (company != null && company.getIsmall() == 1) {
                //判断用户是不是有登陆下单商城中的角色
                if (user.getAdmin() == 1) {
                    user.setSaleman(true);
                    return user;
                }

                if (!Strings.isNullOrEmpty(user.getRids()) && this.isFlag(user.getCid(), salemanAdminFlag, user.getRids())) {
                    user.setSaleman(true);
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public void updateUserPhoto(String photo, long id) {
        Preconditions.checkNotNull(photo);
        Preconditions.checkArgument(id > 0);
        this.userDao.updateUserPhoto(photo, id);
    }

    /**
     * 判断用户是否有该模块权限
     *
     * @param flag
     * @param userRole
     * @return
     */
    private boolean isFlag(long cid, int flag, String userRole) {
        boolean res = false;
        if (!Strings.isNullOrEmpty(userRole)) {
            String[] roleIds = userRole.split("\\,");
            if (roleIds != null && roleIds.length > 0) {
                for (String roleId : roleIds) {
                    if (!Strings.isNullOrEmpty(roleId)) {
                        PowerRole powerRole = this.powerDao.findPowerRoleByCidAndId(cid, Long.valueOf(roleId));
                        if (powerRole != null && !Strings.isNullOrEmpty(powerRole.getMids()) && ("," + powerRole.getMids() + ",").indexOf("," + flag + ",") >= 0) {
                            res = true;
                            break;
                        }
                    }
                }
            }
        }
        return res;
    }

    /**
     * 从方震的接口中登录用户
     * @param username password platform
     * @return user object
     */
    private User findUserByUserNameAndPassword(String username, String password, String platform) {
        Preconditions.checkNotNull(username);
        Preconditions.checkNotNull(password);
        Preconditions.checkNotNull(platform);
        HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();
        Map<String, String> params = Maps.newHashMap();
        params.put("username", String.valueOf(username));
        params.put("password", String.valueOf(password));
        params.put("platform", String.valueOf(platform));

        try {
            String res = httpClientUtil.getResponseBodyAsString(loginUrl, params);
            logger.info("res:" + res);
            if (!Strings.isNullOrEmpty(res)) {
                JSONObject jsonObject = JSON.parseObject(res);
                if (jsonObject != null && jsonObject.getString("msg") != null && Integer.parseInt(jsonObject.getString("err")) == 0) {

                    return (User) JsonParser.StringToJsonVideo(jsonObject.getString("msg"), User.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
