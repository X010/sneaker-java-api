package com.sneaker.mall.api.service.impl;

import com.google.common.base.Preconditions;
import com.sneaker.mall.api.dao.admin.PayGatewayDao;
import com.sneaker.mall.api.model.PayGateway;
import com.sneaker.mall.api.service.PayGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
public class PayGatewayServiceImpl implements PayGatewayService {

    @Autowired
    private PayGatewayDao payGatewayDao;


    @Override
    public void savePayGateway(PayGateway payGateway) {
        Preconditions.checkNotNull(payGateway);
        Preconditions.checkNotNull(payGateway.getErp_id());

        PayGateway temp = this.payGatewayDao.findPayGatewayByPayId(payGateway.getErp_id());

        if (temp == null) {
            //新建
            this.payGatewayDao.insertPayGateway(payGateway);
        } else {
            //更新
            payGateway.setUpdatetime(new Date());
            this.payGatewayDao.updatePayGateway(payGateway);
        }
    }

    @Override
    public PayGateway getPayGatewayById(String id) {
        Preconditions.checkNotNull(id);

        PayGateway payGateway = this.payGatewayDao.findPayGatewayByPayId(id);
        return payGateway;
    }

    @Override
    public List<PayGateway> getPayGatewayByUid(long uid,int page,int size) {
        Preconditions.checkArgument(uid>0);
        Preconditions.checkArgument(page>0);
        Preconditions.checkArgument(size>0);
        return this.payGatewayDao.findPayGatewayByUid(uid,(page-1)*size,size);
    }
}
