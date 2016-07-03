package com.sneaker.mall.api.util;

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
 * 定义常量
 */
public class CONST {
    /**
     * 最小分页条数
     */
    public static int LIMIT_MIN = 1;

    /**
     * 最大分页条数
     */
    public static int LIMIT_MAX = 20;


    /**
     * 默认分页数
     */
    public static int DEFAULT_PAGE_SIZE = 20;

    /**
     * 最大的分页面数据
     */
    public static int MAX_PAGE_SIZE = 100;

    /**
     * 默认起始页
     */
    public static int DEFAULT_PAGE_START = 1;

    /**
     * 订单随机开始范围
     */
    public static int ORDER_RANDOM_START = 100000;

    /**
     * 订单随机结束范围
     */
    public static int ORDER_RANDOM_END = 899999;

    /**
     * 回调变量
     */
    public static String CALLBACK_VAR = "result";

    /**
     * 默认返回编码
     */
    public static String ENCODE = "UTF-8";

    /**
     * 删除订单
     */
    public static int ORDER_STATUS_DELETE = 8;
    public static int ORDER_STATUS_CREATE = 0; //创建
    public static int ORDER_STATUS_ERP = 1;//发送到ERP
    public static int ORDER_STATUS_ERP_CREATE_SOTCKOUT = 3;//生成出库单
    public static int ORDER_STATUS_ERP_CHECK_STOUCKOUT = 4;//审核出库单


    /**
     * 营销主商品
     */
    public static int MARKET_GIVEWAYA_MAIN = 0;

    /**
     * 赠品
     */
    public static int MARKET_GIVEWAYA_GIVE = 1;


    public static int GOODS_PUBLISH_ONLINE = 1;
    public static int GOODS_PUBLISH_OFFLINE = 0;

    /**
     * 仓库状态
     */
    public static int STORE_ONLINE = 1;
    public static int STORE_OFFLINE = 0;

    //经销商价
    public static int CUSTOMER_TYPE_SELL = 1;
    //酒店饭店价
    public static int CUSTOMER_TYPE_WINESHOP = 2;
    //商场超市价
    public static int CUSTOMER_TYPE_MARKET = 3;
    //个人零售价
    public static int CUSTOMER_TYPE_PERSON = 4;

    /**
     * 公司默认显示层级
     */
    public static String COMPANY_CATE_SHOW_LAYOUT = "show_cate_layout";
    /**
     * 分类长度
     */
    public static int CATE_LENGTH = 2;

    /**
     * 业务员自动更新KEY
     */
    public static String SALEMAN_AUTO_UPDATE = "SALEMAN_AUTO_UPDATE";

    /**
     * 客户端自动更新KEY
     */
    public static String CUSTOMER_AUTO_UPDATE = "CUSTOMER_AUTO_UPDATE";

    /**
     * 业务员自动更新下载KEY
     */
    public static String SALEMAN_AUTO_UPDATE_DOWNLOAD = "SALEMAN_AUTO_UPDATE_DOWNLOAD";

    /**
     * 客户端自动更新下载地址
     */
    public static String CUSTOMER_AUTO_UPDATE_DOWNLOAD = "CUSTOMER_AUTO_UPDATE_DOWNLOAD";

    /**
     * 送货紧急程度
     */
    public static int DELIVERGOODSFID = 2;


    //网关支付记录配置
    public static int PAY_GATEWAY_NO = 1;
    public static int PAY_GATEWAY_PAY = 2;
    public static int PAY_GATEWAY_NOTICE = 3;
    public static int PAY_GATEWAY_ERROR = 4;

    //营销类别
    public static int MARKET_MTYPE_BUYBFREE = 1;

}
