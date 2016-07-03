package com.sneaker.mall.api.api.saleman;

import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.sneaker.mall.api.exception.ParameterException;
import com.sneaker.mall.api.exception.SessionException;
import com.sneaker.mall.api.market.goods.Strategy;
import com.sneaker.mall.api.model.*;
import com.sneaker.mall.api.service.*;
import com.sneaker.mall.api.service.impl.B2CompanyService;
import com.sneaker.mall.api.util.CONST;
import com.sneaker.mall.api.util.JsonParser;
import com.sneaker.mall.api.util.RequestUtil;
import com.sneaker.mall.api.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
@Controller("salemanorderapi")
@RequestMapping(value = "/saleman/order")
public class OrderApi extends BaseController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OfcService ofcService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AddresseeService addresseeService;

    @Autowired
    private B2CompanyService companyService;

    @Autowired
    private PriceService priceService;

    @Autowired
    @Qualifier("bindGoods")
    private Strategy bindStrategy;

    @Autowired
    @Qualifier("originalGoods")
    private Strategy originalStrategy;

    @Autowired
    @Qualifier("pkgGoods")
    private Strategy pkgStrategy;

    @Autowired
    @Qualifier("default_img_url")
    private String default_img_url;

    @Autowired
    @Qualifier("img_server_prefix")
    private String img_server_prefix;


    /**
     * 跟踪订单信息
     *
     * @param request
     * @param response
     */
    @RequestMapping("followOrder.action")
    public void followOrder(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<String> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            long orderid = RequestUtil.getLong(request, "id", 0);
            if (orderid > 0) {

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
     * 取消订单
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "cancel.action")
    public void cancelOrder(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<String> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            long orderid = RequestUtil.getLong(request, "id", 0);
            if (orderid > 0) {
                this.orderService.cancelOrderByUid(orderid, getLoginUser(request).getId());
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
     * 创建订单
     * OrderItem   total,scid,mgid,
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "createorder.action")
    public void createOrder(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<String> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            String sorderItem = RequestUtil.validateParam(request, "orderItem", responseMessage);
            String ccid = RequestUtil.validateParam(request, "ccid", responseMessage);
            long addresseeId = RequestUtil.getLong(request, "addresseeId", 0);
            String delivery = RequestUtil.getString(request, "delivery", "0");
            float favorable = RequestUtil.getFloat(request, "favorable", 0); //优惠金额
            String memo = request.getParameter("memo"); //备注
            int pt = RequestUtil.getInt(request, "pt", 1);//在线支付
            long cid = getLoginUser(request).getCid();
            final Company company = this.companyService.getCompanyById(Long.valueOf(ccid));

            Preconditions.checkNotNull(company);

            //返序列化OrderItem
            List<OrderItem> orderItems = (List<OrderItem>) JsonParser.parser(sorderItem, new TypeReference<List<OrderItem>>() {
            });

            //补充商品信息并校验订单的项的有效性
            final List<OrderItem> realOrderList = Lists.newArrayList();
            final List<Order> tempOrder = Lists.newArrayList();

            orderItems.stream().forEach(orderItem -> {
                if (orderItem.getMgid() > 0 && orderItem.getTotal() > 0 && orderItem.getScid() > 0) {
                    //补充商品信息
                    Goods goods = this.goodsService.getGoodsById(orderItem.getMgid(), getLoginUser(request).getCid(), Long.valueOf(ccid));
                    if (goods != null && goods.getCompany_id() == orderItem.getScid()) {
                        Customer customer = this.companyService.getCustomerByCidAndScid(company.getId(), orderItem.getScid());
                        if (goods.getPublish() == CONST.GOODS_PUBLISH_ONLINE) {
                            if (customer == null) {
                                throw new ParameterException("this gid [" + orderItem.getMgid() + " is not supplier for db]");
                            } else if (customer.getSuid() > 0) {
                                orderItem.setSuid(getLoginUser(request).getId());
                            }

                            Order itemOrder = null;
                            if (goods.getIsbind() == 1 && goods.getPkgSize() == 0) {
                                //绑定数据
                                itemOrder = this.bindStrategy.perfect(cid
                                        , Long.valueOf(ccid)
                                        , getLoginUser(request)
                                        , goods
                                        , customer
                                        , orderItem
                                        , memo
                                        , true);

                            } else if (goods.getIsbind() == 0 && goods.getPkgSize() == 1) {
                                //大小包状,如果是大包装则将需将数量进行换算
                                itemOrder = this.pkgStrategy.perfect(cid
                                        , Long.valueOf(ccid)
                                        , getLoginUser(request)
                                        , goods
                                        , customer
                                        , orderItem
                                        , memo
                                        , true);

                            } else if (goods.getIsbind() == 0 && goods.getPkgSize() == 0) {
                                //原始商品分支
                                //判断该商品是否有营销活动,如果有活动.需要将问题信息获取到
                                itemOrder = this.originalStrategy.perfect(cid
                                        , Long.valueOf(ccid)
                                        , getLoginUser(request)
                                        , goods
                                        , customer
                                        , orderItem
                                        , memo
                                        , true);
                            }
                            if (itemOrder != null) {
                                tempOrder.add(itemOrder);
                                realOrderList.addAll(itemOrder.getItems());
                            }
                        }
                    } else {
                        throw new ParameterException("该客户不允许购买该商品");
                    }
                } else {
                    throw new ParameterException("订单参数错误");
                }
            });

            if (realOrderList.size() > 0) {
                //对订单进行拆分
                Order order = this.ofcService.separateOrder(realOrderList);

                //保存订单信息
                if (order != null) {
                    //把收件人信息读取出来
                    if (addresseeId > 0) {
                        final Addressee addressee = this.addresseeService.getAddresseeById(addresseeId);
                        if (addressee == null)
                            throw new ParameterException("Addressee Info is Error");

                        //完善订单信息
                        order.setDelivery(delivery);
                        order.setCid(company.getId());
                        order.setCompany_name(company.getName());
                        order.setScid(cid);
                        order.setCreate_time(new Date());
                        order.setSuid(getLoginUser(request).getId());
                        order.setStatus(0);
                        order.setUid(getLoginUser(request).getId());
                        order.setContacts(addressee.getContacts());
                        order.setPhone(addressee.getPhone());
                        order.setPt(pt);
                        order.setFavorable(favorable);
                        order.setReceipt(String.format("%s %s %s %s",
                                (!Strings.isNullOrEmpty(addressee.getProvince()) && (!"null".equals(addressee.getProvince())) ? addressee.getProvince() : ""),
                                (!Strings.isNullOrEmpty(addressee.getCity()) && (!"null".equals(addressee.getCity())) ? addressee.getCity() : ""),
                                (!Strings.isNullOrEmpty(addressee.getCounty()) && (!"null".equals(addressee.getCounty())) ? addressee.getCounty() : ""),
                                (!Strings.isNullOrEmpty(addressee.getStreet()) && (!"null".equals(addressee.getStreet())) ? addressee.getStreet() : "")));
                        order.setMemo(memo);
                        Company sCompany = this.companyService.getCompanyById(order.getScid());
                        if (company == null) {
                            throw new ParameterException("Company [" + order.getScid() + " is not found] ");
                        }
                        order.setSupplier_company_name(sCompany.getName());

                        //计算订单总价
                        Double doublePrice = tempOrder.stream().filter(o -> o.getTotal_amount() > 0).mapToDouble(Order::getTotal_amount).sum();
                        order.setTotal_amount(doublePrice.floatValue());
                        //订单总金额,还需要减去优惠金额
                        if (favorable > 0 && order.getTotal_amount() > favorable) {
                            order.setTotal_amount(order.getTotal_amount() - favorable);
                        } else {
                            order.setFavorable(0);
                        }
                    }
                    //保存数据
                    List<Order> orders = Lists.newArrayList();
                    order.setItems(realOrderList);
                    orders.add(order);
                    this.orderService.saveOrder(orders);

                    if (orders.size() > 0) {
                        responseMessage.setData(orders.get(0).getSuper_order_id());
                    }
                }
            }
        } catch (SessionException se) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("login fail");
            se.printStackTrace();
        } catch (ParameterException pe) {
            responseMessage.setMessage(pe.getMessage());
            responseMessage.setStatus(400);
            pe.printStackTrace();
        } catch (Exception e) {
            responseMessage.setMessage("订单信息有误");
            responseMessage.setStatus(400);
            e.printStackTrace();
        }
        //跨域设置
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("XDomainRequestAllowed", "1");
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }

    /**
     * 读取订单信息根据订单ID
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "orderinfobyid.action")
    public void getOrderInfoById(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<Order> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            String id = RequestUtil.validateParam(request, "id", responseMessage);
            Order order = this.orderService.getOrderById(Long.valueOf(id));
            if (order != null) {
                responseMessage.setData(order);
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
     * 根据超级订单号获取订单合集信息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "orderinfo.action")
    public void getOrderInfoBySuperOrderId(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<Order> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            String superOrderId = RequestUtil.validateParam(request, "id", responseMessage);
            List<Order> orders = this.orderService.getOrderBySuperOrderId(superOrderId);
            if (orders != null) {
                Order order = orders.get(0);
                Double aoumt = orders.stream().collect(Collectors.summingDouble(sorder -> sorder.getTotal_amount()));
                order.setSuper_order_id(superOrderId);
                order.setTotal_amount(aoumt.floatValue());
                responseMessage.setData(order);
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
     * 读取订单接口,读取某客户下面的该业务员的订单
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "order.action")
    public void order(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<List<Order>> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            int page = RequestUtil.getInt(request, "page", CONST.DEFAULT_PAGE_START);
            int limit = RequestUtil.getInt(request, "limit", CONST.DEFAULT_PAGE_SIZE, CONST.LIMIT_MIN, CONST.LIMIT_MAX);
            int status = RequestUtil.getInt(request, "status", -1);
            String ccid = RequestUtil.validateParam(request, "ccid", responseMessage); //读取客户ID
            List<Order> orders = null;
            int count = 0;
            if (status == -1) {
                //非按状态分页面读取订单列表
                orders = this.orderService.getOrderByUidAndPages(getLoginUser(request).getId(), Long.valueOf(ccid), page, limit);
                count = this.orderService.countOrderByCompanyAndUid(getLoginUser(request).getId(), Long.valueOf(ccid));
            } else {
                //按状态分页面读取订单列表
                orders = this.orderService.getOrderByPagesAndStatusAndUid(getLoginUser(request).getId(), Long.valueOf(ccid), status, page, limit);
                count = this.orderService.countOrderByStatusAndCompanyAndUid(getLoginUser(request).getId(), Long.valueOf(ccid), status);
            }
            responseMessage.setCount(count);
            responseMessage.setData(orders);
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
     * 读取业务员订单
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "salemanorder.action")
    public void salemanOrder(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<List<Order>> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            int page = RequestUtil.getInt(request, "page", CONST.DEFAULT_PAGE_START);
            int limit = RequestUtil.getInt(request, "limit", CONST.DEFAULT_PAGE_SIZE);
            int status = RequestUtil.getInt(request, "status", -1);
            long lsuid = getLoginUser(request).getId();
            List<Order> orders = this.orderService.getOrderBySalemanAndStatusAndPage(lsuid, status, page, limit);
            int count = this.orderService.countOrderBySalemanAndStatusAndPage(lsuid, status);
            responseMessage.setCount(count);
            responseMessage.setData(orders);
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
     * 一键下单读取商品列表
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "akeyorder.action")
    public void aKeyOrder(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<List<Goods>> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            long ccid = RequestUtil.getLong(request, "ccid", 0);
            long orderid = RequestUtil.getLong(request, "id", 0);
            if (ccid > 0 && orderid > 0) {
                //读取订单看那些商品是这家客户可以买的
                Order order = this.orderService.getOrderById(orderid);
                List<Goods> goodses = this.goodsService.getOrderForAKeyOrder(order, ccid, getLoginUser(request).getCid());
                if (goodses != null) this.priceService.supplementGoodsPrice(goodses, ccid);
                responseMessage.setData(goodses);
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
     * @param request
     * @param response
     * @apiDescription 获取送货紧程度列表
     * @api {get} /saleman/order/delivery.action [送货紧急程序列表]
     * @apiGroup saleman
     * @apiName 获取送货紧急程序列表
     * @apiSuccess {String} [value] 值
     * @apiSuccess {String} [memo] 显示值
     */
    @RequestMapping(value = "delivery.action")
    public void getDeliveryGoods(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<List<ERPConfigDetail>> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            List<ERPConfigDetail> erpConfigDetails = this.orderService.getDeliverGoods();
            if (erpConfigDetails != null) {
                responseMessage.setData(erpConfigDetails);
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
     * @param request
     * @param response
     * @apiDescription 跟踪订单的处理信息
     * @api {get}  /saleman/order/traceorder.action  [订单跟踪]
     * @apiGroup saleman
     * @apiName 跟踪订单的处理信息
     * @apiParam {Number} [orderid] 订单号
     * @apiSuccess {Number} [status] 状态,200表示正常
     * @apiSuccess {String} [message] 消息
     * @apiSuccess {Object} [data] 数据体
     */
    @RequestMapping(value = "traceorder.action")
    public void getOrderTraceInfo(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<List<OrderOper>> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            String orderno = RequestUtil.getString(request, "orderno", null);
            if (!Strings.isNullOrEmpty(orderno)) {
                List<OrderOper> orderOpers = this.orderService.getOrderTraceInfoByOrderNo(orderno);
                if (orderOpers != null) {
                    responseMessage.setData(orderOpers);
                }
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
}
