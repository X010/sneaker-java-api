package com.sneaker.mall.api.dao.info;

import com.sneaker.mall.api.model.PowerRole;
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
public interface PowerDao {


    /**
     * 根据公司与ID查询
     *
     * @return
     */
    @Select("select * from s_role where cid=#{cid} and status=1 and id=#{id} limit 1")
    public PowerRole findPowerRoleByCidAndId(@Param("cid") long cid, @Param("id") long id);

}
