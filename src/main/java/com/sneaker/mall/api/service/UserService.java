package com.sneaker.mall.api.service;

import com.sneaker.mall.api.model.User;

import java.io.UnsupportedEncodingException;

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
public interface UserService {

    /**
     * 根据用户名和密码登陆
     *
     * @param username
     * @param password
     * @return
     */
    public User loginForUserNameAndPassword(String username, String password) throws UnsupportedEncodingException;


    /**
     * 业务员登陆
     *
     * @param phone
     * @param password
     * @return
     */
    public User loginForSalesman(String phone, String password) throws UnsupportedEncodingException;


    /**
     * 支付配送平台登陆
     * @param username
     * @param password
     * @return
     * @throws UnsupportedEncodingException
     */
    public User loginForDispatch(String username,String password) throws UnsupportedEncodingException;

    /**
     * 业务后面管理员登陆
     * @param username
     * @param password
     * @return
     * @throws UnsupportedEncodingException
     */
    public User loginForSalesAdminMan(String username, String password) throws UnsupportedEncodingException;

    /**
     * 更新用户图片
     * @param photo
     * @param id
     */
    public void updateUserPhoto(String photo,long id);
}
