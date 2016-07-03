package com.sneaker.mall.api.api.b2;

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
@Controller
@RequestMapping(value = "/b2/goods")
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
            String scid = RequestUtil.validateParam(request, "scid", responseMessage);
            String cateid = RequestUtil.validateParam(request, "cateid", responseMessage);
            int limit = RequestUtil.getInt(request, "limit", CONST.DEFAULT_PAGE_SIZE, CONST.LIMIT_MIN, CONST.LIMIT_MAX);
            long lscid = Long.valueOf(scid);
            long lcateid = Long.valueOf(cateid);
            List<Goods> goodses = this.goodsService.getGoodsByCidAndCateidAndLimit(lscid, lcateid, limit, getLoginUser(request).getCid());
            int count = this.goodsService.countGoodsByCidAndCateid(lscid, lcateid, getLoginUser(request).getCid());
            if (goodses != null) this.priceService.supplementGoodsPrice(goodses, getLoginUser(request).getCid());
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
            long scid = RequestUtil.getLong(request, "scid", 0);
            int page = RequestUtil.getInt(request, "page", CONST.DEFAULT_PAGE_START);
            int size = RequestUtil.getInt(request, "limit", CONST.DEFAULT_PAGE_SIZE, CONST.LIMIT_MIN, CONST.LIMIT_MAX);
            if (scid > 0) {
                if (!Strings.isNullOrEmpty(typeCode)) {
                    String queryTypeCode = typeCode + "%";
                    List<Goods> goodses = this.goodsService.getGoodsByCidAndTypeCode(page, size, scid, typeCode + "%", getLoginUser(request).getCid());
                    if (goodses != null && goodses.size() > 0) {
                        this.priceService.supplementGoodsPrice(goodses, getLoginUser(request).getCid());
                        responseMessage.setData(goodses);
                        responseMessage.setCount(this.goodsService.countGoodsByCidAndTypeCode(scid, queryTypeCode, getLoginUser(request).getCid()));
                    }
                } else {
                    //读取所有商品
                    List<Goods> goodses = this.goodsService.getGoodsByCidAndPage(scid, page, size, getLoginUser(request).getCid());
                    if (goodses != null && goodses.size() > 0) {
                        this.priceService.supplementGoodsPrice(goodses, getLoginUser(request).getCid());
                        responseMessage.setData(goodses);
                        responseMessage.setCount(this.goodsService.countGoodsByCid(scid, getLoginUser(request).getCid()));
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
            String id = RequestUtil.validateParam(request, "id", responseMessage);
            long scid = RequestUtil.getLong(request, "scid", 0);
            Goods goods = this.goodsService.getGoodsById(Long.valueOf(id), scid, getLoginUser(request).getCid());
            List<Goods> goodses = Lists.newArrayList();
            goodses.add(goods);
            this.priceService.supplementGoodsPrice(goodses, getLoginUser(request).getCid());
            if (goodses != null) {
                goods = goodses.get(0);
            }
            responseMessage.setData(goods);
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
            long scid = RequestUtil.getLong(request, "scid", 0);

            //查询数据
            List<Goods> goodses = this.goodsService.getGoodsByKeywordAndCid("%" + k + "%", scid, getLoginUser(request).getCid());
            if (goodses != null) this.priceService.supplementGoodsPrice(goodses, getLoginUser(request).getCid());
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
            long scid = RequestUtil.getLong(request, "scid", 0);
            List<Goods> goodses = this.goodsService.getChlidGoodsByMainGoodsId(mgid, scid, getLoginUser(request).getCid());
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
