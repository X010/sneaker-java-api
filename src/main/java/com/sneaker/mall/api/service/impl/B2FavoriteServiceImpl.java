package com.sneaker.mall.api.service.impl;

import com.google.common.base.Preconditions;
import com.sneaker.mall.api.dao.admin.FavoriteDao;
import com.sneaker.mall.api.model.Favorite;
import com.sneaker.mall.api.model.Goods;
import com.sneaker.mall.api.service.FavoriteService;
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
public class B2FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteDao favoriteDao;


    @Override
    public void saveFavorite(Favorite favorite) {
        Preconditions.checkNotNull(favorite);
        if (favorite.getId() > 0) {
            //修改
            this.favoriteDao.updateFavorite(favorite);
        } else {
            //新建
            this.favoriteDao.addFavorite(favorite);
        }
    }

    @Override
    public int countFavorite(long cid) {
        Preconditions.checkArgument(cid > 0);
        return this.favoriteDao.countFavorite(cid);
    }

    @Override
    public int countFavoriteForUser(long uid) {
        Preconditions.checkArgument(uid > 0);
        return this.favoriteDao.countFavoriteForUser(uid);
    }

    @Override
    public boolean canFavorite(long cid, long mgid) {
        Preconditions.checkArgument(cid > 0);
        Preconditions.checkArgument(mgid > 0);
        List<Favorite> favorites = this.favoriteDao.findFavoriteByCidAndMgid(cid, mgid, 1);
        if (favorites != null && favorites.size() > 0) return false;
        return true;
    }

    @Override
    public boolean canFavoriteForUser(long uid, long mgid) {
        Preconditions.checkArgument(uid > 0);
        Preconditions.checkArgument(mgid > 0);
        List<Favorite> favorites = this.favoriteDao.findFavoriteByUidAndMgid(uid, mgid, 1);
        if (favorites != null && favorites.size() > 0) return false;
        return true;
    }

    @Override
    public List<Favorite> getFavoriteByCidAndGid(long cid, long mgid) {
        Preconditions.checkArgument(cid > 0);
        Preconditions.checkArgument(mgid > 0);
        return this.favoriteDao.findFavoriteByCidAndMgid(cid, mgid, 1);
    }

    @Override
    public List<Favorite> getFavoriteByUidAndGid(long uid, long mgid) {
        Preconditions.checkArgument(uid > 0);
        Preconditions.checkArgument(mgid > 0);
        return this.favoriteDao.findFavoriteByUidAndMgid(uid, mgid, 1);
    }
}
