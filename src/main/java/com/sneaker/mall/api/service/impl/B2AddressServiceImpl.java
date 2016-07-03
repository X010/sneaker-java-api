package com.sneaker.mall.api.service.impl;

import com.google.common.base.Preconditions;
import com.sneaker.mall.api.dao.admin.AddresseeDao;
import com.sneaker.mall.api.model.Addressee;
import com.sneaker.mall.api.service.AddresseeService;
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
public class B2AddressServiceImpl implements AddresseeService {

    @Autowired
    private AddresseeDao addresseeDao;


    @Override
    public void saveAddressee(Addressee addressee) {
        Preconditions.checkNotNull(addressee);
        //将其它设为非默认收货地址
        if (addressee.getDef() == 1) {
            this.addresseeDao.disableDefAddressee(addressee.getCid());
        }
        if (addressee.getId() > 0) {
            //修改
            this.addresseeDao.updateAddress(addressee);
        } else {
            //把其它的设置为非默认的
            this.addresseeDao.disableDefAddressee(addressee.getCid());
            addressee.setDef(1);
            //新建
            this.addresseeDao.addAddress(addressee);
        }
    }

    @Override
    public List<Addressee> getAddresseeByCid(long cid) {
        Preconditions.checkArgument(cid > 0);
        List<Addressee> addressees = this.addresseeDao.findAddressByCid(cid);
        return addressees;
    }

    @Override
    public List<Addressee> getAddresseeByUid(long uid) {
        Preconditions.checkArgument(uid > 0);
        List<Addressee> addressees = this.addresseeDao.findAddresseeByUid(uid);
        return addressees;
    }

    @Override
    public List<Addressee> getAddresseeByUidAndCcid(long uid, long ccid) {
        Preconditions.checkArgument(uid > 0);
        Preconditions.checkArgument(ccid > 0);
        List<Addressee> addressees = this.addresseeDao.findAddresseeByUidAndCcid(uid, ccid);
        return addressees;
    }

    @Override
    public void deleteAddressee(long id) {
        Preconditions.checkArgument(id > 0);
        Addressee addressee = this.addresseeDao.findAddresseeByid(id);
        if (addressee != null) {
            addressee.setStatus(0);
            this.addresseeDao.updateAddress(addressee);
        }
    }

    @Override
    public Addressee getAddresseeById(long id) {
        Preconditions.checkArgument(id > 0);
        Addressee addressee = this.addresseeDao.findAddresseeByid(id);
        return addressee;
    }

    @Override
    public Addressee getDefaultAddresseeByCid(long cid) {
        Preconditions.checkArgument(cid > 0);
        Addressee def = null;
        List<Addressee> defaultAddressee = this.addresseeDao.findAddressByCidAndDef(cid);
        if (defaultAddressee != null && defaultAddressee.size() > 0) {
            def = defaultAddressee.get(0);
        } else {
            defaultAddressee = this.addresseeDao.findAddressByCid(cid);
            if (defaultAddressee != null && defaultAddressee.size() > 0) {
                def = defaultAddressee.get(0);
            }
        }
        return def;
    }
}
