package com.sneaker.mall.api.service.impl;

import com.google.common.base.Preconditions;
import com.sneaker.mall.api.dao.info.ApplyCustomerDao;
import com.sneaker.mall.api.dao.info.CompanyDao;
import com.sneaker.mall.api.dao.info.GeoLocationDao;
import com.sneaker.mall.api.model.*;
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
public class B2CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private ApplyCustomerDao applyCustomerDao;

    @Autowired
    private GeoLocationDao geoLocationDao;

    public List<Company> getSupplierForCompany(long id) {
        Preconditions.checkArgument(id > 0);
        List<Company> companies = this.companyDao.findSupllierByScid(id);
        return companies;
    }

    /**
     * 从客户关系中获取对应的类型
     * @param user
     * @return
     */
    public List<Customer> getCustomerForCompanyAndUser(User user) {
        Preconditions.checkNotNull(user);
        List<Customer> costomers = this.companyDao.findRComstomerByCompanyIdAndSalemanId(user.getCid(), user.getId());
        return costomers;
    }

    public List<Company> getCustomForCompanyAndUser(User user) {
        Preconditions.checkNotNull(user);
        List<Company> companies = this.companyDao.findComstomerByCompanyIdAndSalemanId(user.getCid(), user.getId());
        return companies;
    }

    /**
     * 从客户关系中搜索
     * @param user
     * @param k
     * @return
     */
    public List<Customer> searchCustomerForUserAndKeyword(User user, String k) {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(k);
        k = "%" + k + "%";
        return this.companyDao.findRComstomerByCompanyIdAndSalemanIdAndKeyword(user.getCid(), user.getId(), k);
    }

    public List<Company> searchCustomForUserAndKeyword(User user, String k) {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(k);
        k = "%" + k + "%";
        return this.companyDao.findComstomerByCompanyIdAndSalemanIdAndKeyword(user.getCid(), user.getId(), k);
    }

    public Company getCompanyById(long id) {
        Preconditions.checkArgument(id > 0);
        return this.companyDao.findCompanyById(id);
    }

    public Customer getCustomerByCidAndScid(long cid, long scid) {
        Preconditions.checkArgument(cid > 0);
        Preconditions.checkArgument(scid > 0);
        return this.companyDao.findCustomerByCompanyIdAndCoustomerCompanyId(scid, cid);
    }

    public void saveApplyCustomer(ApplyCustomer applyCustomer, GeoLocation geoLocation) {
        Preconditions.checkNotNull(applyCustomer);
        //判断该客户是否已经申请过
        //ApplyCustomer oldApplyCustomer = this.applyCustomerDao.findApplyCustomerByCidAndPhone(applyCustomer.getPhone());

        //if (oldApplyCustomer == null) {
        this.applyCustomerDao.addApplyCustomer(applyCustomer);
        //获取刚刚申请的ID
        ApplyCustomer tempCustomer = this.applyCustomerDao.findApplyCustomerByCidAndPhone(applyCustomer.getPhone());
        if (tempCustomer != null && tempCustomer.getId() > 0) {
            geoLocation.setCid(applyCustomer.getCid()); //暂时将申请客户的ID当成CID写到数据库中
            geoLocation.setCcid(tempCustomer.getId());
            geoLocation.setSource(2);
            geoLocation.setUid(applyCustomer.getSuid());
            this.geoLocationDao.insertGeoLocation(geoLocation);
        }
        //}
    }

    public List<ApplyCustomer> getApplyCustomerByStatus(long suid) {
        return this.applyCustomerDao.findApplyCustomerByStatus(suid);
    }

    public void saveCompany(Company company) {
        Preconditions.checkNotNull(company);
        this.companyDao.updateCompany(company);
    }
}
