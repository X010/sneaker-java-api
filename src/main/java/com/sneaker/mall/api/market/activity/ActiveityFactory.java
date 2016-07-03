package com.sneaker.mall.api.market.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
@Component
public class ActiveityFactory {

    @Autowired
    private Activeity buyFreeActiveity;

    /**
     * 根据类型获取活动类型
     *
     * @param mtype
     * @return
     */
    public Activeity getInstance(int mtype) {
        Activeity activeity = null;
        switch (mtype) {
            case 1:
                activeity = buyFreeActiveity;
                break;
        }
        return activeity;
    }
}
