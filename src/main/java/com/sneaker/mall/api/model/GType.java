package com.sneaker.mall.api.model;

import java.util.Comparator;

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
public class GType extends BaseModel<Long> implements Comparable<GType> {
    /**
     * 公司ID
     */
    private long cid;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 商城后台的设置信息
     */
    private CompanyGtype companyGtype;

    public CompanyGtype getCompanyGtype() {
        return companyGtype;
    }

    public void setCompanyGtype(CompanyGtype companyGtype) {
        this.companyGtype = companyGtype;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(GType o2) {
        GType o1=this;
        if (o1.getCompanyGtype() == null) return 0;
        if (o2.getCompanyGtype() == null) return 0;
        if (o1.getCompanyGtype().getCsort() > o2.getCompanyGtype().getCsort()) {
            return 1;
        } else {
            return -1;
        }
    }
}
