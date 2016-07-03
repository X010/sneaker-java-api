package com.sneaker.mall.api.task.impl;

import com.google.common.base.Strings;
import com.sneaker.mall.api.dao.admin.AddresseeDao;
import com.sneaker.mall.api.dao.info.CompanyDao;
import com.sneaker.mall.api.model.Addressee;
import com.sneaker.mall.api.model.Company;
import com.sneaker.mall.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

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
 * 同步公司的收货地址
 */
public class CustomerAddressTask extends Task {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private AddresseeDao addresseeDao;

    final static Logger logger = LoggerFactory.getLogger(CustomerAddressTask.class);

    final static int PAGE_SIZE = 100;

    @Override
    public void run() {
        int comCount = this.companyDao.countCompany();
        if (comCount > 0) {
            int page = 0;
            if (comCount % PAGE_SIZE == 0) page = comCount / PAGE_SIZE;
            else page = comCount / PAGE_SIZE + 1;

            for (int i = 1; i <= page; i++) {
                int start = (i - 1) * PAGE_SIZE;

                List<Company> companies = this.companyDao.findCompanyByPage(start, PAGE_SIZE);

                if (companies != null) {
                    companies.forEach(company -> {
                        try {
                            List<Addressee> addressees = this.addresseeDao.findAddressByCid(company.getId());
                            if (addressees == null || addressees.size() <= 0) {
                                //公司还没有收货地址，则导入ERP的地址
                                Addressee addressee = new Addressee();
                                addressee.setCid(company.getId());
                                addressee.setProvince("");
                                addressee.setCity("");
                                addressee.setCounty("");
                                String addressStr = company.getAddress();
                                if (!Strings.isNullOrEmpty(addressStr)) {
                                    addressStr.replaceAll("null", "");
                                }
                                addressee.setStreet(addressStr);
                                addressee.setCfrom(1);
                                addressee.setContacts(company.getContactor());
                                addressee.setPhone(company.getContactor_phone());
                                addressee.setDef(1);
                                addressee.setStatus(1);
                                addressee.setUid(0);
                                addressee.setMemo("来至ERP");
                                //保存信息
                                this.addresseeDao.addAddress(addressee);
                            } else {
                                //更新收货信息
                                addressees = addressees.stream().filter(addressee1 -> addressee1.getCfrom() == 1).collect(Collectors.toList());
                                if (addressees != null && addressees.size() > 0) {
                                    Addressee addressee = addressees.get(0);
                                    addressee.setProvince("");
                                    addressee.setCity("");
                                    addressee.setCounty("");
                                    String addressStr = company.getAddress();
                                    if (!Strings.isNullOrEmpty(addressStr)) {
                                        addressStr.replaceAll("null", "");
                                    }
                                    addressee.setStreet(addressStr);
                                    addressee.setCfrom(1);
                                    addressee.setContacts(company.getContactor());
                                    addressee.setPhone(company.getContactor_phone());
                                    this.addresseeDao.updateAddress(addressee);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        }
    }
}
