package com.sneaker.mall.api.dao.info;

import com.sneaker.mall.api.model.GType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
public interface GTypeDao {
    /**
     * 根据公司读取相应层级分类
     *
     * @param cid
     * @param code
     * @return
     */
    @Select("select * from o_company_goods_type where cid=#{cid} and code like #{gcode} order by code")
    public List<GType> findGTypeByCid(@Param("cid") long cid, @Param("gcode") String code);

    /**
     * 根据编码长度获取分类信息
     *
     * @param cid
     * @param length
     * @return
     */
    @Select("select * from o_company_goods_type where cid=#{cid} and length(code)=#{cl} order by code")
    public List<GType> findGTypeByCodeLength(@Param("cid") long cid, @Param("cl") int length);

    /**
     * 获取平台的一次分类
     *
     * @return
     */
    @Select("select * from o_goods_type where length(code)=2 order by code")
    public List<GType> findPlatformGType();
}
