package com.sneaker.mall.api.service;

import com.sneaker.mall.api.model.Favorite;
import com.sneaker.mall.api.model.Goods;

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
public interface FavoriteService {

    /**
     * 保存一个Factory对象
     *
     * @param favorite
     */
    public void saveFavorite(Favorite favorite);

    /**
     * 根据公司ID获取收藏记录条数
     *
     * @param cid
     * @return
     */
    public int countFavorite(long cid);


    /**
     * 根据用户ID获取收藏记录条数
     *
     * @param uid
     * @return
     */
    public int countFavoriteForUser(long uid);

    /**
     * 判断该商品可否收藏
     *
     * @param cid
     * @param mgid
     * @return
     */
    public boolean canFavorite(long cid, long mgid);

    /**
     * 判断用户是不是收藏该商品
     *
     * @param uid
     * @param mgid
     * @return
     */
    public boolean canFavoriteForUser(long uid, long mgid);



    /**
     * 根据CID和GID找到收藏对像
     *
     * @param cid
     * @param mgid
     * @return
     */
    public List<Favorite> getFavoriteByCidAndGid(long cid, long mgid);

    /**
     * 根据UID和GID找到收藏对象
     *
     * @param uid
     * @param mgid
     * @return
     */
    public List<Favorite> getFavoriteByUidAndGid(long uid, long mgid);
}
