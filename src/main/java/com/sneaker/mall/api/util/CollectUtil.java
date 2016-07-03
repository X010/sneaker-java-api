package com.sneaker.mall.api.util;

import com.sneaker.mall.api.model.Company;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
public class CollectUtil {

    public static Map<String, List<Company>> sortMapByKey(Map<String, List<Company>> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, List<Company>> sortMap = new TreeMap<String, List<Company>>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }
}


class MapKeyComparator implements Comparator<String> {
    public int compare(String str1, String str2) {
        return str1.compareTo(str2);
    }
}
