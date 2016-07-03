package com.sneaker.mall.api.util;

import java.io.Serializable;

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
 * 构建支付数据
 */
public class PayUtil {


    /**
     * 微信POST的参数
     */
    public static class WxPostParam implements Serializable {
        /**
         * 客户端IP
         */
        private String spbill_create_ip;

        /**
         * 商品描述
         */
        private String body;

        /**
         * 金额
         */
        private String orderMoney;

        /**
         * 产品ID
         */
        private String product_id;

        /**
         * 回调接口地址
         */
        private String return_url;

        public String getReturn_url() {
            return return_url;
        }

        public void setReturn_url(String return_url) {
            this.return_url = return_url;
        }

        public String getSpbill_create_ip() {
            return spbill_create_ip;
        }

        public void setSpbill_create_ip(String spbill_create_ip) {
            this.spbill_create_ip = spbill_create_ip;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getOrderMoney() {
            return orderMoney;
        }

        public void setOrderMoney(String orderMoney) {
            this.orderMoney = orderMoney;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }
    }

    /**
     * 微信返回数据
     */
    public static class WxQrResult implements Serializable {
        /**
         * 状态码
         */
        private String errorcode;

        /**
         * 二维码地址
         */
        private String code_url;

        /**
         * 支付网关流水号
         */
        private String out_trade_no;


        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getErrorcode() {
            return errorcode;
        }

        public void setErrorcode(String errorcode) {
            this.errorcode = errorcode;
        }

        public String getCode_url() {
            return code_url;
        }

        public void setCode_url(String code_url) {
            this.code_url = code_url;
        }
    }

    /**
     * 支付宝POST的参数
     */
    public static class AliPostParam implements Serializable {
        /**
         * 客户端IP
         */
        private String spbill_create_ip;

        /**
         * 商品描述
         */
        private String body;

        /**
         * 金额
         */
        private String total_fee;

        /**
         * 主题
         */
        private String subject;


        /**
         * 商品ID
         */
        private String product_id;

        /**
         * 回调接回地址
         */
        private String return_url;

        public String getReturn_url() {
            return return_url;
        }

        public void setReturn_url(String return_url) {
            this.return_url = return_url;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getSpbill_create_ip() {
            return spbill_create_ip;
        }

        public void setSpbill_create_ip(String spbill_create_ip) {
            this.spbill_create_ip = spbill_create_ip;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(String total_fee) {
            this.total_fee = total_fee;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }
    }

    /**
     * 支付宝返回数据
     */
    public static class AliQrResult implements Serializable {
        /**
         * 签名文字
         */
        private String sign;

        /**
         * 支付流程号
         */
        private String out_trade_no;

        /**
         * 错误编码
         */
        private String errorcode;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getErrorcode() {
            return errorcode;
        }

        public void setErrorcode(String errorcode) {
            this.errorcode = errorcode;
        }
    }
}
