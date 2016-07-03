package com.sneaker.mall.api.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.sneaker.mall.api.dao.admin.FavoriteDao;
import com.sneaker.mall.api.dao.admin.GoodsDao;
import com.sneaker.mall.api.dao.info.CompanyDao;
import com.sneaker.mall.api.model.*;
import com.sneaker.mall.api.service.GoodsService;
import com.sneaker.mall.api.service.MarketService;
import com.sneaker.mall.api.service.PriceService;
import com.sneaker.mall.api.util.CONST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
public class B2GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private PriceService priceService;

    @Autowired
    private MarketService marketService;

    @Autowired
    private FavoriteDao favoriteDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    @Qualifier("default_img_url")
    private String default_img_url;

    @Autowired
    @Qualifier("img_server_prefix")
    private String img_server_prefix;

    @Override
    public List<Goods> getGoodsBycid(long cid, long cateid, long ccid) {
        Preconditions.checkArgument(cid > 0);
        Preconditions.checkArgument(cateid > 0);
        //查询客户的客户类型与仓库
        List<Goods> goodses = Lists.newArrayList();
        Customer customer = this.companyDao.findCustomerByCompanyIdAndCoustomerCompanyId(cid, ccid);
        if (customer != null) {
            goodses = this.goodsDao.findGoodsByCidAndCateid(cid, cateid, customer.getCctype(), customer.getSid());
            if (goodses != null) {
                //补充绑定信息
                this.paddingMarket(goodses, cid, ccid);
            }
        }
        return goodses;
    }

    @Override
    public List<Goods> getHotGoodsByCid(long cid, int limit, long ccid) {
        Preconditions.checkArgument(cid > 0);
        Preconditions.checkArgument(ccid > 0);
        Preconditions.checkArgument(limit > 0);
        List<Goods> goodses = null;
        Customer customer = this.companyDao.findCustomerByCompanyIdAndCoustomerCompanyId(cid, ccid);
        if (customer != null) {
            goodses = this.goodsDao.findHotGoods(cid, limit, customer.getCctype(), customer.getSid());
            if (goodses != null) {
                //加载是不是绑定的
                this.paddingMarket(goodses, cid, ccid);
            }
        }
        return goodses;
    }

    @Override
    public List<Goods> getGoodsByTypeIdAndCid(long cid, long typeid, long ccid) {
        Preconditions.checkArgument(cid > 0);
        Preconditions.checkArgument(typeid > 0);
        List<Goods> goodses = null;
        Customer customer = this.companyDao.findCustomerByCompanyIdAndCoustomerCompanyId(cid, ccid);
        if (customer != null) {
            goodses = this.goodsDao.findGoodsByCidAndTypeid(cid, typeid, customer.getCctype(), customer.getSid());
            if (goodses != null) {
                //补充绑定信息
                this.paddingMarket(goodses, cid, ccid);
            }
        }
        return goodses;
    }

    @Override
    public List<Goods> getGoodsByCidAndTypeCode(int page, int limit, long cid, String code, long ccid) {
        Preconditions.checkArgument(cid > 0);
        Preconditions.checkNotNull(code);
        int start = (page - 1) * limit;
        List<Goods> goodses = null;
        Customer customer = this.companyDao.findCustomerByCompanyIdAndCoustomerCompanyId(cid, ccid);
        if (customer != null) {
            goodses = this.goodsDao.findGoodsByCidAndTypeCode(start, limit, cid, code, customer.getCctype(), customer.getSid());
            if (goodses != null) {
                //加载是不是绑定的
                this.paddingMarket(goodses, cid, ccid);
            }
        }
        return goodses;
    }

    @Override
    public List<Goods> getGoodsByCidAndTypeCode(int page, int limit, long cid, String code, String cctype, String sid) {
        Preconditions.checkNotNull(cctype);
        Preconditions.checkNotNull(sid);
        Preconditions.checkNotNull(code);
        int start = (page - 1) * limit;
        List<Goods> goodses = this.goodsDao.findGoodsByCidAndTypeCode(start, limit, cid, code, cctype, sid);
        if (goodses != null) {
            //加载是不是绑定的
            this.paddingMarket(goodses, cid, cctype, sid);
        }

        return goodses;
    }

    @Override
    public int countGoodsByCidAndTypeCode(long cid, String code, long ccid) {
        Preconditions.checkArgument(cid > 0);
        int count = 0;
        Customer customer = this.companyDao.findCustomerByCompanyIdAndCoustomerCompanyId(cid, ccid);
        if (customer != null) {
            count = this.goodsDao.countGoodsByCidAndTypeCode(cid, code, customer.getCctype(), customer.getSid());
        }
        return count;
    }

    @Override
    public int countGoodsByCidAndTypeCode(long cid, String code, String cctype, String sid) {
        Preconditions.checkArgument(cid > 0);
        int count = this.goodsDao.countGoodsByCidAndTypeCode(cid, code, cctype, sid);
        return count;
    }

    @Override
    public List<Goods> getGoodsByCidAndPage(long cid, int pageNumber, int size, long ccid) {
        Preconditions.checkArgument(cid > 0);
        Preconditions.checkArgument(pageNumber > 0);
        Preconditions.checkArgument(size > 0);
        int start = (pageNumber - 1) * size;
        List<Goods> goodses = null;
        Customer customer = this.companyDao.findCustomerByCompanyIdAndCoustomerCompanyId(cid, ccid);
        if (customer != null) {
            goodses = this.goodsDao.findGoodsByCidAndPage(cid, start, size, customer.getCctype(), customer.getSid());
            if (goodses != null) {
                //加载是不是绑定的
                this.paddingMarket(goodses, cid, ccid);
            }
        }
        return goodses;
    }

    @Override
    public List<Goods> getGoodsByCidAndPage(long cid, int pageNumber, int size, String cctype, String sid) {
        Preconditions.checkArgument(cid > 0);
        Preconditions.checkArgument(pageNumber > 0);
        Preconditions.checkArgument(size > 0);
        int start = (pageNumber - 1) * size;
        List<Goods> goodses = null;

        goodses = this.goodsDao.findGoodsByCidAndPage(cid, start, size, cctype, sid);
        if (goodses != null) {
            //加载是不是绑定的
            this.paddingMarket(goodses, cid, cctype, sid);
        }

        return goodses;
    }

    @Override
    public int countGoodsByCid(long cid, long ccid) {
        Preconditions.checkArgument(cid > 0);
        int count = 0;
        Customer customer = this.companyDao.findCustomerByCompanyIdAndCoustomerCompanyId(cid, ccid);
        if (customer != null) {
            count = this.goodsDao.countGoodsByCid(cid, customer.getCctype(), customer.getSid());
        }
        return count;
    }

    @Override
    public int countGoodsByCid(long cid, String cctype, String sid) {
        Preconditions.checkArgument(cid > 0);
        int count = 0;
        count = this.goodsDao.countGoodsByCid(cid, cctype, sid);

        return count;
    }

    @Override
    public List<Goods> getGoodsByCidAndCateidAndLimit(long cid, long cateid, int limit, long ccid) {
        Preconditions.checkArgument(cid > 0);
        Preconditions.checkArgument(cateid > 0);
        Preconditions.checkArgument(limit > 0);
        List<Goods> goodses = null;
        Customer customer = this.companyDao.findCustomerByCompanyIdAndCoustomerCompanyId(cid, ccid);
        if (customer != null) {
            goodses = this.goodsDao.findGoodsByCidAndCateIdAndLimit(cid, cateid, limit, customer.getCctype(), customer.getSid());
            if (goodses != null) {
                //加载是不是绑定的
                this.paddingMarket(goodses, cid, ccid);
            }
        }
        return goodses;
    }

    @Override
    public List<Goods> getGoodsByCidAndCateidAndLimit(long cid, long cateid, int limit, String cctype, String sid) {
        Preconditions.checkNotNull(cctype);
        Preconditions.checkNotNull(sid);
        Preconditions.checkArgument(cateid > 0);
        Preconditions.checkArgument(limit > 0);

        List<Goods> goodses = this.goodsDao.findGoodsByCidAndCateIdAndLimit(cid, cateid, limit, cctype, sid);
        if (goodses != null) {
            //加载是不是绑定的
            this.paddingMarket(goodses, cid, cctype, sid);
        }

        return goodses;
    }

    @Override
    public int countGoodsByCidAndCateid(long cid, long cateid, long ccid) {
        Preconditions.checkArgument(cid > 0);
        Preconditions.checkArgument(cateid > 0);
        int count = 0;
        Customer customer = this.companyDao.findCustomerByCompanyIdAndCoustomerCompanyId(cid, ccid);
        if (customer != null) {
            count = this.goodsDao.countGoodsByCidAndCateId(cid, cateid, customer.getCctype(), customer.getSid());
        }
        return count;
    }

    @Override
    public int countGoodsByCidAndCateid(long cid, long cateid, String cctype, String sid) {
        Preconditions.checkArgument(cid > 0);
        Preconditions.checkArgument(cateid > 0);
        int count = this.goodsDao.countGoodsByCidAndCateId(cid, cateid, cctype, sid);
        return count;
    }

    @Override
    public Goods getGoodsByGidAndCid(long gid, long cid) {
        return null;
    }

    @Override
    public Goods getGoodsById(long id, long cid, long ccid) {
        Preconditions.checkArgument(id > 0);
        Goods goods = this.goodsDao.findGoodsById(id);
        if (goods != null) {
            //补充绑定信息
            this.genartePhoto(goods);
            this.paddingSingleMarket(goods, cid, ccid);
        }
        return goods;
    }

    @Override
    public Goods getGoodsByBarCode(String barcode, long cid, long ccid) {
        Preconditions.checkNotNull(barcode);
        Preconditions.checkArgument(cid > 0);
        Goods goods = this.goodsDao.findGoodsByCidAndBarCode(cid, barcode);
        if (goods != null) {
            //补充绑定信息
            this.genartePhoto(goods);
            this.paddingSingleMarket(goods, cid, ccid);
        }
        return goods;
    }

    @Override
    public List<Goods> getGoodsByKeywordAndCid(String keywrod, long cid, long ccid) {
        Preconditions.checkNotNull(keywrod);
        Preconditions.checkArgument(cid > 0);
        List<Goods> goodses = null;
        Customer customer = this.companyDao.findCustomerByCompanyIdAndCoustomerCompanyId(cid, ccid);
        if (customer != null) {
            goodses = this.goodsDao.findGoodsByKeywordAndCid(cid, keywrod, customer.getCctype(), customer.getSid());
            if (goodses != null) {
                //加载是不是绑定的
                this.paddingMarket(goodses, cid, ccid);
            }
        }
        return goodses;
    }

    @Override
    public List<Goods> getFavoriteGoodsCidAndScid(long scid, long cid, int page, int limit) {
        Preconditions.checkArgument(cid > 0);
        Preconditions.checkArgument(scid > 0);
        Preconditions.checkArgument(page > 0);
        Preconditions.checkArgument(limit > 0);
        int start = (page - 1) * limit;
        List<Goods> goodses = this.favoriteDao.findFavoriteForCidAndScid(scid, cid, start, limit); //考虑这里面是否带入价格与营销活动信息
        if (goodses != null) {
            //加载是不是绑定的
            this.paddingMarket(goodses, scid, cid);
        }
        return goodses;
    }

    @Override
    public List<Goods> getFavoriteGoodsForUser(long uid, int page, int limit, long cid, long ccid) {
        Preconditions.checkArgument(uid > 0);
        Preconditions.checkArgument(page > 0);
        Preconditions.checkArgument(limit > 0);
        int start = (page - 1) * limit;
        List<Goods> goodses = this.favoriteDao.findFavoriteForUid(uid, start, limit);
        if (goodses != null) {
            //加载是不是绑定的
            this.paddingMarket(goodses, cid, ccid);
        }
        return goodses;
    }

    @Override
    public List<Goods> getChlidGoodsByMainGoodsId(long id, long cid, long ccid) {
        Preconditions.checkNotNull(id > 0);
        Goods mainGoods = this.getGoodsById(id, cid, ccid);
        if (mainGoods != null && mainGoods.getIsbind() == 1) {
            List<Goods> goodses = this.goodsDao.findGoodsByMarket(id);
            if (goodses != null) {
                goodses.stream().forEach(goods -> {
                    this.genartePhoto(goods);
                });
            }
            return goodses;
        }
        return null;
    }

    /**
     * 填充商品信息
     *
     * @param goodses
     */
    public void paddingMarket(List<Goods> goodses, long cid, long ccid) {
        Preconditions.checkNotNull(goodses);
        goodses.stream().forEach(goods -> {
            this.genartePhoto(goods);
            goods.setContent("");//读取列表均不返回Content
            if (goods.getIsbind() == 1 || goods.getPkgSize() == 1 || goods.getShop_price() == 1) {
                //是绑定商品,读取绑定商品信息列表
                this.paddingSingleMarket(goods, cid, ccid);
            }
        });
    }

    /**
     * 填充商品信息  根据sid,cctype
     *
     * @param goodses
     */
    public void paddingMarket(List<Goods> goodses, long cid, String cctype, String sid) {
        Preconditions.checkNotNull(goodses);
        goodses.stream().forEach(goods -> {
            this.genartePhoto(goods);
            goods.setContent("");//读取列表均不返回Content
            if (goods.getIsbind() == 1 || goods.getPkgSize() == 1 || goods.getShop_price() == 1) {
                //是绑定商品,读取绑定商品信息列表
                this.paddingSingleMarket(goods, cid, cctype, sid);
            }
        });
    }

    @Override
    public List<Goods> getOrderForAKeyOrder(Order order, long ccid, long scid) {
        Preconditions.checkNotNull(order);
        Preconditions.checkArgument(ccid > 0);
        Preconditions.checkArgument(scid > 0);
        List<OrderItem> orderItems = order.getItems();
        List<Goods> goodses = Lists.newArrayList();
        if (orderItems != null) {
            orderItems.stream().forEach(orderItem -> {
                if (orderItem.getGiveaway() != CONST.MARKET_GIVEWAYA_GIVE) {
                    Goods goods = null;
                    if (orderItem.getBindId() > 0) {
                        //打包商品
                        goods = this.getGoodsById(orderItem.getBindId(), scid, ccid);
                    } else {
                        //单号
                        goods = this.getGoodsById(orderItem.getMgid(), scid, ccid);
                    }
                    if (goods != null && goods.getPublish() == CONST.GOODS_PUBLISH_ONLINE) {
                        //判断商品是不是打包商品，如果是打包商品需要判断该客户是否可以购买
                        if (goods.getIsbind() == 1) {
                            //判断该商品是不是可以卖给该客户类型，及仓库的判断
                            Customer customer = this.companyDao.findCustomerByCompanyIdAndCoustomerCompanyId(scid, ccid);
                            if (customer != null) {
                                //判断客户类型与仓库
                                if (("," + goods.getCctype() + ",").indexOf("," + customer.getCctype() + ",") >= 0 && ("," + goods.getSids() + ",").indexOf("," + customer.getSid() + ",") >= 0) {
                                    goodses.add(goods);
                                }
                            }
                        } else {
                            goodses.add(goods);
                        }
                    }
                }
            });
        }
        return goodses;
    }


    /**
     * 补充商品的绑定信息
     *
     * @param goods
     */
    public void paddingSingleMarket(Goods goods, long cid, long ccid) {
        //设置商品价格
        //根据公司ID与公司ID读取客户类型与仓库信息
        Customer customer = this.companyDao.findCustomerByCompanyIdAndCoustomerCompanyId(cid, ccid);
        if (customer != null) {
            ShopPrice shopPrice = this.priceService.getShopPriceByCcTypeAndSidAndMgid(cid, Integer.valueOf(customer.getCctype())
                    , Integer.valueOf(customer.getSid())
                    , goods.getId());
            if (shopPrice != null) {
                goods.setPrice(shopPrice.getPrice());
            }
        }

        //绑定商品
        if (goods.getIsbind() == 1) {

            //读取绑定信息
            List<Goods> marketGoods = this.goodsDao.findGoodsByMarket(goods.getId());

            if (marketGoods != null) {
                //组合打包信息文字
                Map<Integer, List<Goods>> goodsMap = marketGoods.stream().collect(Collectors.groupingBy(Goods::getGiveaway));
                if (goodsMap != null && goodsMap.size() >= 1) {
                    //拼接绑定商品信息
                    List<Goods> mainGoods = goodsMap.get(CONST.MARKET_GIVEWAYA_MAIN);
                    if (mainGoods != null) {
                        mainGoods.stream().forEach(mGoods -> {
                            this.genartePhoto(mGoods);
                        });
                        goods.setMainGoods(mainGoods);
                    }
                }
            }
        }
    }

    /**
     * 补充商品的绑定信息
     *
     * @param goods
     */
    public void paddingSingleMarket(Goods goods, long cid, String cctype, String sid) {
        //设置商品价格
        //根据公司ID与公司ID读取客户类型与仓库信息
        ShopPrice shopPrice = this.priceService.getShopPriceByCcTypeAndSidAndMgid(cid, Integer.valueOf(cctype)
                , Integer.valueOf(sid)
                , goods.getId());
        if (shopPrice != null) {
            goods.setPrice(shopPrice.getPrice());
        }


        //绑定商品
        if (goods.getIsbind() == 1) {

            //读取绑定信息
            List<Goods> marketGoods = this.goodsDao.findGoodsByMarket(goods.getId());

            if (marketGoods != null) {
                //组合打包信息文字
                Map<Integer, List<Goods>> goodsMap = marketGoods.stream().collect(Collectors.groupingBy(Goods::getGiveaway));
                if (goodsMap != null && goodsMap.size() >= 1) {
                    //拼接绑定商品信息
                    List<Goods> mainGoods = goodsMap.get(CONST.MARKET_GIVEWAYA_MAIN);
                    if (mainGoods != null) {
                        mainGoods.stream().forEach(mGoods -> {
                            this.genartePhoto(mGoods);
                        });
                        goods.setMainGoods(mainGoods);
                    }
                }
            }
        }
    }

    /**
     * 生成图片地址
     *
     * @param goods
     */
    public void genartePhoto(Goods goods) {
        if (!Strings.isNullOrEmpty(goods.getGphoto()) && !"0".equals(goods.getGphoto())) {
            goods.setGphoto(img_server_prefix + "/" + goods.getGcode() + "_" + goods.getGphoto() + ".jpg");
        } else {
            goods.setGphoto(default_img_url);
        }
    }
}
