package com.sneaker.mall.api.service;

import com.sneaker.mall.api.model.ERPConfigDetail;
import com.sneaker.mall.api.model.Order;
import com.sneaker.mall.api.model.OrderOper;

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
public interface OrderService {

    /**
     * 保存订单信息
     *
     * @param orderList
     */
    public void saveOrder(List<Order> orderList);

    /**
     * 分页面读取订单信息
     *
     * @param page
     * @param limit
     * @return
     */
    public List<Order> getOrderByPages(long cid, int page, int limit);

    /**
     * 读取某个业务员给该客户下的订单
     *
     * @param uid
     * @param cid
     * @param page
     * @param limit
     * @return
     */
    public List<Order> getOrderByUidAndPages(long uid, long cid, int page, int limit);

    /**
     * 统计公司订单数量
     *
     * @param cid
     * @return
     */
    public int countOrderByCompany(long cid);

    /**
     * 统计某个业务为客户下的订单数据
     *
     * @param uid
     * @param cid
     * @return
     */
    public int countOrderByCompanyAndUid(long uid, long cid);

    /**
     * 按状态分页面读取订单信息
     *
     * @param status
     * @param page
     * @param limit
     * @return
     */
    public List<Order> getOrderByPagesAndStatus(long cid, int status, int page, int limit);

    /**
     * 按状态获取某个业务为该客户下的单
     *
     * @param uid
     * @param cid
     * @param status
     * @param page
     * @param limit
     * @return
     */
    public List<Order> getOrderByPagesAndStatusAndUid(long uid, long cid, int status, int page, int limit);

    /**
     * 根据超级订单号获取订单信息
     *
     * @param superOrderId
     * @return
     */
    public List<Order> getOrderBySuperOrderId(String superOrderId);

    /**
     * 根据订单号读取订单信息
     *
     * @param id
     * @return
     */
    public Order getOrderById(long id);


    /**
     * 根据公司和订单统计
     *
     * @param cid
     * @param status
     * @return
     */
    public int countOrderByStatusAndCompany(long cid, int status);

    /**
     * 统计时间段内的根据公司与状态的订单数据
     *
     * @param start
     * @param end
     * @param status
     * @return
     */
    public int countOrderByTimeAndStatusAndCompany(Date start, Date end, int status);

    /**
     * 查询时间段的根据状态与公司查询订单
     *
     * @param start
     * @param end
     * @param status
     * @param pages
     * @param size
     * @return
     */
    public List<Order> getOrderByTimeAndStatus(Date start, Date end, int status, int pages, int size);

    /**
     * 统计某业务为该客户下的订单数据
     *
     * @param uid
     * @param cid
     * @param status
     * @return
     */
    public int countOrderByStatusAndCompanyAndUid(long uid, long cid, int status);


    /**
     * 根据业务员及状态读取订单信息
     *
     * @param suid
     * @param status
     * @param page
     * @param limit
     * @return
     */
    public List<Order> getOrderBySalemanAndStatusAndPage(long suid, int status, int page, int limit);


    /**
     * 获取条数
     *
     * @param suid
     * @param status
     * @return
     */
    public int countOrderBySalemanAndStatusAndPage(long suid, int status);


    /**
     * 统计业务订单数
     *
     * @param suid
     * @return
     */
    public int countOrderBySaleman(long suid);

    /**
     * 根据状态查询订单
     *
     * @param status
     * @return
     */
    public List<Order> getOrderByStatus(int status);



    /**
     * 更新订单状态
     *
     * @param orderno
     * @param status
     */
    public void updateOrderStatus(String orderno, int status);

    /**
     * 是否已经支付
     *
     * @param orderno
     * @param ispay
     */
    public void updateOrderIsPay(String orderno, int ispay);


    /**
     * 更新派车状态
     *
     * @param orderno
     * @param status
     */
    public void updateSendCar(String orderno, int status);

    /**
     * 根据商城订单号更新ERP订单号
     *
     * @param orderno
     * @param erp_order_no
     */
    public void updateErpOrderId(String orderno, String erp_order_no);

    /**
     * 取消订单
     *
     * @param id
     * @param cid
     */
    public void cancelOrder(long id, long cid);

    /**
     * 取消订单根据用户判断
     *
     * @param id
     * @param uid
     */
    public void cancelOrderByUid(long id, long uid);


    /**
     * 添加订单的操作信息记录
     *
     * @param erp_order_id
     * @param orderOper
     */
    public void addOrderOper(String erp_order_id, OrderOper orderOper);

    /**
     * 获取订单的跟踪信息
     *
     * @param id
     * @return
     */
    public List<OrderOper> getOrderTraceInfoById(long id);

    /**
     * 获取订单的跟踪信息
     *
     * @param orderNo
     * @return
     */
    public List<OrderOper> getOrderTraceInfoByOrderNo(String orderNo);

    /**
     * 获取送紧急程序
     *
     * @return
     */
    public List<ERPConfigDetail> getDeliverGoods();

    /**
     * 根据ERP订单号查询订单信息
     * @param erp_id
     * @return
     */
    public Order getOrderByErpId(String erp_id);
}
