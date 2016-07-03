package com.sneaker.mall.api.api.saleman;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.sneaker.mall.api.exception.ParameterException;
import com.sneaker.mall.api.exception.SessionException;
import com.sneaker.mall.api.model.Goods;
import com.sneaker.mall.api.model.ResponseMessage;
import com.sneaker.mall.api.service.GoodsService;
import com.sneaker.mall.api.service.PriceService;
import com.sneaker.mall.api.util.CONST;
import com.sneaker.mall.api.util.JsonParser;
import com.sneaker.mall.api.util.RequestUtil;
import com.sneaker.mall.api.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
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
@Controller("salemangoodsapi")
@RequestMapping(value = "/saleman/goods")
public class GoodsApi extends BaseController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private PriceService priceService;

    /**
     * 读取公司下面的商品列表,根据栏目ID
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "forcate.action")
    public void forCate(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<List<Goods>> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
//            String ccid = RequestUtil.validateParam(request, "ccid", responseMessage);
            long ccid = RequestUtil.getLong(request, "ccid", 0);
            String cateid = RequestUtil.validateParam(request, "cateid", responseMessage);

            int limit = RequestUtil.getInt(request, "limit", CONST.DEFAULT_PAGE_SIZE, CONST.LIMIT_MIN, CONST.LIMIT_MAX);
            long lcateid = Long.valueOf(cateid);

            List<Goods> goodses = null;
            int count = 0;
            if (ccid > 0) {

                goodses = this.goodsService.getGoodsByCidAndCateidAndLimit(getLoginUser(request).getCid(), lcateid, limit, ccid);
                count = this.goodsService.countGoodsByCidAndCateid(getLoginUser(request).getCid(), lcateid, ccid);
                if (goodses != null) this.priceService.supplementGoodsPrice(goodses, ccid);
            } else {
                String sid = RequestUtil.validateParam(request, "sid", responseMessage);
                String cctype = RequestUtil.validateParam(request, "cctype", responseMessage);
                goodses = this.goodsService.getGoodsByCidAndCateidAndLimit(getLoginUser(request).getCid(), lcateid, limit, cctype, sid);
                count = this.goodsService.countGoodsByCidAndCateid(getLoginUser(request).getCid(), lcateid, cctype, sid);
                if (goodses != null) this.priceService.supplementGoodsPrice(goodses, getLoginUser(request).getCid(), Long.valueOf(cctype));
            }

            responseMessage.setData(goodses);
            responseMessage.setCount(count);
        } catch (ParameterException pe) {
            pe.printStackTrace();
        } catch (SessionException e1) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("login fail");
        } catch (Exception e) {
            responseMessage.setStatus(400);
            responseMessage.setMessage(e.getMessage());
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }

    /**
     * 根据分类读取商品信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "fortype.action")
    public void forType(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<List<Goods>> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            String typeCode = RequestUtil.getString(request, "code", null);
            long ccid = RequestUtil.getLong(request, "ccid", 0);
            //long sid  = RequestUtil.getLong(request, "sid", 0);
            long cctype = RequestUtil.getLong(request, "cctype", 0);
            int page = RequestUtil.getInt(request, "page", CONST.DEFAULT_PAGE_START);
            int limit = RequestUtil.getInt(request, "limit", CONST.DEFAULT_PAGE_SIZE, CONST.LIMIT_MIN, CONST.LIMIT_MAX);
            if (ccid > 0) {
                if (!Strings.isNullOrEmpty(typeCode)) {
                    String queryTypeCode = typeCode + "%";
                    List<Goods> goodses = this.goodsService.getGoodsByCidAndTypeCode(page, limit, getLoginUser(request).getCid(), queryTypeCode, Long.valueOf(ccid));
                    if (goodses != null && goodses.size() > 0) {
                        this.priceService.supplementGoodsPrice(goodses, ccid, cctype);
                        responseMessage.setData(goodses);
                        responseMessage.setCount(this.goodsService.countGoodsByCidAndTypeCode(getLoginUser(request).getCid(), queryTypeCode, Long.valueOf(ccid)));
                    }
                } else {
                    //读取所有商品
                    List<Goods> goodses = this.goodsService.getGoodsByCidAndPage(getLoginUser(request).getCid(), page, limit, Long.valueOf(ccid));
                    if (goodses != null && goodses.size() > 0) {
                        this.priceService.supplementGoodsPrice(goodses, ccid, cctype);
                        responseMessage.setData(goodses);
                        responseMessage.setCount(this.goodsService.countGoodsByCid(getLoginUser(request).getCid(), Long.valueOf(ccid)));
                    }
                }
            } else {
                //新用户,展示用,有sid, cctype参数, 无ccid
                String sid  = RequestUtil.getString(request, "sid", "");
                if (!Strings.isNullOrEmpty(typeCode)) {
                    String queryTypeCode = typeCode + "%";
                    List<Goods> goodses = this.goodsService.getGoodsByCidAndTypeCode(page, limit, getLoginUser(request).getCid(), queryTypeCode,String.valueOf(cctype), sid);
                    if (goodses != null && goodses.size() > 0) {
                        this.priceService.supplementGoodsPrice(goodses, ccid, cctype);
                        responseMessage.setData(goodses);
                        responseMessage.setCount(this.goodsService.countGoodsByCidAndTypeCode(getLoginUser(request).getCid(), queryTypeCode, String.valueOf(cctype), sid));
                    }
                } else {
                    //读取所有商品
                    List<Goods> goodses = this.goodsService.getGoodsByCidAndPage(getLoginUser(request).getCid(), page, limit, String.valueOf(cctype), sid);
                    if (goodses != null && goodses.size() > 0) {
                        this.priceService.supplementGoodsPrice(goodses, ccid, cctype);
                        responseMessage.setData(goodses);
                        responseMessage.setCount(this.goodsService.countGoodsByCid(getLoginUser(request).getCid(), String.valueOf(cctype), sid));
                    }
                }

            }
        } catch (ParameterException pe) {
            pe.printStackTrace();
        } catch (SessionException e1) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("login fail");
        } catch (Exception e) {
            responseMessage.setStatus(400);
            responseMessage.setMessage(e.getMessage());
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }

    /**
     * 获取热门商品
     * 最多为6个,如果后台配置少于6个,则截断为偶数个
     * @param request
     * @param response
     */
    @RequestMapping(value = "hotgoods.action")
    public void hotGoods(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<List<Goods>> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        int limit = RequestUtil.getInt(request, "limit", 5); //默认获取5个热卖商品
        long ccid = RequestUtil.getLong(request, "ccid", 0);
        if (limit > 6) {
            limit = 6;
        }
        try {
            List<Goods> goodses = this.goodsService.getHotGoodsByCid(getLoginUser(request).getCid(), limit, ccid);
            int size = goodses.size();
            if (goodses != null && size > 0) {
                List<Goods> showGoodSes = null;
                if (size % 2 == 1) {
                    size--;
                    showGoodSes = goodses.subList(0, size);
                } else {
                    showGoodSes = goodses;
                }
                this.priceService.supplementGoodsPrice(showGoodSes, ccid);
                responseMessage.setData(showGoodSes);
                responseMessage.setCount(size);
                //responseMessage.setCount(this.goodsService.countGoodsByCid(getLoginUser(request).getCid(), Long.valueOf(ccid)));
            }
        } catch (ParameterException pe) {
            pe.printStackTrace();
        } catch (SessionException e1) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("login fail");
        } catch (Exception e) {
            responseMessage.setStatus(400);
            responseMessage.setMessage(e.getMessage());
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }


    /**
     * 读取商品详情
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "info.action")
    public void goodsInfo(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<Goods> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        //获取商品ID
        try {
            String ccid = RequestUtil.validateParam(request, "ccid", responseMessage);
            String id = RequestUtil.validateParam(request, "id", responseMessage);
            if (!Strings.isNullOrEmpty(ccid)) {
                Goods goods = this.goodsService.getGoodsById(Long.valueOf(id), getLoginUser(request).getCid(), Long.valueOf(ccid));
                List<Goods> goodses = Lists.newArrayList();
                goodses.add(goods);
                this.priceService.supplementGoodsPrice(goodses, Long.valueOf(ccid));
                if (goodses != null) {
                    goods = goodses.get(0);
                }
                responseMessage.setData(goods);
            }
        } catch (ParameterException pe) {
            pe.printStackTrace();
        } catch (Exception e) {
            responseMessage.setStatus(306);
            responseMessage.setMessage(e.getMessage());
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }

    /**
     * 根据BarCode查询商品
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "barcode.action")
    public void goodsBarCode(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<Goods> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            String barcode = RequestUtil.getString(request, "barcode", null);
            long ccid = RequestUtil.getLong(request, "ccid", 0);
            if (!Strings.isNullOrEmpty(barcode)) {
                Goods goods = this.goodsService.getGoodsByBarCode(barcode, getLoginUser(request).getCid(), ccid);
                if (goods != null) {
                    responseMessage.setData(goods);
                }
            }
        } catch (ParameterException pe) {
            pe.printStackTrace();
        } catch (Exception e) {
            responseMessage.setStatus(306);
            responseMessage.setMessage(e.getMessage());
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }

    /**
     * 商品搜索
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "search.action")
    public void search(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<List<Goods>> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            String k = RequestUtil.getString(request, "k", null);
            long ccid = RequestUtil.getLong(request, "ccid", 0);

            //查询数据
            List<Goods> goodses = this.goodsService.getGoodsByKeywordAndCid("%" + k + "%", getLoginUser(request).getCid(), Long.valueOf(ccid));
            if (goodses != null) this.priceService.supplementGoodsPrice(goodses, ccid);
            if (goodses != null) {
                responseMessage.setData(goodses);
                responseMessage.setCount(goodses.size());
            }
        } catch (ParameterException pe) {
            pe.printStackTrace();
        } catch (SessionException e1) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("login fail");
        } catch (Exception e) {
            responseMessage.setStatus(400);
            responseMessage.setMessage(e.getMessage());
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }

    /**
     * 读取赠品列表
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "getgiveawaybyid.action")
    public void getGiveAwayById(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<List<Goods>> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            long mgid = RequestUtil.getLong(request, "mgid", 0);
            long ccid = RequestUtil.getLong(request, "ccid", 0);
            List<Goods> goodses = this.goodsService.getChlidGoodsByMainGoodsId(mgid, getLoginUser(request).getCid(), ccid);
            if (goodses != null) {
                List<Goods> giveAwayList = goodses.stream().filter(goods -> goods.getGiveaway() == 1).collect(Collectors.toList());
                responseMessage.setData(giveAwayList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }
}
