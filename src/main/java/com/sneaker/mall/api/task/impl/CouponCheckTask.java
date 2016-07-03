package com.sneaker.mall.api.task.impl;

import com.sneaker.mall.api.dao.admin.CouponDetailDao;
import com.sneaker.mall.api.model.CouponDetail;
import com.sneaker.mall.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;

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
 * 红包信息每天检测
 */
public class CouponCheckTask extends Task {
    private final static int PAGE_SIZE = 100;

    @Autowired
    private CouponDetailDao couponDetailDao;

    @Override
    public void run() {
        int count = this.couponDetailDao.countCouponDetail();
        if (count > 0) {
            int page = 0;
            if (count % PAGE_SIZE == 0) {
                page = count / PAGE_SIZE;
            } else {
                page = count / PAGE_SIZE + 1;
            }

            if (page > 0) {
                for (int i = 1; i <= page; i++) {
                    int start = (i - 1) * PAGE_SIZE;
                    List<CouponDetail> couponDetails = this.couponDetailDao.getCouponDetailByPage(start, PAGE_SIZE);
                    for (CouponDetail couponDetail : couponDetails) {
                        if (couponDetail.getCoupon_use_end().getTime() >= System.currentTimeMillis()) {
                            couponDetail.setStatus(8);
                            this.couponDetailDao.updateCouponDetail(couponDetail);
                        }
                    }
                }
            }
        }
    }
}
