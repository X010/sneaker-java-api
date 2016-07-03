package com.sneaker.mall.api.service;

import com.sneaker.mall.api.model.Goods;
import com.sneaker.mall.api.model.Order;

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
public interface GoodsService {

    /**
     * 根据公司ID读取商品信息
     *
     * @param cid    公司ID
     * @param cateid 分类ID
     * @param ccid   客户ID
     * @return
     */
    public List<Goods> getGoodsBycid(long cid, long cateid, long ccid);

    /**
     * 获取热够商品
     *
     * @param cid
     * @param limit
     * @param ccid
     * @return
     */
    public List<Goods> getHotGoodsByCid(long cid, int limit, long ccid);

    /**
     * 根据公司ID和类型获取商品列表
     *
     * @param cid
     * @param typeid
     * @param ccid   客户ID
     * @return
     */
    public List<Goods> getGoodsByTypeIdAndCid(long cid, long typeid, long ccid);

    /**
     * 根据公司ID与类型的编码获取商品列表
     *
     * @param cid
     * @param code
     * @param ccid 客户ID
     * @return
     */
    public List<Goods> getGoodsByCidAndTypeCode(int page, int limit, long cid, String code, long ccid);

    /**
     * 根据仓库与类型的编码获取商品列表
     * @param page
     * @param limit
     * @param code
     * @param ccid
     * @return
     */
    public List<Goods> getGoodsByCidAndTypeCode(int page, int limit, long cid, String code, String cctype, String sid);

    /**
     * 根据公司ID与类型统计商品个数
     *
     * @param cid
     * @param code
     * @param ccid 客户ID
     * @return
     */
    public int countGoodsByCidAndTypeCode(long cid, String code, long ccid);

    /**
     * 根据公司ID与类型统计商品个数 根据sid, cctype
     *
     * @param cid
     * @param code
     * @param ccid 客户ID
     * @return
     */
    public int countGoodsByCidAndTypeCode(long cid, String code, String cctype, String sid);

    /**
     * 分页读取公司商品信息
     *
     * @param cid
     * @param pageNumber
     * @param size
     * @param ccid       客户ID
     * @return
     */
    public List<Goods> getGoodsByCidAndPage(long cid, int pageNumber, int size, long ccid);

    /**
     * 分页读取公司商品信息 根据cctype, sid
     *
     * @param cid
     * @param pageNumber
     * @param size
     * @param ccid       客户ID
     * @return
     */
    public List<Goods> getGoodsByCidAndPage(long cid, int pageNumber, int size, String cctype, String sid);

    /**
     * 统计公司商品
     *
     * @param cid
     * @param ccid 客户ID
     * @return
     */
    public int countGoodsByCid(long cid, long ccid);

    /**
     * 统计公司商品
     *
     * @param cid
     * @param ccid 客户ID
     * @return
     */
    public int countGoodsByCid(long cid, String cctype, String sid);

    /**
     * 根据公司及栏目读取商品信息
     *
     * @param cid
     * @param cateid
     * @param limit
     * @param ccid   客户ID
     * @return
     */
    public List<Goods> getGoodsByCidAndCateidAndLimit(long cid, long cateid, int limit, long ccid);

    /**
     * 根据公司及栏目读取商品信息 cctype sid
     * @param cid
     * @param cateid
     * @param limit
     * @param cctype
     * @param sid
     * @return
     */
    public List<Goods> getGoodsByCidAndCateidAndLimit(long cid, long cateid, int limit, String cctype, String sid);

    /**
     * 统计公司及栏目下面的商品统总数
     *
     * @param cid
     * @param cateid
     * @param ccid   客户ID
     * @return
     */
    public int countGoodsByCidAndCateid(long cid, long cateid, long ccid);

    /**
     * 统计公司及栏目下面的商品统总数 cctype sid
     * @param cid
     * @param cateid
     * @param cctype
     * @param sid
     * @return
     */
    public int countGoodsByCidAndCateid(long cid, long cateid, String cctype, String sid);

    /**
     * 根据公司ID与商品ID读取商品信息
     *
     * @param gid
     * @param cid
     * @return
     */
    public Goods getGoodsByGidAndCid(long gid, long cid);

    /**
     * 根据ID读取商品基本信息
     *
     * @param id
     * @return
     */
    public Goods getGoodsById(long id, long cid, long ccid);

    /**
     * 根据BarCode查询商品
     *
     * @param barcode
     * @return
     */
    public Goods getGoodsByBarCode(String barcode, long cid, long ccid);

    /**
     * 根据公司ID和商品KEYWROD搜索
     *
     * @param keywrod
     * @param cid
     * @param ccid    客户ID
     * @return
     */
    public List<Goods> getGoodsByKeywordAndCid(String keywrod, long cid, long ccid);

    /**
     * 根据商品ID获取子商信息列表
     *
     * @param id
     * @return
     */
    public List<Goods> getChlidGoodsByMainGoodsId(long id, long cid, long ccid);


    /**
     * 根据供应商与自己公司获取收藏
     *
     * @param scid
     * @param cid
     * @param page
     * @param limit
     * @return
     */
    public List<Goods> getFavoriteGoodsCidAndScid(long scid, long cid, int page, int limit);

    /**
     * 根据用户获取收藏列表
     *
     * @param uid
     * @param page
     * @param limit
     * @return
     */
    public List<Goods> getFavoriteGoodsForUser(long uid, int page, int limit, long cid, long ccid);


    /**
     * 补充商品的绑定信息
     *
     * @param goods
     */
    public void paddingSingleMarket(Goods goods, long cid, long ccid);


    /**
     * 填充商品信息
     *
     * @param goodses
     */
    public void paddingMarket(List<Goods> goodses, long cid, long ccid);

    /**
     * 一键下单，获取该客户可以从该订单中再次下单的商品
     *
     * @param order
     * @return
     */
    public List<Goods> getOrderForAKeyOrder(Order order, long ccid, long scid);

    /**
     * 生成图片地址
     *
     * @param goods
     */
    public void genartePhoto(Goods goods);
}
