package com.sneaker.mall.api.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.sneaker.mall.api.dao.admin.CompanyGtypeDao;
import com.sneaker.mall.api.dao.info.GTypeDao;
import com.sneaker.mall.api.model.CompanyGtype;
import com.sneaker.mall.api.model.GType;
import com.sneaker.mall.api.service.CompanySettingService;
import com.sneaker.mall.api.service.GTypeService;
import com.sneaker.mall.api.util.CONST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
 */
@Service
public class B2GTypeServiceImpl implements GTypeService {

    @Autowired
    private GTypeDao gTypeDao;

    @Autowired
    private CompanySettingService companySettingService;

    @Autowired
    private CompanyGtypeDao companyGtypeDao;

    @Override
    public List<GType> getGtypeForCid(long cid, String code) {
        Preconditions.checkArgument(cid > 0);
        Preconditions.checkNotNull(code);
        return this.gTypeDao.findGTypeByCid(cid, code);
    }

    @Override
    public List<GType> getGTypeForCidAndShowConfig(long cid, String code) {
        Preconditions.checkArgument(cid > 0);
        List<GType> gTypes = null;
        if (!Strings.isNullOrEmpty(code)) {
            //获取子级分类
            gTypes = this.getGtypeForCid(cid, code + "__");
        } else {
            //获取顶级分类，由于顶级分类由用户在商城后台，设计显示层级及展示顺序
            int showLayout = this.companySettingService.getCompanyCateShowLayout(cid);
            if (showLayout < 1) {
                gTypes = this.getGtypeForCid(cid, "__");
            } else {
                gTypes = this.gTypeDao.findGTypeByCodeLength(cid, showLayout * CONST.CATE_LENGTH);
                //过滤显示的，和按指定顺序排序
                if (gTypes != null) {
                    List<CompanyGtype> companyGtypes = this.companyGtypeDao.findCompanyGtypeByCid(cid);
                    if (companyGtypes != null) {
                        Map<Long, CompanyGtype> companyGtypeMap = companyGtypes.stream().collect(Collectors.toMap(CompanyGtype::getTid, Function.identity()));
                        List<GType> filterGtypes = gTypes.stream().filter(gType -> companyGtypeMap.get(gType.getId()) != null).collect(Collectors.toList());
                        if (filterGtypes != null) {
                            filterGtypes.forEach(
                                    gType -> {
                                        gType.setCompanyGtype(companyGtypeMap.get(gType.getId()));
                                    }
                            );

                            //对FilterGTypes按Csort进行排序
                            Collections.sort(filterGtypes);
                            return filterGtypes;
                        }
                    }
                }
            }
        }
        return gTypes;
    }

    @Override
    public List<GType> getPlatformGTypeForFirst() {
        return this.gTypeDao.findPlatformGType();
    }
}
