package com.sneaker.mall.api.service.impl;

import com.google.common.base.Preconditions;
import com.sneaker.mall.api.dao.info.BOrderDao;
import com.sneaker.mall.api.dao.info.SettleCustomerDao;
import com.sneaker.mall.api.dao.info.StockOutDao;
import com.sneaker.mall.api.model.*;
import com.sneaker.mall.api.service.DispatchService;
import com.sneaker.mall.api.service.OrderService;
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
public class DispatchServiceImpl implements DispatchService {

    @Autowired
    private BOrderDao bOrderDao;

    @Autowired
    private SettleCustomerDao settleCustomerDao;

    @Autowired
    private StockOutDao stockOutDao;

    @Autowired
    private OrderService orderService;


    @Override
    public StockOut getStockOutById(long id) {
        Preconditions.checkArgument(id > 0);
        StockOut stockOut = this.stockOutDao.findStockOutById(id);
        //获取GoodList
        List<PayGateway.Glist> glistList = this.stockOutDao.findStockOutGoodList(id);
        if (glistList != null) {
            stockOut.setGoodsList(glistList);
        }
        //判断该出库单是否可进行结算
        if (stockOut.getStatus() == 1) {
            stockOut = null;
        }
        return stockOut;
    }

    @Override
    public BOrder getBOrderById(long id) {
        Preconditions.checkArgument(id > 0);
        BOrder bOrder = this.bOrderDao.findBOrderById(id);
        //判断客户订单是否可以进行结算
        return bOrder;
    }

    @Override
    public SettleCustomer getSettleCustomerById(long id) {
        Preconditions.checkArgument(id > 0);
        SettleCustomer settleCustomer = this.settleCustomerDao.findSettleCustomerById(id);
        //判断结算单是否可以进行结算
        if (settleCustomer.getStatus() != 1) {
            settleCustomer = null;
        }
        return settleCustomer;
    }

    @Override
    public Integer isCanPay(long id, int type) {
        Preconditions.checkArgument(id > 0);
        int res = 0; //0表示初始，1表示可以支付 4类型不存在 5表示订单不存在 6表示存在不可以支付
        switch (type) {
            case 1://出库单
                StockOut stockOut = this.getStockOutById(id);
                if (stockOut != null) {
                    if (stockOut.getSettle_id() <= 0) {
                        //末结算
                        res = 1;
                    }
                }
                break;
            case 2://结算单
                SettleCustomer settleCustomer = this.getSettleCustomerById(id);
                if (settleCustomer != null && settleCustomer.getStatus() != 2) {
                    //末结算
                    res = 1;
                }
                break;
            case 3://客户订单
                Order bOrder = this.orderService.getOrderById(id);
                if (bOrder != null) {
                    if (bOrder.getIspay() != 9 && bOrder.getIspay() != 8) {
                        //没有支付
                        res = 1;
                    }
                }
                break;
            default:
                res = 4;
        }
        return res;
    }
}
