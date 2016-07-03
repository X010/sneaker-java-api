package com.sneaker.mall.api.dao.admin;

import com.sneaker.mall.api.model.Goods;
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
public interface GoodsDao {

    /**
     * 根据公司ID读取商品信息
     *
     * @param cid
     * @return
     */
    @Select("select * from db_goods where  publish=1 and flagdel=0 and company_id=#{cid} order by top desc,`order` desc,tid desc,id desc")
    public List<Goods> findGoodsByCid(@Param("cid") long cid);

    /**
     * 根据公司ID与编码条码读取商品信息
     *
     * @param cid
     * @param barcode
     * @return
     */
    @Select("select * from db_goods where  publish=1 and flagdel=0 and company_id=#{cid} and barcode=#{barcode} limit 1")
    public Goods findGoodsByCidAndBarCode(@Param("cid") long cid, @Param("barcode") String barcode);


    /**
     * 根据公司ID获取商品数
     *
     * @param cid
     * @return
     */
    @Select("select count(1) from db_goods where  publish=1 and flagdel=0 and company_id=#{cid} and (FIND_IN_SET(#{cctype},cctype)) and ((sids is NULL)or(FIND_IN_SET(#{sid},sids))) ")
    public int countGoodsByCid(@Param("cid") long cid, @Param("cctype") String cctype, @Param("sid") String sid);

    /**
     * 分页读取公司商品信息
     *
     * @param cid
     * @param start
     * @param rows
     * @return
     */
    @Select("select * from db_goods where  publish=1 and flagdel=0 and company_id=#{cid} and  (FIND_IN_SET(#{cctype},cctype)) and ((sids is NULL)or(FIND_IN_SET(#{sid},sids)))  order by top desc,`order` desc,tid desc,id desc limit #{start},#{rows}")
    public List<Goods> findGoodsByCidAndPage(@Param("cid") long cid, @Param("start") int start, @Param("rows") int rows, @Param("cctype") String cctype, @Param("sid") String sid);


    /**
     * 根据公司ID与商品ID读取商品信息
     *
     * @param cid
     * @param gid
     * @return
     */
    @Select("select * from db_goods where company_id=#{cid} and gid=#{gid}")
    public Goods findGoodsByCidAndGid(@Param("cid") long cid, @Param("gid") long gid);


    /**
     * 根据公司及分类读取公司商品
     *
     * @param cid
     * @param id
     * @param limit
     * @return
     */
    @Select("select * from db_goods where company_id=#{cid} and flagdel=0 and publish=1 and cateid=#{cateid} and  (FIND_IN_SET(#{cctype},cctype)) and ((sids is NULL)or(FIND_IN_SET(#{sid},sids)))  order by top desc,`order` desc,tid desc,id desc limit 0,#{limit}")
    public List<Goods> findGoodsByCidAndCateIdAndLimit(@Param("cid") long cid, @Param("cateid") long id, @Param("limit") int limit, @Param("cctype") String cctype, @Param("sid") String sid);


    /**
     * 获取热够商品
     *
     * @param cid
     * @param limit
     * @param cctype
     * @param sid
     * @return
     */
    @Select("select * from db_goods where company_id=#{cid} and flagdel=0 and publish=1 and  (FIND_IN_SET(#{cctype},cctype)) and ((sids is NULL)or(FIND_IN_SET(#{sid},sids)))  order by salesNum desc limit 0,#{limit}")
    public List<Goods> findHotGoods(@Param("cid") long cid, @Param("limit") int limit, @Param("cctype") String cctype, @Param("sid") String sid);

    /**
     * 统计公司及分类读取公司商品
     *
     * @param cid
     * @param id
     * @return
     */
    @Select("select count(1) from db_goods where company_id=#{cid} and flagdel=0 and publish=1 and cateid=#{cateid} and (FIND_IN_SET(#{cctype},cctype)) and ((sids is NULL)or(FIND_IN_SET(#{sid},sids))) ")
    public int countGoodsByCidAndCateId(@Param("cid") long cid, @Param("cateid") long id, @Param("cctype") String cctype, @Param("sid") String sid);


    /**
     * 根据公司ID读取商品信息
     *
     * @param id
     * @return
     */
    @Select("select * from db_goods where id=#{id} and flagdel=0 and publish=1")
    public Goods findGoodsById(@Param("id") long id);

    /**
     * 根据公司ID读取商品信息
     *
     * @param id
     * @return
     */
    @Select("select * from db_goods where id=#{id}")
    public Goods findGoodsByIdAndNoPublish(@Param("id") long id);


    /**
     * 根据公司ID和栏目ID读取商品信息
     *
     * @param cid
     * @param cateid
     * @return
     */
    @Select("select * from db_goods where company_id=#{cid} and flagdel=0 and publish=1 and cateid=#{cateid} and (FIND_IN_SET(#{cctype},cctype)) and ((sids is NULL)or(FIND_IN_SET(#{sid},sids))) order by top desc,`order` desc,tid desc,id desc")
    public List<Goods> findGoodsByCidAndCateid(@Param("cid") long cid, @Param("cateid") long cateid, @Param("cctype") String cctype, @Param("sid") String sid);

    /**
     * 按公司读取分类下面的商品
     *
     * @param cid
     * @param typeid
     * @return
     */
    @Select("select * from db_goods where company_id=#{cid} and flagdel=0 and publish=1 and tid=#{typeid} and   (FIND_IN_SET(#{cctype},cctype)) and ((sids is NULL)or(FIND_IN_SET(#{sid},sids)))   order by top desc,`order` desc,tid desc,id desc")
    public List<Goods> findGoodsByCidAndTypeid(@Param("cid") long cid, @Param("typeid") long typeid, @Param("cctype") String cctype, @Param("sid") String sid);

    /**
     * 根据公司与分类Code读取商品信息
     *
     * @param cid
     * @param code
     * @return
     */
    @Select("select * from db_goods where company_id=#{cid} and flagdel=0 and publish=1 and  (FIND_IN_SET(#{cctype},cctype)) and ((sids is NULL)or(FIND_IN_SET(#{sid},sids)))    and tcode like #{tcode} order by top desc,`order` desc,tid desc,id desc limit #{start},#{limit}")
    public List<Goods> findGoodsByCidAndTypeCode(@Param("start") int start, @Param("limit") int limit, @Param("cid") long cid, @Param("tcode") String code, @Param("cctype") String cctype, @Param("sid") String sid);


    /**
     * 根据公司ID与分类Code统计商品条数
     *
     * @param cid
     * @param code
     * @return
     */
    @Select("select count(1) from db_goods where company_id=#{cid} and flagdel=0 and publish=1 and (FIND_IN_SET(#{cctype},cctype)) and ((sids is NULL)or(FIND_IN_SET(#{sid},sids)))  and tcode like #{tcode}")
    public int countGoodsByCidAndTypeCode(@Param("cid") long cid, @Param("tcode") String code, @Param("cctype") String cctype, @Param("sid") String sid);

    /**
     * 根据公司和关键字
     *
     * @param cid
     * @param keyword
     * @return
     */
    @Select("select * from  db_goods where company_id=#{cid} and flagdel=0 and publish=1 and   (FIND_IN_SET(#{cctype},cctype)) and ((sids is NULL)or(FIND_IN_SET(#{sid},sids)))   and gname like #{keywrod} order by top desc,`order` desc,tid desc,id desc")
    public List<Goods> findGoodsByKeywordAndCid(@Param("cid") long cid, @Param("keywrod") String keyword, @Param("cctype") String cctype, @Param("sid") String sid);

    /**
     * 根据商品ID获取赠送商品信息
     *
     * @param mgid
     * @return
     */
    @Select("select *  from db_goods as g,db_goods_bind as bd where  g.id=bd.child_mgid and bd.mgid=#{mgid}")
    public List<Goods> findGoodsByMarket(@Param("mgid") long mgid);

    /**
     * 根据商品销售数量
     *
     * @param mgid
     */
    @Update("update db_goods set salesNum=(salesNum+#{saleNum}) where id=#{mgid}")
    public void updateGoodsSalesNum(@Param("mgid") long mgid, @Param("saleNum") int saleNum);
}
