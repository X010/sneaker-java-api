package com.sneaker.mall.api.api.dispatch;

import com.google.common.base.Strings;
import com.sneaker.mall.api.api.saleman.BaseController;
import com.sneaker.mall.api.exception.SessionException;
import com.sneaker.mall.api.model.PayGateway;
import com.sneaker.mall.api.model.ResponseMessage;
import com.sneaker.mall.api.service.PayGatewayService;
import com.sneaker.mall.api.service.PayService;
import com.sneaker.mall.api.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
 */
@Controller("dispatchpayapi")
@RequestMapping(value = "/dispatch/pay")
public class PayApi extends BaseController {

    @Autowired
    private PayService payService;

    @Autowired
    private PayGatewayService payGatewayService;

    private Logger logger = LoggerFactory.getLogger(PayApi.class);


    /**
     * 支付回调接口
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "callback.action")
    public String payCallback(HttpServletRequest request, HttpServletResponse response) {
        String res = "fail";
        String id = RequestUtil.getString(request, "id", "");
        logger.info(id);
        String payType = RequestUtil.getString(request, "type", "");
        logger.info(payType);
        if (!Strings.isNullOrEmpty(id) && !Strings.isNullOrEmpty(payType)) {
            //支付成了通知ERP支付成功了
            int bType = OrderTypeUtil.getOrderType(id);
            if (bType > 0) {
                try {
                    boolean handStr = this.payService.commitPay(bType, Long.valueOf(id), Integer.valueOf(payType), "0");
                    if (handStr) {
                        res = "success";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                logger.info("no found id:" + id + " bill type");
            }
        }
        return res;
    }


    /**
     * 现金支付
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "cash.action")
    public void cashPay(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<String> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        long id = RequestUtil.getLong(request, "id", 0);
        int type = RequestUtil.getInt(request, "type", 0);
        String inputAmount = RequestUtil.getString(request, "inputAmount", "");
        String ip = NetworkUtil.customerIp(request);
        try {
            //在PAYGETEWAY中生成一条记录
            this.payService.getPayQrSign(id, type, 3, ip, getLoginUser(request).getId());
            //发送确认到ERP那边去
            boolean handStr = this.payService.commitPay(type, Long.valueOf(id), 3, inputAmount);
            if (handStr) {
                responseMessage.setData("success");
            } else {
                responseMessage.setData("fail");
            }
        } catch (SessionException se) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("login fail");
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }

    /**
     * 微信支付
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "wx.action")
    public void wxPay(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<String> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        long id = RequestUtil.getLong(request, "id", 0);
        int type = RequestUtil.getInt(request, "type", 0);
        //获取客户端IP
        String ip = NetworkUtil.customerIp(request);
        try {
            //获取支付Sign
            String key = this.payService.getPayQrSign(id, type, 1, ip, getLoginUser(request).getId());
            if (!Strings.isNullOrEmpty(key)) {
                responseMessage.setData(key);
            } else {
                responseMessage.setStatus(300);
                responseMessage.setMessage("暂时无法获取二维码地址,请稍后重试");
            }
        } catch (SessionException se) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("login fail");
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }

    /**
     * 支付宝支付
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "ali.action")
    public ModelAndView aliPay(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
        long id = RequestUtil.getLong(request, "id", 0);
        int type = RequestUtil.getInt(request, "type", 0);
        long uid=RequestUtil.getInt(request, "type", 0);
        //获取客户端IP
        String ip = NetworkUtil.customerIp(request);
        try {
            String key = this.payService.getPayQrSign(id, type, 2, ip, uid);
            if (!Strings.isNullOrEmpty(key)) {
                model.addObject("body", key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.setViewName("aliPay");
        return model;
    }

    /**
     * 轮询单据是否已支付
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "ispay.action")
    public void ispay(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<Integer> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            String id = RequestUtil.getString(request, "id", null);
            if (!Strings.isNullOrEmpty(id)) {
                PayGateway payGateway = this.payGatewayService.getPayGatewayById(id);
                if (payGateway != null) {
                    responseMessage.setData(payGateway.getStatus());
                }
            }
        } catch (SessionException se) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("login fail");
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);

    }


    /**
     * 获取支付历史记录
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "history.action")
    public void history(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<List<PayGateway>> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            long uid = getLoginUser(request).getId();
            int page = RequestUtil.getInt(request, "page", CONST.DEFAULT_PAGE_START);
            int limit = RequestUtil.getInt(request, "limit", CONST.DEFAULT_PAGE_SIZE, CONST.LIMIT_MIN, CONST.LIMIT_MAX);
            List<PayGateway> payGateways = this.payGatewayService.getPayGatewayByUid(uid, page, limit);
            if (payGateways != null) {
                responseMessage.setData(payGateways);
            }
        } catch (SessionException se) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("login fail");
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }
}
