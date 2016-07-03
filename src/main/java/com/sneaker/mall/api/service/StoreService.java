package com.sneaker.mall.api.service;

import com.sneaker.mall.api.model.Store;

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
public interface StoreService {

    /**
     * 读取公司正在使用的仓库
     * @return
     */
    public List<Store> getStoreByCid(long cid);

    /**
     * 读取单个仓库信息
     */
    public Store getStoreByid(long id);

    /**
     * 根据id批量获取仓库信息
     * @param id
     * @return
     */
    public List<Store> getStoreByids(String id);
}
