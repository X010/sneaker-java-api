package com.sneaker.mall.api.dao.info;

import com.sneaker.mall.api.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
public interface UserDao {
    /**
     * 根据用户名和密码查询用户
     *
     * @param username
     * @param password
     * @return
     */
    @Select("select * from o_user where username=#{username} and password=#{password} and status=1")
    public User findUserByUserNameAndPassword(@Param("username") String username, @Param("password") String password);


    /**
     * 根据手机号和密码查询用户
     *
     * @param phone
     * @param password
     * @return
     */
    public User findUserByPhoneAndPassword(@Param("phone") String phone, @Param("password") String password);

    /**
     * 更新个人图像
     *
     * @param photo
     */
    @Update("update o_user set photo=#{photo} where id=#{id}")
    public void updateUserPhoto(@Param("photo") String photo, @Param("id") long id);
}
