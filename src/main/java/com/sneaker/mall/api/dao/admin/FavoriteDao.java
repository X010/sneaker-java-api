package com.sneaker.mall.api.dao.admin;

import com.sneaker.mall.api.model.Favorite;
import com.sneaker.mall.api.model.Goods;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
public interface FavoriteDao {

    /**
     * 添加一个收藏
     *
     * @param favorite
     */
    @Insert("insert into db_favorite(uid,cid,mgid,create_time,status)values(#{uid},#{cid},#{mgid},#{create_time},#{status})")
    public void addFavorite(Favorite favorite);

    /**
     * 修改一个收藏
     *
     * @param favorite
     */
    @Update("update db_favorite set status=#{status} where id=#{id}")
    public void updateFavorite(Favorite favorite);

    /**
     * 根据公司与商品查询
     *
     * @param cid
     * @param mgid
     * @return
     */
    @Select("select * from db_favorite where status=#{status} and cid=#{cid} and mgid=#{mgid}")
    public List<Favorite> findFavoriteByCidAndMgid(@Param("cid") long cid, @Param("mgid") long mgid, @Param("status") int status);

    /**
     * 根据用户与商品查询
     *
     * @param uid
     * @param mgid
     * @param status
     * @return
     */
    @Select("select * from db_favorite where status=#{status} and uid=#{uid} and mgid=#{mgid}")
    public List<Favorite> findFavoriteByUidAndMgid(@Param("uid") long uid, @Param("mgid") long mgid, @Param("status") int status);


    /**
     * 统计公司的收藏条数
     *
     * @param cid
     * @return
     */
    @Select("select count(1) from db_favorite where status=1 and cid=#{cid}")
    public int countFavorite(@Param("cid") long cid);


    /**
     * 统计用户的收藏条数
     *
     * @param uid
     * @return
     */
    @Select("select count(1) from db_favorite where status=1 and uid=#{uid}")
    public int countFavoriteForUser(@Param("uid") long uid);

    /**
     * 分页读取收藏的商品信息
     *
     * @param cid
     * @param page
     * @param limit
     * @return
     */
    @Select("select * from db_goods where status=1 and id in(select mgid from db_favorite where  status=1 and cid=#{cid}) limit #{page},#{limit}")
    public List<Goods> findFavoriteForCid(@Param("cid") long cid, @Param("page") int page, @Param("limit") int limit);

    /**
     * 分页读取收藏的商品信息
     *
     * @param cid
     * @param page
     * @param limit
     * @return
     */
    @Select("select * from db_goods where status=1 and company_id=#{scid} and id in(select mgid from db_favorite where  status=1 and cid=#{cid}) limit #{page},#{limit}")
    public List<Goods> findFavoriteForCidAndScid(@Param("scid") long scid,@Param("cid") long cid, @Param("page") int page, @Param("limit") int limit);


    /**
     * 分页读取收藏用户的商品信息
     * @param uid
     * @param page
     * @param limit
     * @return
     */
    @Select("select * from db_goods where   id in(select mgid from db_favorite where  status=1 and uid=#{uid}) limit #{page},#{limit}")
    public List<Goods> findFavoriteForUid(@Param("uid") long uid, @Param("page") int page, @Param("limit") int limit);
}
