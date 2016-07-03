package com.sneaker.mall.api.api.dispatch;

import com.sneaker.mall.api.api.saleman.BaseController;
import com.sneaker.mall.api.model.*;
import com.sneaker.mall.api.service.DispatchService;
import com.sneaker.mall.api.service.OrderService;
import com.sneaker.mall.api.util.CONST;
import com.sneaker.mall.api.util.JsonParser;
import com.sneaker.mall.api.util.RequestUtil;
import com.sneaker.mall.api.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@Controller("dispatchreceiptsapi")
@RequestMapping(value = "/dispatch/receipts")
public class ReceiptsApi extends BaseController {

    @Autowired
    private DispatchService dispatchService;

    @Autowired
    private OrderService orderService;


    /**
     * 判断ID是否可以进行结算操作
     *
     * @param request
     * @param response
     * @apiDescription 判断单据是否可以进行支付操作
     * @api {get} /dispatch/receipts/isexist.action  [判断是否可以进行支付操作]
     * @apiGroup dispatch
     * @apiParam {Number} [id] 出库单号
     * @apiParam {Number} [type] 类型
     * @apiSuccess ｛Number｝ [data]  0表示不可以支付，1表示可以支付,4 表示没有找到
     */
    @RequestMapping(value = "isexist.action")
    public void isExist(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<Integer> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        long id = RequestUtil.getLong(request, "id", 0);
        int type = RequestUtil.getInt(request, "type", 0);
        int res = this.dispatchService.isCanPay(id, type);
        responseMessage.setData(res);
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }


    /**
     * 获取出库单
     *
     * @param request
     * @param response
     * @apiDescription 根据ID获取出库单
     * @api {get} /dispatch/receipts/stockout.action  [根据ID获取出库单]
     * @apiGroup dispatch
     * @apiParam {Number} [id] 出库单号
     * @apiSuccess ｛String｝ [result] JSON出库单信息
     */
    @RequestMapping(value = "stockout.action")
    public void getStockOut(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<StockOut> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        long id = RequestUtil.getLong(request, "id", 0);
        try {
            StockOut stockOut = this.dispatchService.getStockOutById(id);
            if (stockOut != null) {
                responseMessage.setData(stockOut);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setStatus(300);
            responseMessage.setMessage("参数错误");
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }


    /**
     * 获取客户订单
     *
     * @param request
     * @param response
     * @apiDescription 根据ID获取出客户订单
     * @api {get} /dispatch/receipts/border.action  [根据ID获取客户订单]
     * @apiGroup dispatch
     * @apiParam {Number} [id] 客户订单号
     * @apiSuccess ｛String｝ [result] JSON客户订单信息
     */
    @RequestMapping(value = "border.action")
    public void getBOrder(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<Order> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        long id = RequestUtil.getLong(request, "id", 0);
        try {
            Order bOrder = this.orderService.getOrderById(id);
            if (bOrder != null) {
                responseMessage.setData(bOrder);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setStatus(300);
            responseMessage.setMessage("参数错误");
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }

    /**
     * 获取结算单
     *
     * @param request
     * @param response
     * @apiDescription 根据ID获取结算单
     * @api {get} /dispatch/receipts/settlecustomer.action  [根据ID获取结算单]
     * @apiGroup dispatch
     * @apiParam {Number} [id] 结算单号
     * @apiSuccess ｛String｝ [result] JSON结算单信息
     */
    @RequestMapping(value = "settlecustomer.action")
    public void getSettleCustomer(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<SettleCustomer> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        long id = RequestUtil.getLong(request, "id", 0);
        try {
            SettleCustomer settleCustomer = this.dispatchService.getSettleCustomerById(id);
            if (settleCustomer != null) {
                responseMessage.setData(settleCustomer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setStatus(300);
            responseMessage.setMessage("参数错误");
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }
}
