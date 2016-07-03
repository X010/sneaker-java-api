package com.sneaker.mall.api.service;

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
public interface PayService {

    /**
     * 根据单号与类型及支付类型获取支付QR的Sign
     *
     * @param bid
     * @param type
     * @param payType
     * @return
     */
    public String getPayQrSign(long bid, int type, int payType, String clientIp, long uid);

    /**
     * 确认支付成功
     *
     * @param btype
     * @param id
     * @param paytype
     * @return
     */
    public boolean commitPay(int btype, long id, int paytype, String inputAmount);
}
