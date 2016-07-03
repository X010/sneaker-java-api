package com.sneaker.mall.api.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sneaker.mall.api.model.Order;
import com.sneaker.mall.api.model.PayGateway;
import com.sneaker.mall.api.model.SettleCustomer;
import com.sneaker.mall.api.model.StockOut;
import com.sneaker.mall.api.service.DispatchService;
import com.sneaker.mall.api.service.OrderService;
import com.sneaker.mall.api.service.PayGatewayService;
import com.sneaker.mall.api.service.PayService;
import com.sneaker.mall.api.task.impl.SubmitOrderToErpTask;
import com.sneaker.mall.api.util.CONST;
import com.sneaker.mall.api.util.HttpClientUtil;
import com.sneaker.mall.api.util.JsonParser;
import com.sneaker.mall.api.util.PayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class PayServiceImpl implements PayService {

    private Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

    @Autowired
    @Qualifier("qr_pay_wx")
    private String wxPayQrUrl;

    @Autowired
    @Qualifier("qr_pay_ali")
    private String aliPayQrUrl;

    @Autowired
    @Qualifier("pay_return_url")
    private String returnUrl;

    @Autowired
    @Qualifier("pay_notice")
    private String noticeUrl;

    @Autowired
    private DispatchService dispatchService;

    @Autowired
    private PayGatewayService payGatewayService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SubmitOrderToErpTask submitOrderToErpTask;


    @Override
    public String getPayQrSign(long bid, int type, int payType, String clientIp, long uid) {
        Preconditions.checkArgument(bid > 0);
        Preconditions.checkArgument(type > 0);
        Preconditions.checkArgument(payType > 0);
        Preconditions.checkNotNull(clientIp);
        String res = null;

        switch (type) {

            case 1: //出库单
                StockOut stockOut = this.dispatchService.getStockOutById(bid);
                if (stockOut != null) {
                    switch (payType) {
                        case 1://微信
                            PayUtil.WxPostParam wxPostParam = this.getWxPostParamByStockOut(stockOut, clientIp);
                            res = this.getWxResponse(wxPostParam);
                            break;
                        case 2://支付宝
                            PayUtil.AliPostParam aliPostParam = this.getAliPostParamByStockOut(stockOut, clientIp);
                            res = this.getAliResponse(aliPostParam);
                            break;
                        case 3://现金
                            res = String.valueOf(System.currentTimeMillis());
                            break;
                    }
                    //添加支付记录到DB中去
                    PayGateway payGateway = this.createPayGatewayByStouckout(stockOut, res);
                    if (payGateway != null) {
                        payGateway.setBtype(payType);
                        payGateway.setUid(uid);
                        this.payGatewayService.savePayGateway(payGateway);
                    }
                }
                break;

            case 2://结算单
                SettleCustomer settleCustomer = this.dispatchService.getSettleCustomerById(bid);
                if (settleCustomer != null) {
                    switch (payType) {
                        case 1://微信
                            PayUtil.WxPostParam wxPostParam = this.getWxPostParamBySettleCustomer(settleCustomer, clientIp);
                            res = this.getWxResponse(wxPostParam);
                            break;
                        case 2://支付宝
                            PayUtil.AliPostParam aliPostParam = this.getAliPostParamBySettleCustomer(settleCustomer, clientIp);
                            res = this.getAliResponse(aliPostParam);
                            break;
                        case 3://现金
                            res = String.valueOf(System.currentTimeMillis());
                            break;
                    }
                    //添加支付记录到DB中去
                    PayGateway payGateway = this.createPayGatewayByCustomerSettle(settleCustomer, res);
                    if (payGateway != null) {
                        payGateway.setUid(uid);
                        payGateway.setBtype(payType);
                        this.payGatewayService.savePayGateway(payGateway);
                    }
                }
                break;

            case 3: //客户订单
                Order bOrder = this.orderService.getOrderById(bid);
                if (bOrder != null) {
                    switch (payType) {
                        case 1://微信
                            PayUtil.WxPostParam wxPostParam = this.getWxPostParamByBOrder(bOrder, clientIp);
                            res = this.getWxResponse(wxPostParam);
                            break;
                        case 2://支付宝
                            PayUtil.AliPostParam aliPostParam = this.getAliPostParamByBOrder(bOrder, clientIp);
                            res = this.getAliResponse(aliPostParam);
                            break;
                        case 3://现金
                            res = String.valueOf(System.currentTimeMillis());
                            break;
                    }
                    //添加支付记录到DB中去
                    PayGateway payGateway = this.createPayGatewayByBOrder(bOrder, res);
                    if (payGateway != null) {
                        payGateway.setUid(uid);
                        payGateway.setBtype(payType);
                        this.payGatewayService.savePayGateway(payGateway);
                    }
                }
                break;
        }
        return res;
    }

    @Override
    public boolean commitPay(int btype, long id, int paytype, String inputAmount) {
        Preconditions.checkNotNull(id);
        Preconditions.checkArgument(btype > 0);
        Preconditions.checkArgument(paytype > 0);
        logger.info("commit:btype=" + btype + ",id=" + id + ",paytype=" + paytype);
        boolean res = false;
        String sid = String.valueOf(id);
        PayGateway payGateway = this.payGatewayService.getPayGatewayById(sid);
        if (payGateway != null) {
            //修改网关状态
            payGateway.setStatus(CONST.PAY_GATEWAY_PAY);
            payGateway.setSmall_amount(inputAmount);
            this.payGatewayService.savePayGateway(payGateway);
            //如果是订单则看是不是商城的订单.然后执行更新操作
            if (btype == 3) {
                //是客户订单,判断商中是否存在该订单
                Order order = this.orderService.getOrderById(id);
                if (order != null) {
                    this.orderService.updateOrderIsPay(order.getOrder_id(), 9);
                }
                order.setUid(payGateway.getUid());//设置收款人
                //如果是立该支付订单后同步订单ERP中,走全新同步方式
                List<Order> orders = Lists.newArrayList();
                orders.add(order);
                List<SubmitOrderToErpTask.ErpOrder> erpOrders = this.submitOrderToErpTask.convertOrder(orders, true);

                //支设支付方式
                erpOrders.stream().forEach(tempErpOrders -> {
                    tempErpOrders.setPay_type(paytype);//高设支付方式
                });

                if (erpOrders != null) {
                    //已付款
                    erpOrders.stream().forEach(erpOrder -> {
                        erpOrder.setIspaid(1);
                        erpOrder.setSmall_amount(inputAmount);
                    });

                    HashMap<String, String> param = new HashMap<>();
                    param.put("data", JsonParser.simpleJson(erpOrders));
                    try {
                        SubmitOrderToErpTask.ResponseJson responseJson = this.submitOrderToErpTask.requestOrderErp(param);
                        if (responseJson != null) {
                            List<SubmitOrderToErpTask.CreateOrderResponse> responses = responseJson.getMsg();
                            if (responses != null && responses.size() > 0) {
                                for (SubmitOrderToErpTask.CreateOrderResponse response : responses) {
                                    String mall_error_code = response.getErrCode();
                                    if (!Strings.isNullOrEmpty(mall_error_code)) {
                                        //有错误
                                        this.orderService.updateOrderStatus(response.getOrderNo(), 99);
                                        payGateway.setStatus(CONST.PAY_GATEWAY_ERROR);
                                        payGateway.setError(mall_error_code);
                                    } else {
                                        payGateway.setStatus(CONST.PAY_GATEWAY_NOTICE);
                                        res = true;
                                    }
                                    this.payGatewayService.savePayGateway(payGateway);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                //通知ERP表示支付成功
                try {
                    ErpPayResult erpPayResult = this.noticeErp(btype, paytype, sid,
                            String.valueOf(Float.valueOf(payGateway.getAmount()) / Float.valueOf(100)), "0", payGateway.getUid());//将ERP中的分转换为元,统一与其它接口使用元
                    if (erpPayResult != null) {
                        if (erpPayResult.getErr() == 0) {
                            //通知失败成功
                            payGateway.setStatus(CONST.PAY_GATEWAY_NOTICE);
                        } else {
                            payGateway.setStatus(CONST.PAY_GATEWAY_ERROR);
                            payGateway.setError(erpPayResult.getMsg());
                        }
                        this.payGatewayService.savePayGateway(payGateway);
                        res = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }


    /**
     * 获取微信返回数据
     *
     * @param wxPostParam
     * @return
     */
    private String getWxResponse(PayUtil.WxPostParam wxPostParam) {
        if (wxPostParam != null) {
            HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();
            String param = JsonParser.simpleJson(wxPostParam);
            String doPost = null;
            try {
                doPost = httpClientUtil.getResponseJsonBodyAsString(this.wxPayQrUrl, param);
                logger.info("wx pay:" + doPost);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!Strings.isNullOrEmpty(doPost)) {
                PayUtil.WxQrResult wxQrResult = (PayUtil.WxQrResult) JsonParser.StringToJsonVideo(doPost, PayUtil.WxQrResult.class);
                if (wxQrResult != null && !Strings.isNullOrEmpty(wxQrResult.getCode_url())) {
                    return wxQrResult.getCode_url();
                }
            }
        }
        return null;
    }

    /**
     * 获取支付宝的返回数据
     *
     * @param aliPostParam
     * @return
     */
    private String getAliResponse(PayUtil.AliPostParam aliPostParam) {
        String doPost = null;
        if (aliPostParam != null) {
            HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();
            String param = JsonParser.simpleJson(aliPostParam);

            try {
                doPost = httpClientUtil.getResponseJsonBodyAsString(this.aliPayQrUrl, param);
                logger.info("ali pay:" + doPost);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //解析返回数据
            if (!Strings.isNullOrEmpty(doPost)) {
                PayUtil.AliQrResult aliQrResult = (PayUtil.AliQrResult) JsonParser.StringToJsonVideo(doPost, PayUtil.AliQrResult.class);
                if (aliQrResult != null && !Strings.isNullOrEmpty(aliQrResult.getSign())) {
                    return aliQrResult.getSign();
                }
            }
        }
        return null;
    }

    /**
     * 将SotckOut转换成WxPostParam
     *
     * @param stockOut
     * @return
     */
    private PayUtil.WxPostParam getWxPostParamByStockOut(StockOut stockOut, String clientIp) {
        PayUtil.WxPostParam wxPostParam = null;
        try {
            wxPostParam = new PayUtil.WxPostParam();
            wxPostParam.setProduct_id(String.valueOf(stockOut.getId()));
            wxPostParam.setBody("出库单(客户:" + stockOut.getIn_cname() + ")-" + stockOut.getId());
            wxPostParam.setSpbill_create_ip(clientIp);
            long pay = stockOut.getAmount();
            this.logger.info("bill:" + wxPostParam.getProduct_id() + ",m:" + pay);
            wxPostParam.setOrderMoney(String.valueOf(pay));
            wxPostParam.setReturn_url(this.returnUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wxPostParam;
    }

    /**
     * 将StockOut转换成AliPostParam
     *
     * @param stockOut
     * @return
     */
    private PayUtil.AliPostParam getAliPostParamByStockOut(StockOut stockOut, String clientIp) {
        PayUtil.AliPostParam aliPostParam = null;
        try {
            aliPostParam = new PayUtil.AliPostParam();
            aliPostParam.setSpbill_create_ip(clientIp);
            aliPostParam.setBody("出库单(客户:" + stockOut.getIn_cname() + ")-" + stockOut.getId());
            aliPostParam.setProduct_id(String.valueOf(stockOut.getId()));
            aliPostParam.setSubject("出库单(付款)-" + stockOut.getId());
            float pay = Float.valueOf(stockOut.getAmount()) / 100;
            aliPostParam.setTotal_fee(String.valueOf(pay));
            aliPostParam.setReturn_url(this.returnUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aliPostParam;
    }

    /**
     * 将SettleCustomer转换成WxPostParam
     *
     * @param settleCustomer
     * @param clientIp
     * @return
     */
    private PayUtil.WxPostParam getWxPostParamBySettleCustomer(SettleCustomer settleCustomer, String clientIp) {
        PayUtil.WxPostParam wxPostParam = null;
        try {
            wxPostParam = new PayUtil.WxPostParam();
            wxPostParam.setProduct_id(String.valueOf(settleCustomer.getId()));
            wxPostParam.setBody("结算单(客户:" + settleCustomer.getCcname() + ")-" + settleCustomer.getId());
            wxPostParam.setSpbill_create_ip(clientIp);
            long pay = settleCustomer.getAmount_price();
            wxPostParam.setOrderMoney(String.valueOf(pay));
            wxPostParam.setReturn_url(this.returnUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wxPostParam;
    }

    /**
     * 将SettleCustomer转换成AliPostParam
     *
     * @param settleCustomer
     * @param clientIp
     * @return
     */
    private PayUtil.AliPostParam getAliPostParamBySettleCustomer(SettleCustomer settleCustomer, String clientIp) {
        PayUtil.AliPostParam aliPostParam = null;
        try {
            aliPostParam = new PayUtil.AliPostParam();
            aliPostParam.setSpbill_create_ip(clientIp);
            aliPostParam.setBody("结算单(客户:" + settleCustomer.getCcname() + ")-" + settleCustomer.getId());
            aliPostParam.setProduct_id(String.valueOf(settleCustomer.getId()));
            aliPostParam.setSubject("结算单(付款)-" + settleCustomer.getId());
            float pay = Float.valueOf(settleCustomer.getAmount_price()) / 100;
            aliPostParam.setTotal_fee(String.valueOf(pay));
            aliPostParam.setReturn_url(this.returnUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aliPostParam;
    }

    /**
     * 将Border转换成WxPostParam
     *
     * @param bOrder
     * @param clientIp
     * @return
     */
    private PayUtil.WxPostParam getWxPostParamByBOrder(Order bOrder, String clientIp) {
        PayUtil.WxPostParam wxPostParam = null;
        try {
            wxPostParam = new PayUtil.WxPostParam();
            wxPostParam.setProduct_id(String.valueOf(bOrder.getId()));
            wxPostParam.setBody("客户订单(客户:" + bOrder.getCompany_name() + ")-" + bOrder.getId());
            wxPostParam.setSpbill_create_ip(clientIp);
            Float pay = bOrder.getTotal_amount() * 100;
            wxPostParam.setOrderMoney(String.valueOf(pay.longValue()));
            wxPostParam.setReturn_url(this.returnUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wxPostParam;
    }

    /**
     * 将BOrder转换成AliPostParam
     *
     * @param bOrder
     * @param clientIp
     * @return
     */
    private PayUtil.AliPostParam getAliPostParamByBOrder(Order bOrder, String clientIp) {
        PayUtil.AliPostParam aliPostParam = null;
        try {
            aliPostParam = new PayUtil.AliPostParam();
            aliPostParam.setSpbill_create_ip(clientIp);
            aliPostParam.setBody("客户订单(客户:" + bOrder.getCompany_name() + ")-" + bOrder.getId());
            aliPostParam.setProduct_id(String.valueOf(bOrder.getId()));
            aliPostParam.setSubject("客户订单(付款)-" + bOrder.getId());
            float pay = bOrder.getTotal_amount();
            aliPostParam.setTotal_fee(String.valueOf(pay));
            aliPostParam.setReturn_url(this.returnUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aliPostParam;
    }

    /**
     * 根据Stockout创建PayGateway
     *
     * @param stockOut
     * @return
     */
    private PayGateway createPayGatewayByStouckout(StockOut stockOut, String paySign) {
        Preconditions.checkNotNull(stockOut);
        Preconditions.checkNotNull(paySign);
        PayGateway payGateway = new PayGateway();
        payGateway.setStatus(CONST.PAY_GATEWAY_NO);
        payGateway.setAmount(stockOut.getAmount());
        payGateway.setCcid(stockOut.getIn_cid());
        payGateway.setCid(stockOut.getCid());
        payGateway.setCreatetime(new Date());
        payGateway.setCcname(stockOut.getIn_cname());
        payGateway.setCcname(stockOut.getCname());
        payGateway.setErp_id(String.valueOf(stockOut.getId()));
        payGateway.setGateway_id(paySign);
        payGateway.setSuid(stockOut.getSuid());
        return payGateway;
    }

    /**
     * 根据SettleCustomer根据PayGateway
     *
     * @param settleCustomer
     * @param paySign
     * @return
     */
    private PayGateway createPayGatewayByCustomerSettle(SettleCustomer settleCustomer, String paySign) {
        Preconditions.checkNotNull(settleCustomer);
        Preconditions.checkNotNull(paySign);
        PayGateway payGateway = new PayGateway();
        payGateway.setAmount(settleCustomer.getAmount_price());
        payGateway.setSuid(settleCustomer.getUid());
        payGateway.setGateway_id(paySign);
        payGateway.setCcid(settleCustomer.getCcid());
        payGateway.setCid(settleCustomer.getCid());
        payGateway.setErp_id(String.valueOf(settleCustomer.getId()));
        payGateway.setCreatetime(new Date());
        payGateway.setStatus(CONST.PAY_GATEWAY_NO);
        payGateway.setCcname(settleCustomer.getCname());
        payGateway.setCcname(settleCustomer.getCcname());
        return payGateway;
    }

    /**
     * 根据BOrder创建PayGateWay
     *
     * @param bOrder
     * @param paySign
     * @return
     */
    private PayGateway createPayGatewayByBOrder(Order bOrder, String paySign) {
        Preconditions.checkNotNull(bOrder);
        Preconditions.checkNotNull(paySign);
        PayGateway payGateway = new PayGateway();
        Float amount = bOrder.getTotal_amount() * 100;
        payGateway.setAmount(amount.longValue());
        payGateway.setSuid(bOrder.getSuid());
        payGateway.setGateway_id(paySign);
        payGateway.setCcid(bOrder.getScid());
        payGateway.setCid(bOrder.getCid());
        payGateway.setErp_id(String.valueOf(bOrder.getId()));
        payGateway.setStatus(CONST.PAY_GATEWAY_NO);
        payGateway.setCreatetime(new Date());
        payGateway.setCcname(bOrder.getCompany_name());
        payGateway.setCname(bOrder.getSupplier_company_name());
        return payGateway;
    }

    /**
     * 通知ERP支付订单接口
     *
     * @param type     类型1-出库单通知 2-结算单通知 3-订单通知
     * @param pay_type 支付方式
     * @param id       id 单据号，可能是出库单号，结算单号，订单号，根据type变化
     * @param amount   amount 支付金额，单位元。用于二次核对
     * @return
     */
    private ErpPayResult noticeErp(int type, int pay_type, String id, String amount, String inputAmount, long uid) throws Exception {
        ErpPayResult response = null;
        HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();
        Map<String, String> params = Maps.newHashMap();
        params.put("type", String.valueOf(type));
        params.put("pay_type", String.valueOf(pay_type));
        params.put("id", id);
        params.put("amount", amount);
        params.put("uid", String.valueOf(uid));
        params.put("small_amount", inputAmount);//找零金额
        logger.info("request notic:" + params.toString());
        String res = httpClientUtil.getResponseBodyAsString(this.noticeUrl, params);
        logger.info("notic erp response:" + res);
        if (!Strings.isNullOrEmpty(res)) {
            try {
                response = (ErpPayResult) JsonParser.StringToJsonVideo(res, ErpPayResult.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    /**
     * 支付接口返回的结果
     */
    public static class ErpPayResult {
        /**
         * 0-成功 1-失败
         */
        private int err;

        /**
         * 错误编码
         */
        private String status;

        /**
         * msg
         */
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
}
