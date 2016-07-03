package com.sneaker.mall.api.api.b2;

import com.sneaker.mall.api.exception.ParameterException;
import com.sneaker.mall.api.exception.SessionException;
import com.sneaker.mall.api.model.Favorite;
import com.sneaker.mall.api.model.Goods;
import com.sneaker.mall.api.model.ResponseMessage;
import com.sneaker.mall.api.service.FavoriteService;
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
import java.util.Date;
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
@Controller
@RequestMapping(value = "/b2/fav")
public class FavoriteApi extends BaseController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private PriceService priceService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 能否收藏
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "canfav.action")
    public void canFav(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<Boolean> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            String mgid = RequestUtil.validateParam(request, "mgid", responseMessage);
            long cid = getLoginUser(request).getCid(); //获取公司ID
            long lmgid = Long.valueOf(mgid);
            boolean res = this.favoriteService.canFavorite(cid, lmgid);
            responseMessage.setData(res);
        } catch (SessionException se) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("login fail");
            se.printStackTrace();
        } catch (ParameterException pe) {
            pe.printStackTrace();
        } catch (Exception e) {
            responseMessage.setMessage(e.getMessage());
            responseMessage.setStatus(400);
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);

    }

    /**
     * 获取 当前公司的 收藏列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getgoods.action")
    public void getFav(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<List<Goods>> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            int page = RequestUtil.getInt(request, "page", CONST.DEFAULT_PAGE_START);
            int limit = RequestUtil.getInt(request, "limit", CONST.DEFAULT_PAGE_SIZE);
            long scid = RequestUtil.getLong(request, "scid", 0);
            long cid = getLoginUser(request).getCid();
            //读取商品列表
            List<Goods> goodses = this.goodsService.getFavoriteGoodsCidAndScid(scid,cid, page, limit);
            if (goodses != null) {
                responseMessage.setData(goodses);
                this.priceService.supplementGoodsPrice(goodses, getLoginUser(request).getCid());
                responseMessage.setCount(this.favoriteService.countFavorite(cid));//补充条数，给前端分页使用
            }
        } catch (SessionException se) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("login fail");
            se.printStackTrace();
        } catch (ParameterException pe) {
            pe.printStackTrace();
        } catch (Exception e) {
            responseMessage.setMessage(e.getMessage());
            responseMessage.setStatus(400);
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }

    /**
     * 删除一个收藏
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "delete.action")
    public void deleteFav(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<String> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            String mgid = RequestUtil.validateParam(request, "mgid", responseMessage);
            long cid = getLoginUser(request).getCid(); //获取公司ID
            long lmgid = Long.valueOf(mgid);
            //根据GIDAND CID找到收藏对象
            List<Favorite> favorites = this.favoriteService.getFavoriteByCidAndGid(cid, lmgid);
            if (favorites != null) {
                favorites.stream().forEach(favorite -> {
                    favorite.setStatus(0); //0表示删除
                    this.favoriteService.saveFavorite(favorite);
                });
            }

        } catch (SessionException se) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("login fail");
            se.printStackTrace();
        } catch (ParameterException pe) {
            pe.printStackTrace();
        } catch (Exception e) {
            responseMessage.setMessage(e.getMessage());
            responseMessage.setStatus(400);
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }

    /**
     * 收藏一个商品
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "save.action")
    public void saveFav(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<String> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            String mgid = RequestUtil.validateParam(request, "mgid", responseMessage);
            Favorite favorite = new Favorite();
            favorite.setStatus(1);
            favorite.setCid(getLoginUser(request).getCid());
            favorite.setUid(getLoginUser(request).getId());
            favorite.setMgid(Long.valueOf(mgid));
            favorite.setCreate_time(new Date());
            this.favoriteService.saveFavorite(favorite);
        } catch (SessionException se) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("login fail");
            se.printStackTrace();
        } catch (ParameterException pe) {
            pe.printStackTrace();
        } catch (Exception e) {
            responseMessage.setMessage(e.getMessage());
            responseMessage.setStatus(400);
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }
}
