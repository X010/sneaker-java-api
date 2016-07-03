package com.sneaker.mall.api.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.sneaker.mall.api.dao.admin.CouponDetailDao;
import com.sneaker.mall.api.dao.admin.OrderDao;
import com.sneaker.mall.api.dao.info.ConfigDetailDao;
import com.sneaker.mall.api.model.*;
import com.sneaker.mall.api.service.OrderService;
import com.sneaker.mall.api.service.PriceService;
import com.sneaker.mall.api.util.CONST;
import com.sneaker.mall.api.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
@Service
public class B2OrderServiceImpl implements OrderService {

    private Logger logger = LoggerFactory.getLogger(B2OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    @Qualifier("delete_erp_order")
    private String cancelOrderUrl;

    @Autowired
    private CouponDetailDao couponDetailDao;


    @Autowired
    private PriceService priceService;

    @Autowired
    private ConfigDetailDao configDetailDao;

    @Override
    public List<ERPConfigDetail> getDeliverGoods() {
        return this.configDetailDao.findDeliverGoods(CONST.DELIVERGOODSFID);
    }

    @Override
    public Order getOrderByErpId(String erp_id) {
        Preconditions.checkNotNull(erp_id);
        return this.orderDao.findOrderByErpId(erp_id);
    }

    @Override
    public void saveOrder(List<Order> orderList) {
        Preconditions.checkNotNull(orderList);
        orderList.stream().forEach(order -> {
            //插入
            String order_id = order.getOrder_id();
            List<OrderItem> orderItems = order.getItems();
            orderItems.stream().forEach(orderItem -> {
                orderItem.setOrder_id(order_id);
                this.orderDao.addOrderItem(orderItem);
            });
            this.orderDao.addOrder(order); //最后写入订单信息

            //添加一条订单跟踪信息
            OrderOper orderOper = new OrderOper();
            orderOper.setOrder_no(order.getOrder_id());
            orderOper.setStatus(1);
            orderOper.setOper_time(new Date());
            orderOper.setAction("创建订单");
            this.orderDao.insertOrderOper(orderOper);
        });
    }

    @Override
    public List<Order> getOrderByPages(long cid, int page, int limit) {
        Preconditions.checkArgument(page > 0);
        Preconditions.checkArgument(limit > 0);
        int start = (page - 1) * limit;
        List<Order> orders = this.orderDao.findOrderByPage(cid, start, limit);
        if (orders != null) {
            //补充Item信息
            orders.stream().forEach(order -> {
                List<OrderItem> orderItems = this.orderDao.findOrderItemByOrderId(order.getOrder_id());
                order.setItems(orderItems);
            });
        }
        return orders;
    }

    @Override
    public List<Order> getOrderByUidAndPages(long uid, long cid, int page, int limit) {
        Preconditions.checkArgument(uid > 0);
        Preconditions.checkArgument(cid > 0);
        Preconditions.checkArgument(page > 0);
        Preconditions.checkArgument(limit > 0 && limit <= CONST.MAX_PAGE_SIZE);
        int start = (page - 1) * limit;
        List<Order> orders = this.orderDao.findOrderByPageAndUid(uid, cid, page, limit);
        if (orders != null) {
            //补充Item信息
            orders.stream().forEach(order -> {
                List<OrderItem> orderItems = this.orderDao.findOrderItemByOrderId(order.getOrder_id());
                order.setItems(orderItems);
            });
        }
        return orders;
    }


    @Override
    public int countOrderByCompany(long cid) {
        Preconditions.checkArgument(cid > 0);
        return this.orderDao.countOrderByCid(cid);
    }

    @Override
    public int countOrderByCompanyAndUid(long uid, long cid) {
        Preconditions.checkArgument(uid > 0);
        Preconditions.checkArgument(cid > 0);
        return this.orderDao.countOrderByCidAndUid(uid, cid);
    }


    @Override
    public List<Order> getOrderByPagesAndStatus(long cid, int status, int page, int limit) {
        Preconditions.checkArgument(page > 0);
        Preconditions.checkArgument(limit > 0);
        int start = (page - 1) * limit;
        List<Order> orders = this.orderDao.findOrderByPageAndStatus(cid, status, start, limit);
        if (orders != null) {
            //补充Item信息
            orders.stream().forEach(order -> {
                List<OrderItem> orderItems = this.orderDao.findOrderItemByOrderId(order.getOrder_id());
                order.setItems(orderItems);
            });
        }
        return orders;
    }

    @Override
    public List<Order> getOrderByPagesAndStatusAndUid(long uid, long cid, int status, int page, int limit) {
        Preconditions.checkArgument(uid > 0);
        Preconditions.checkArgument(cid > 0);
        Preconditions.checkArgument(page > 0);
        Preconditions.checkArgument(limit > 0);
        int start = (page - 1) * limit;
        List<Order> orders = this.orderDao.findOrderByPageAndStatusAndUid(uid, cid, status, start, limit);
        if (orders != null) {
            //补充Item信息
            orders.stream().forEach(order -> {
                List<OrderItem> orderItems = this.orderDao.findOrderItemByOrderId(order.getOrder_id());
                order.setItems(orderItems);
            });
        }
        return orders;
    }

    @Override
    public List<Order> getOrderBySuperOrderId(String superOrderId) {
        Preconditions.checkNotNull(superOrderId);
        List<Order> orders = this.orderDao.findOrderBySuperOrderId(superOrderId);
        if (orders != null) {
            orders.stream().forEach(order -> {
                order.setItems(this.orderDao.findOrderItemByOrderId(order.getOrder_id()));
            });
        }
        return orders;
    }

    @Override
    public Order getOrderById(long id) {
        Preconditions.checkArgument(id > 0);
        Order order = this.orderDao.findOrderById(id);
        if (order != null) {
            List<OrderItem> orderItems = this.orderDao.findOrderItemByOrderId(order.getOrder_id());
            order.setItems(orderItems);
            //将OrderItem转成GoodsList
            if (orderItems != null) {
                final List<PayGateway.Glist> glistList = Lists.newArrayList();
                orderItems.stream().forEach(orderItem -> {
                    PayGateway.Glist glist = new PayGateway.Glist();
                    glist.setGname(orderItem.getGname());
                    glist.setAmount_price(orderItem.getTotal_amount() * 100);
                    glist.setTotal(orderItem.getTotal());
                    glistList.add(glist);
                });
                order.setGoodsList(glistList);
            }

        }
        return order;
    }

    @Override
    public int countOrderByStatusAndCompany(long cid, int status) {
        Preconditions.checkArgument(cid > 0);
        Preconditions.checkArgument(status > 0);
        return this.orderDao.countOrderByCidAndStatus(cid, status);
    }

    @Override
    public int countOrderByTimeAndStatusAndCompany(Date start, Date end, int status) {
        Preconditions.checkNotNull(start);
        Preconditions.checkNotNull(end);
        return this.orderDao.countByTimeAndCidAndStatus(status, start, end);
    }

    @Override
    public List<Order> getOrderByTimeAndStatus(Date start, Date end, int status, int pages, int size) {
        Preconditions.checkNotNull(start);
        Preconditions.checkNotNull(end);
        Preconditions.checkNotNull(pages > 0);
        Preconditions.checkNotNull(size > 0);
        int startRow = (pages - 1) * size;
        List<Order> orders = this.orderDao.findOrderByTimeAndStatus(start, end, status, startRow, size);
        if (orders != null) {
            //补充Item信息
            orders.stream().forEach(order -> {
                List<OrderItem> orderItems = this.orderDao.findOrderItemByOrderId(order.getOrder_id());
                order.setItems(orderItems);
            });
        }
        return orders;
    }

    @Override
    public int countOrderByStatusAndCompanyAndUid(long uid, long cid, int status) {
        Preconditions.checkArgument(uid > 0);
        Preconditions.checkArgument(cid > 0);
        return this.orderDao.countOrderByUidAndStatusAndPage(cid, uid, status);
    }

    @Override
    public List<Order> getOrderBySalemanAndStatusAndPage(long suid, int status, int page, int limit) {
        Preconditions.checkArgument(suid > 0);
        Preconditions.checkArgument(page > 0);
        Preconditions.checkArgument(limit > 0);
        int start = (page - 1) * limit;
        List<Order> orders = null;
        if (status == -1) {
            //全部
            orders = this.orderDao.findOrderByPageAndSuid(suid, start, limit);
        } else if (status == 3) {
            //已经确认, 包括状态3,4
            orders = this.orderDao.findSureOrderByPageAndSuid(suid, start, limit);

        } else {
            orders = this.orderDao.findOrderByPageAndStatusAndSuid(suid, status, start, limit);
        }

        if (orders != null) {
            //补充Item信息
            orders.stream().forEach(order -> {
                List<OrderItem> orderItems = this.orderDao.findOrderItemByOrderId(order.getOrder_id());
                order.setItems(orderItems);
            });
        }
        return orders;
    }

    @Override
    public int countOrderBySalemanAndStatusAndPage(long suid, int status) {
        Preconditions.checkArgument(suid > 0);
        int res = 0;
        if (status == -1) {
            res = this.orderDao.countOrderBySaleman(suid);
        } else if (status == 3) {
            //已经确认, 包括状态3,4
            res = this.orderDao.countSureOrderBySalemanPage(suid);
        } else {
            res = this.orderDao.countOrderBySalemanAndStatusAndPage(suid, status);
        }
        return res;
    }

    @Override
    public int countOrderBySaleman(long suid) {
        Preconditions.checkNotNull(suid > 0);
        return this.orderDao.countOrderBySaleman(suid);
    }

    @Override
    public List<Order> getOrderByStatus(int status) {
        Preconditions.checkArgument(status >= 0);
        List<Order> orders = this.orderDao.findOrderByStatus(status);
        if (orders != null) {
            //补充Item信息
            orders.stream().forEach(order -> {
                List<OrderItem> orderItems = this.orderDao.findOrderItemByOrderId(order.getOrder_id());
                order.setItems(orderItems);
            });
        }
        return orders;
    }

    @Override
    public void updateOrderStatus(String orderno, int status) {
        Preconditions.checkNotNull(orderno);
        Preconditions.checkArgument(status > 0);
        this.orderDao.updateStatusOrderByOrderId(orderno, status, new Date());
    }

    @Override
    public void updateOrderIsPay(String orderno, int ispay) {
        Preconditions.checkNotNull(orderno);
        Preconditions.checkArgument(ispay == 9 || ispay == 10);
        CouponDetail couponDetail = this.couponDetailDao.getCouponDetailByOrderNo(orderno);
        if (couponDetail != null) {
            if (ispay == 9) {
                //表示已支付

                if (couponDetail.getStatus() == 2) {
                    Coupon coupon = this.couponDetailDao.getCouponById(couponDetail.getCoupon_id());
                    if (coupon != null && coupon.getCoupon_status() == 2) {
                        if (coupon.getCoupon_use_start().getTime() <= System.currentTimeMillis() && coupon.getCoupon_use_end().getTime() >= System.currentTimeMillis()) {
                            //修改红包详情状态为可用
                            couponDetail.setStatus(3);
                        } else {
                            //修改为不可用
                            couponDetail.setStatus(8);
                        }
                    } else {
                        //修改为不可用
                        couponDetail.setStatus(8);
                    }
                    this.couponDetailDao.updateCouponDetail(couponDetail);
                }
            } else {
                //表示取消支付
                couponDetail.setStatus(8);
                this.couponDetailDao.updateCouponDetail(couponDetail);
            }
        }
        this.orderDao.updateIspayOrderByOrderId(orderno, ispay, new Date());
    }

    @Override
    public void updateSendCar(String orderno, int status) {
        Preconditions.checkNotNull(orderno);
        this.orderDao.updateSendCarOrderByOrderId(orderno, status, new Date());
    }

    @Override
    public void updateErpOrderId(String orderno, String erp_order_no) {
        Preconditions.checkNotNull(orderno);
        Preconditions.checkNotNull(erp_order_no);
        this.orderDao.updateErpOrderNo(orderno, erp_order_no, new Date());
    }

    @Override
    public void cancelOrder(long id, long cid) {
        Preconditions.checkArgument(id > 0);
        Preconditions.checkArgument(cid > 0);
        Order order = this.orderDao.findOrderById(id);

        if (order != null) {
            order.setId(id);
            if (order.getCid() == cid) {
                if (order.getStatus() == 0) {
                    this.orderDao.updateOrderById(id, CONST.ORDER_STATUS_DELETE);
                } else if (order.getStatus() == 1) {
                    //还需要通知ERP那边取消订单,或者使用同步的方式
                    //1,选取消ERP
                    try {
                        this.noticErpCancelOrder(order);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void cancelOrderByUid(long id, long uid) {
        Preconditions.checkArgument(id > 0);
        Preconditions.checkArgument(uid > 0);
        Order order = this.orderDao.findOrderById(id);

        if (order != null) {
            order.setId(id);
            if (order.getUid() == uid || order.getSuid() == uid) {
                if (order.getStatus() == 0) {
                    this.orderDao.updateOrderById(id, CONST.ORDER_STATUS_DELETE);
                } else if (order.getStatus() == 1) {
                    //还需要通知ERP那边取消订单,或者使用同步的方式
                    //1,选取消ERP
                    try {
                        this.noticErpCancelOrder(order);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void addOrderOper(String orderno, OrderOper orderOper) {
        Preconditions.checkNotNull(orderno);
        Preconditions.checkNotNull(orderOper);
        Order order = this.orderDao.findOrderByOrderId(orderno);
        if (order != null) {
            orderOper.setOrder_id(order.getId());
            orderOper.setOrder_no(order.getOrder_id());
            this.orderDao.insertOrderOper(orderOper);
        }
    }

    @Override
    public List<OrderOper> getOrderTraceInfoById(long id) {
        Preconditions.checkArgument(id > 0);
        return this.orderDao.findOrderOperInfoByOrderId(id);
    }

    @Override
    public List<OrderOper> getOrderTraceInfoByOrderNo(String orderNo) {
        Preconditions.checkNotNull(orderNo);
        return this.orderDao.findOrderOperInfoByOrderNo(orderNo);
    }

    /**
     * 通知ERP取消订单
     *
     * @param order
     */
    private void noticErpCancelOrder(Order order) throws Exception {
        //取消ERP的订单
        String order_id = order.getOrder_id();
        if (!Strings.isNullOrEmpty(cancelOrderUrl) && !Strings.isNullOrEmpty(order_id)) {
            HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();
            String res = httpClientUtil.getResponseBodyAsString(cancelOrderUrl + order_id, null);
            if (!Strings.isNullOrEmpty(res)) {
                //如果ERP没有问题再，更新库里面
                if (res.indexOf("0000") >= 0) {
                    this.orderDao.updateOrderById(order.getId(), CONST.ORDER_STATUS_DELETE);
                }
            }
        }
    }
}
