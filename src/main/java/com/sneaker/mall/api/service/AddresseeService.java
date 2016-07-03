package com.sneaker.mall.api.service;

import com.sneaker.mall.api.model.Addressee;

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
public interface AddresseeService {

    /**
     * 保存一个收件信息
     *
     * @param addressee
     */
    public void saveAddressee(Addressee addressee);


    /**
     * 根据公司ID读取收件信息
     *
     * @param cid
     * @return
     */
    public List<Addressee> getAddresseeByCid(long cid);

    /**
     * 根据用户ID获取收件信息
     * @param uid
     * @return
     */
    public List<Addressee> getAddresseeByUid(long uid);

    /**
     * 根据用户ID与客户ID获取收件信息
     * @param uid
     * @param ccid
     * @return
     */
    public List<Addressee> getAddresseeByUidAndCcid(long uid,long ccid);


    /**
     * 根据删除一个收件信息
     *
     * @param id
     */
    public void deleteAddressee(long id);

    /**
     * 根据ID获取收件人信息
     *
     * @param id
     * @return
     */
    public Addressee getAddresseeById(long id);

    /**
     * 获取公司默认收货地址
     *
     * @param cid
     * @return
     */
    public Addressee getDefaultAddresseeByCid(long cid);
}
