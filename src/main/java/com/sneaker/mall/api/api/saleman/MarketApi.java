package com.sneaker.mall.api.api.saleman;

import com.google.common.base.Strings;
import com.sneaker.mall.api.exception.ParameterException;
import com.sneaker.mall.api.exception.SessionException;
import com.sneaker.mall.api.model.Market;
import com.sneaker.mall.api.model.ResponseMessage;
import com.sneaker.mall.api.service.MarketService;
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
 * 营销活动
 */
@Controller("salemanmarketapi")
@RequestMapping(value = "/saleman/market")
public class MarketApi extends BaseController {

    @Autowired
    private MarketService marketService;

    /**
     * 根据商品ID获取营销活动信息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "getmarketbymgid.action")
    public void getMarketByMgid(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<List<Market>> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            long ccid = RequestUtil.getLong(request, "ccid", 0);
            long cid = getLoginUser(request).getCid();
            long mgid = RequestUtil.getLong(request, "mgid", 0);
            String mids = RequestUtil.getString(request, "mids", "");
            if (ccid > 0 && mgid > 0 && !Strings.isNullOrEmpty(mids)) {
                List<Market> markets = this.marketService.getGoodsMarketByMgid(mgid, ccid, cid, mids);
                if (markets != null) {
                    responseMessage.setData(markets);
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
}
