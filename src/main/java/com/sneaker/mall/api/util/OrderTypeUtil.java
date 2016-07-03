package com.sneaker.mall.api.util;

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
public class OrderTypeUtil {

    /**
     * 获取单据类型
     *
     * @param id
     * @return
     */
    public static int getOrderType(String id) {
        int res = 0;
        try {
            long lid = Long.valueOf(id);
            if (lid >= 1000000001L && lid <= 1999999999L) {
                res = 13;//客户订单
            } else if (lid >= 2000000001L && lid <= 2999999999L) {
                res = 1;//出库单
            } else if (lid >= 3000000001L && lid <= 3999999999L) {
                res = 4;//入库单
            } else if (lid >= 4100000001L && lid <= 4199999999L) {
                res = 5;//永久调价单
            } else if (lid >= 4200000001L && lid <= 4299999999L) {
                res = 6;//临时调价单
            } else if (lid >= 5100000001L && lid <= 5199999999L) {
                res = 7;//帐盘单
            } else if (lid >= 5200000001L && lid <= 5299999999L) {
                res = 8;//实盘单
            } else if (lid >= 6100000001L && lid <= 6199999999L) {
                res = 9;//供应商结算单
            } else if (lid >= 6200000001L && lid <= 6299999999L) {
                res = 2;//客户结算单
            } else if (lid >= 6300000001L && lid <= 6399999999L) {
                res = 11;//收款单
            } else if (lid >= 6400000001L && lid <= 6499999999L) {
                res = 12;//付款单
            } else if (lid >= 6500000001L && lid <= 6599999999L) {
                res = 13;//供应商代销结算单
            } else {
                res = 3;//下单商城订单
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
