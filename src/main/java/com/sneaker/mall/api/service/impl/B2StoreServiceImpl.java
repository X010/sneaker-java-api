package com.sneaker.mall.api.service.impl;

import com.sneaker.mall.api.dao.info.StoreDao;
import com.sneaker.mall.api.model.Store;
import com.sneaker.mall.api.service.StoreService;
import com.sneaker.mall.api.util.CONST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
@Service
public class B2StoreServiceImpl implements StoreService {

    @Autowired
    private StoreDao storeDao;


    @Override
    public List<Store> getStoreByCid(long cid) {
        return this.storeDao.findStoreByCidAndStatus(cid, CONST.STORE_ONLINE);
    }

    @Override
    public Store getStoreByid(long id) {
        return this.storeDao.findStoreById(id, CONST.STORE_ONLINE);
    }

    @Override
    public List<Store> getStoreByids(String ids) {
        return this.storeDao.findStoreByIds(ids, CONST.STORE_ONLINE);
    }
}
