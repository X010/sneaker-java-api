package com.sneaker.mall.api.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.sneaker.mall.api.dao.admin.ShopPriceDao;
import com.sneaker.mall.api.model.Goods;
import com.sneaker.mall.api.model.ShopPrice;
import com.sneaker.mall.api.service.PriceService;
import com.sneaker.mall.api.util.CONST;
import com.sneaker.mall.api.util.HttpClientUtil;
import com.sneaker.mall.api.util.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
public class B2PriceServiceImpl implements PriceService {
    private Logger logger = LoggerFactory.getLogger(B2PriceServiceImpl.class);

    @Autowired
    @Qualifier("readPriceUrl")
    private String erp_price_url;

    @Autowired
    private ShopPriceDao shopPriceDao;


    @Override
    public void supplementGoodsPrice(List<Goods> goodses, long cid) {
        if (goodses != null && goodses.size() > 0) {
            HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();
            List<GoodsListForErp> goodsListForErps = goodses.stream()
                    .filter(gd -> gd.getIsbind() == 0 && gd.getPkgSize() == 0)
                    .map(goods -> new GoodsListForErp(goods.getGcode(), goods.getCompany_id())).collect(Collectors.toList());
            Map<String, String> params = new HashMap<>();
            params.put("cid", String.valueOf(cid));
            params.put("scid", String.valueOf(goodses.get(0).getCompany_id()));
            params.put("goods_list", JsonParser.simpleJson(goodsListForErps));
            logger.info(params.toString());
            try {
                String res = httpClientUtil.getResponseBodyAsString(this.erp_price_url, params);
                if (!Strings.isNullOrEmpty(res)) {
                    //对商品信息
                    ResponseJson responseJson = (ResponseJson) JsonParser.StringToJsonVideo(res, ResponseJson.class);
                    if (responseJson != null && "0000".equals(responseJson.status)) {
                        final Map<String, Float> prices = (Map<String, Float>) JsonParser.parser(responseJson.getMsg(), new TypeReference<Map<String, Float>>() {
                        });
                        if (prices != null) {
                            goodses.stream().forEach(goods -> {
                                if (goods.getPkgSize() == 0 && goods.getIsbind() == 0) {
                                    if (prices.get(goods.getGcode()) != null && goods.getIsbind() != CONST.MARKET_GIVEWAYA_GIVE) {
                                        goods.setPrice(prices.get(goods.getGcode()));
                                    }
                                }
                            });
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void supplementGoodsPrice(List<Goods> goodses, long cid, long cctype) {
        if (goodses != null && goodses.size() > 0) {
            HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();
            List<GoodsListForErp> goodsListForErps = goodses.stream()
                    .filter(gd -> gd.getIsbind() == 0 && gd.getPkgSize() == 0)
                    .map(goods -> new GoodsListForErp(goods.getGcode(), goods.getCompany_id())).collect(Collectors.toList());
            Map<String, String> params = new HashMap<>();
            params.put("cid", String.valueOf(cid));
            params.put("scid", String.valueOf(goodses.get(0).getCompany_id()));
            params.put("cctype", String.valueOf(cctype));
            params.put("goods_list", JsonParser.simpleJson(goodsListForErps));
            logger.info(params.toString());
            try {
                String res = httpClientUtil.getResponseBodyAsString(this.erp_price_url, params);
                if (!Strings.isNullOrEmpty(res)) {
                    //对商品信息
                    ResponseJson responseJson = (ResponseJson) JsonParser.StringToJsonVideo(res, ResponseJson.class);
                    if (responseJson != null && "0000".equals(responseJson.status)) {
                        final Map<String, Float> prices = (Map<String, Float>) JsonParser.parser(responseJson.getMsg(), new TypeReference<Map<String, Float>>() {
                        });
                        if (prices != null) {
                            goodses.stream().forEach(goods -> {
                                if (goods.getPkgSize() == 0 && goods.getIsbind() == 0) {
                                    if (prices.get(goods.getGcode()) != null && goods.getIsbind() != CONST.MARKET_GIVEWAYA_GIVE) {
                                        goods.setPrice(prices.get(goods.getGcode()));
                                    }
                                }
                            });
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Map<String, Float> supplementGoodsPriceReturnMap(List<Goods> goodses, long cid) {
        if (goodses != null && goodses.size() > 0) {
            HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();
            List<GoodsListForErp> goodsListForErps = goodses.stream()
                    .filter(gd -> gd.getIsbind() == 0)
                    .map(goods -> new GoodsListForErp(goods.getGcode(), goods.getCompany_id())).collect(Collectors.toList());
            Map<String, String> params = new HashMap<>();
            params.put("cid", String.valueOf(cid));
            params.put("scid", String.valueOf(goodses.get(0).getCompany_id()));
            params.put("goods_list", JsonParser.simpleJson(goodsListForErps));
            try {
                String res = httpClientUtil.getResponseBodyAsString(this.erp_price_url, params);
                if (!Strings.isNullOrEmpty(res)) {
                    //对商品信息
                    ResponseJson responseJson = (ResponseJson) JsonParser.StringToJsonVideo(res, ResponseJson.class);
                    if (responseJson != null && "0000".equals(responseJson.status)) {
                        final Map<String, Float> prices = (Map<String, Float>) JsonParser.parser(responseJson.getMsg(), new TypeReference<Map<String, Float>>() {
                        });
                        if (prices != null) {
                            return prices;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ShopPrice getShopPriceByCcTypeAndSidAndMgid(long cid, int cctype, long sid, long mgid) {
        Preconditions.checkArgument(cctype > 0);
        Preconditions.checkArgument(sid > 0);
        Preconditions.checkArgument(mgid > 0);
        return this.shopPriceDao.findShopPriceByCctypeAndSid(cid, cctype, sid, mgid);
    }

    /**
     * 返回信息
     */
    public static class ResponseJson {
        private int err;
        private String status;
        private String msg;

        public int getErr() {
            return err;
        }

        public void setErr(int err) {
            this.err = err;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    /**
     * 参数实体类
     */
    public static class GoodsListForErp {

        public GoodsListForErp(String gcode, long scid) {
            this.gcode = gcode;
            this.scid = scid;
        }

        /**
         * 商品ID
         */
        private String gcode;
        /**
         * 供应商ID
         */
        private long scid;

        public String getGcode() {
            return gcode;
        }

        public void setGcode(String gcode) {
            this.gcode = gcode;
        }

        public long getScid() {
            return scid;
        }

        public void setScid(long scid) {
            this.scid = scid;
        }
    }
}
