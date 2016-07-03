package com.sneaker.mall.api.task.impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.sneaker.mall.api.model.Order;
import com.sneaker.mall.api.service.OrderService;
import com.sneaker.mall.api.task.Task;
import com.sneaker.mall.api.util.HttpClientUtil;
import com.sneaker.mall.api.util.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.Serializable;
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
@Component
public class SubmitOrderToErpTask extends Task {

    private final static Logger logger = LoggerFactory.getLogger(SubmitOrderToErpTask.class);
    private final static int ORDER_STATUS = 0;

    @Autowired
    private OrderService orderService;

    @Autowired
    @Qualifier("erpSyncOrder")
    private String erp_sync_order;

    @Override
    public void run() {
        List<Order> orders = this.orderService.getOrderByStatus(ORDER_STATUS);
        if (orders != null && orders.size() > 0) {
            List<ErpOrder> erpOrders = convertOrder(orders, false);
            if (erpOrders != null) {
                HashMap<String, String> param = new HashMap<>();
                param.put("data", JsonParser.simpleJson(erpOrders));
                logger.info(param.toString());
                try {
                    ResponseJson responseJson = this.requestOrderErp(param);
                    if (responseJson != null) {
                        List<CreateOrderResponse> responses = responseJson.getMsg();
                        if (responses != null && responses.size() > 0) {
                            responses.stream().forEach(createOrderResponse -> {
                                String mall_order_id = createOrderResponse.getOrderNo();
                                String mall_error_code = createOrderResponse.getErrCode();
                                if (Strings.isNullOrEmpty(mall_error_code)) {
                                    //更新Mall_order_id状态
                                    this.orderService.updateOrderStatus(mall_order_id, 1);
                                } else {
                                    //错误订单
                                    this.orderService.updateOrderStatus(mall_order_id, 99);
                                    logger.info("error sync order id:" + mall_order_id + "   &code:" + mall_error_code);
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 请求ERP的同步订单接口
     *
     * @param param
     * @return
     */
    public ResponseJson requestOrderErp(Map param) throws Exception {
        ResponseJson responseJson = null;
        HttpClientUtil clientUtil = HttpClientUtil.getInstance();
        logger.info("request:" + param.toString());
        String res = clientUtil.getResponseBodyAsString(this.erp_sync_order, param);
        logger.info("response:" + res);
        if (!Strings.isNullOrEmpty(res)) {
            responseJson = (ResponseJson) JsonParser.StringToJsonVideo(res, ResponseJson.class);
        }
        return responseJson;
    }

    /**
     * 将商城订单转换为ERP订单
     *
     * @param orderList
     * @return
     */
    public List<ErpOrder> convertOrder(List<Order> orderList, boolean isPay) {
        Preconditions.checkNotNull(orderList);
        final List<ErpOrder> erpOrders = Lists.newArrayList();
        orderList.stream().forEach(order -> {
            ErpOrder erpOrder = new ErpOrder();
            erpOrder.setContacts(order.getContacts());
            erpOrder.setPhone(order.getPhone());
            erpOrder.setIn_cid(order.getCid());
            erpOrder.setOut_cid(order.getScid());
            erpOrder.setMemo(order.getMemo());
            erpOrder.setRank(0);
            erpOrder.setOrderNo(order.getOrder_id());
            erpOrder.setReceipt(order.getReceipt());
            erpOrder.setDelivery(order.getDelivery());
            erpOrder.setIspaid(0); //是否付款
            erpOrder.setPay_type(1); //现金
            erpOrder.setSuid(order.getSuid());
            if (isPay) {
                erpOrder.setUid(order.getUid());
            }
            erpOrder.setFavorable(order.getFavorable());
            final List<ErpOrderItem> erpOrderItems = Lists.newArrayList();
            order.getItems().stream().forEach(orderItem -> {
                ErpOrderItem erpOrderItem = new ErpOrderItem();
                erpOrderItem.setAmount(orderItem.getTotal_amount());
                erpOrderItem.setGid(String.valueOf(orderItem.getGid()));
                erpOrderItem.setTotal(orderItem.getTotal());
                erpOrderItem.setUnit_price(orderItem.getPrice());
                erpOrderItem.setAmount_price(orderItem.getTotal_amount());
                erpOrderItem.setMainGcode(orderItem.getMemo());
                erpOrderItems.add(erpOrderItem);
            });
            erpOrder.setGoods_list(erpOrderItems);
            erpOrders.add(erpOrder);
        });
        return erpOrders;
    }


    public static class ResponseJson {
        private int err;
        private String status;
        private List<CreateOrderResponse> msg;

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

        public List<CreateOrderResponse> getMsg() {
            return msg;
        }

        public void setMsg(List<CreateOrderResponse> msg) {
            this.msg = msg;
        }
    }

    /**
     * 调用ERP定单号后的反回信息
     */
    public static class CreateOrderResponse implements Serializable {
        /**
         * 商城订单号
         */
        private String orderNo;

        /**
         * ERP订单号
         */
        private String orderId;

        /**
         * 错误码
         */
        private String errCode;

        public String getErrCode() {
            return errCode;
        }

        public void setErrCode(String errCode) {
            this.errCode = errCode;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }

    /**
     * ERP订单号
     */
    public static class ErpOrder implements Serializable {
        /**
         * 付款方式
         */
        private int pay_type;

        /**
         * 是否已经付款 0-未付款 1-已付款
         */
        private int ispaid;

        /**
         * 进货公司ID
         */
        private long in_cid;

        /**
         * 出货公司ID
         */
        private long out_cid;

        /**
         * 出货公司ID
         */
        private int rank;

        /**
         * 备注
         */
        private String memo;

        /**
         * 商城订单编号
         */
        private String orderNo;

        /**
         * 收货地址
         */
        private String receipt;

        /**
         * 联系人
         */
        private String contacts;

        /**
         * 电话
         */
        private String phone;

        /**
         * 订单紧急程度
         */
        private String delivery;

        /**
         * 业务员ID
         */
        private long suid;

        /**
         * 优惠金额
         */
        private float favorable;

        /**
         * 找零
         */
        private String small_amount = "0";

        /**
         * 收款人
         */
        private long uid;

        public long getUid() {
            return uid;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }

        public String getSmall_amount() {
            return small_amount;
        }

        public void setSmall_amount(String small_amount) {
            this.small_amount = small_amount;
        }

        public float getFavorable() {
            return favorable;
        }

        public void setFavorable(float favorable) {
            this.favorable = favorable;
        }

        public long getSuid() {
            return suid;
        }

        public void setSuid(long suid) {
            this.suid = suid;
        }

        public String getDelivery() {
            return delivery;
        }

        public void setDelivery(String delivery) {
            this.delivery = delivery;
        }

        public int getPay_type() {
            return pay_type;
        }

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
        }

        public int getIspaid() {
            return ispaid;
        }

        public void setIspaid(int ispaid) {
            this.ispaid = ispaid;
        }

        private List<ErpOrderItem> goods_list = Lists.newArrayList();

        public List<ErpOrderItem> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<ErpOrderItem> goods_list) {
            this.goods_list = goods_list;
        }

        public long getIn_cid() {
            return in_cid;
        }

        public void setIn_cid(long in_cid) {
            this.in_cid = in_cid;
        }

        public long getOut_cid() {
            return out_cid;
        }

        public void setOut_cid(long out_cid) {
            this.out_cid = out_cid;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getReceipt() {
            return receipt;
        }

        public void setReceipt(String receipt) {
            this.receipt = receipt;
        }

        public String getContacts() {
            return contacts;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    /**
     * ERP订单号Item
     */
    public static class ErpOrderItem implements Serializable {
        /**
         * 商品ID
         */
        private String gid;

        /**
         * 商品数量
         */
        private int total;

        /**
         * 商品单价
         */
        private float unit_price;

        /**
         * 总价
         */
        private float amount_price;

        /**
         * 总金额
         */
        private float amount;


        /**
         * 主商品Gcode
         */
        private String mainGcode;


        public String getMainGcode() {
            return mainGcode;
        }

        public void setMainGcode(String mainGcode) {
            this.mainGcode = mainGcode;
        }

        public float getAmount_price() {
            return amount_price;
        }

        public void setAmount_price(float amount_price) {
            this.amount_price = amount_price;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public float getUnit_price() {
            return unit_price;
        }

        public void setUnit_price(float unit_price) {
            this.unit_price = unit_price;
        }

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }
    }
}
