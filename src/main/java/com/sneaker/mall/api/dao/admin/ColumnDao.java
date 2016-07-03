package com.sneaker.mall.api.dao.admin;

import com.sneaker.mall.api.model.Column;
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
public interface ColumnDao {

    /**
     * 根据公司ID获取栏目信息
     *
     * @param cid
     * @return
     */
    @Select("select * from db_cate where  type=1 and company_id=#{cid} and publish=1 order by csort")
    public List<Column> findColumnByCompanyId(@Param("cid") long cid);

    /**
     * 获取公司广告信息通栏
     *
     * @param cid
     * @return
     */
    @Select("select * from db_cate where  type=2 and company_id=#{cid} and publish=1 order by csort")
    public List<Column> findAdByCompanyId(@Param("cid") long cid);
}
