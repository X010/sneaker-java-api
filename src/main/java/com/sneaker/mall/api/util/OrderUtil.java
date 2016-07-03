package com.sneaker.mall.api.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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
public class OrderUtil {
    private static final Random random = new Random();

    /**
     * 生成一个订单号
     *
     * @return
     */
    public static String generateOrderId() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuilder stringBuilder = new StringBuilder(simpleDateFormat.format(new Date()));
        stringBuilder.append(String.valueOf((random.nextInt(CONST.ORDER_RANDOM_END) + CONST.ORDER_RANDOM_START)));
        return stringBuilder.toString();
    }

    /**
     * 生成一个超级订单号
     *
     * @return
     */
    public static String generateSuperOrderId() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuilder stringBuilder = new StringBuilder("9");
        stringBuilder.append(simpleDateFormat.format(new Date()));
        stringBuilder.append(String.valueOf((random.nextInt(CONST.ORDER_RANDOM_END) + CONST.ORDER_RANDOM_START)));
        return stringBuilder.toString();
    }
}
