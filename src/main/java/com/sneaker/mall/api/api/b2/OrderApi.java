package com.sneaker.mall.api.api.b2;

import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.sneaker.mall.api.exception.ParameterException;
import com.sneaker.mall.api.exception.SessionException;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
@Controller
@RequestMapping(value = "/b2/order")
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
    @Qualifier("default_img_url")
    private String default_img_url;

    @Autowired
    @Qualifier("img_server_prefix")
    private String img_server_prefix;

    /**
     * 读取订单接口
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
            List<Order> orders = null;
            int count = 0;
            if (status == -1) {
                //非按状态分页面读取订单列表
                orders = this.orderService.getOrderByPages(getLoginUser(request).getCid(), page, limit);
                count = this.orderService.countOrderByCompany(getLoginUser(request).getCid());
            } else {
                //按状态分页面读取订单列表
                orders = this.orderService.getOrderByPagesAndStatus(getLoginUser(request).getCid(), status, page, limit);
                count = this.orderService.countOrderByStatusAndCompany(getLoginUser(request).getCid(), status);
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
     * 提交订单
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "submit.action")
    public ModelAndView submitOrder(HttpServletRequest request, HttpServletResponse response, ModelAndView model) {
        try {
            // User user = getLoginUser(request);
            // if (user != null) {
            //     model.addObject("data", user);
            // }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.setViewName("submitOrder");
        return model;
    }


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
                this.orderService.cancelOrder(orderid, getLoginUser(request).getCid());
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
            long addresseeId = RequestUtil.getLong(request, "addresseeId", 0);
            String memo = request.getParameter("memo"); //备注
            //返序列化OrderItem
            List<OrderItem> orderItems = (List<OrderItem>) JsonParser.parser(sorderItem, new TypeReference<List<OrderItem>>() {
            });
            //补充商品信息并校验订单的项的有效性
            final List<Goods> readPriceList = Lists.newArrayList();
            final List<OrderItem> realOrderList = Lists.newArrayList();
            orderItems.stream().forEach(orderItem -> {
                if (orderItem.getMgid() > 0 && orderItem.getTotal() > 0 && orderItem.getScid() > 0) {

                    //补充商品信息
                    Goods goods = this.goodsService.getGoodsById(orderItem.getMgid(), orderItem.getScid(), getLoginUser(request).getCid());
                    Customer customer = this.companyService.getCustomerByCidAndScid(getLoginUser(request).getCid(), orderItem.getScid());
                    if (goods != null && goods.getCompany_id() == orderItem.getScid()) {
                        if (goods.getPublish() == CONST.GOODS_PUBLISH_ONLINE) {
                            if (goods.getIsbind() == 0) {
                                //检测是否存在客户与供应商之间原关系
                                if (customer == null) {
                                    throw new ParameterException("this gid [" + orderItem.getMgid() + " is not supplier for db]");
                                } else if (customer.getSuid() > 0) {
                                    orderItem.setSuid(customer.getSuid());
                                }
                                //补充信息
                                orderItem.setGbarcode(goods.getBarcode());
                                orderItem.setGcode(goods.getGcode());
                                orderItem.setGname(goods.getGname());
                                orderItem.setGid(goods.getGid());
                                orderItem.setGphoto(goods.getGphoto());
                                orderItem.setMemo(memo);
                                readPriceList.add(goods);
                                realOrderList.add(orderItem); //将不是打包商品的订单号添加列表中去
                            } else {
                                //查询该商品是不是允许买给该客户类型
                                String cctype = goods.getCctype();
                                String sids = goods.getSids();
                                boolean isCanShop = false;
                                if (!Strings.isNullOrEmpty(cctype) && !Strings.isNullOrEmpty(sids)) {
                                    if (("," + cctype + ",").indexOf("," + customer.getCctype() + ",") >= 0 && ("," + sids + ",").indexOf("," + customer.getSid() + ",") >= 0) {
                                        isCanShop = true;
                                    }
                                }
                                if (isCanShop) {
                                    //如果是打包商品
                                    List<Goods> giveGoods = this.goodsService.getChlidGoodsByMainGoodsId(goods.getId(), orderItem.getScid(), getLoginUser(request).getCid());
                                    //判断里面的主商品是否已经下架
                                    if (giveGoods != null) {
                                        for (Goods isOffliceGoods : giveGoods) {
                                            if (isOffliceGoods.getGiveaway() == CONST.MARKET_GIVEWAYA_MAIN && isOffliceGoods.getPublish() == CONST.GOODS_PUBLISH_OFFLINE) {
                                                isCanShop = false;
                                            }
                                        }
                                    }
                                    if (isCanShop) {
                                        //进行限购判断
                                        orderItem.setTotal(goods.getRestrict() <= 0 ? orderItem.getTotal() : (goods.getRestrict() > orderItem.getTotal() ? orderItem.getTotal() : goods.getRestrict()));

                                        if (giveGoods != null) {
                                            //存在打包商品，开始换算打包商品
                                            giveGoods.stream().forEach(bindGoods -> {
                                                //将这些打包商品细化将转成订单项
                                                OrderItem bindOrderItem = new OrderItem();
                                                bindOrderItem.setScid(orderItem.getScid());
                                                bindOrderItem.setMgid(bindGoods.getId());
                                                bindOrderItem.setBindId(orderItem.getMgid());
                                                int totalNum = bindGoods.getNum() * orderItem.getTotal();
                                                bindOrderItem.setTotal(totalNum);
                                                bindOrderItem.setBindTotal(orderItem.getTotal());
                                                bindOrderItem.setPrice(bindGoods.getCprice() / bindGoods.getNum()); //设置打包商品的价格,赠送商品价格为0
                                                //添加页面员信息
                                                Customer bindCustomer = this.companyService.getCustomerByCidAndScid(getLoginUser(request).getCid(), orderItem.getScid());
                                                if (bindCustomer == null) {
                                                    throw new ParameterException("this gid [" + orderItem.getMgid() + " is not supplier for db]");
                                                } else if (bindCustomer.getSuid() > 0) {
                                                    bindOrderItem.setSuid(bindCustomer.getSuid());
                                                }
                                                bindOrderItem.setGbarcode(bindGoods.getBarcode());
                                                bindOrderItem.setGcode(bindGoods.getGcode());
                                                bindOrderItem.setGname(bindGoods.getGname());
                                                bindOrderItem.setGid(bindGoods.getGid());
                                                bindOrderItem.setGiveaway(bindGoods.getGiveaway());
                                                bindOrderItem.setGphoto(bindGoods.getGphoto());
                                                bindOrderItem.setMemo(memo);
                                                bindOrderItem.setTotal_amount(bindGoods.getCprice() * orderItem.getTotal());
                                                realOrderList.add(bindOrderItem);
                                            });
                                        }
                                    }
                                } else {
                                    throw new ParameterException("该打包商品主商品已下架");
                                }
                            }
                        }
                    } else {
                        throw new ParameterException("您购买的商品不属于该供应商");
                    }
                } else {
                    throw new ParameterException("订单参数错误");
                }
            });
            //补充价格信息
            final Map<String, Float> prices = this.priceService.supplementGoodsPriceReturnMap(readPriceList, getLoginUser(request).getCid());


            if (realOrderList.size() > 0) {
                //对订单进行拆分
                List<Order> orders =null; //this.ofcService.separateOrder(realOrderList);

                //保存订单信息
                if (orders != null) {
                    //把收件人信息读取出来

                    if (addresseeId > 0) {
                        final Addressee addressee = this.addresseeService.getAddresseeById(addresseeId);
                        if (addressee == null)
                            throw new ParameterException("Addressee Info is Error");


                        orders.stream().forEach(order -> {
                            //完善订单信息
                            order.setCid(getLoginUser(request).getCid());
                            order.setCompany_name(getLoginUser(request).getCom().getName());
                            order.setCreate_time(new Date());
                            order.setStatus(0);
                            order.setUid(getLoginUser(request).getId());
                            order.setContacts(addressee.getContacts());
                            order.setPhone(addressee.getPhone());
                            order.setReceipt(String.format("%s,%s,%s,%s",
                                    (!Strings.isNullOrEmpty(addressee.getProvince()) && (!"null".equals(addressee.getProvince())) ? addressee.getProvince() : ""),
                                    (!Strings.isNullOrEmpty(addressee.getCity()) && (!"null".equals(addressee.getCity())) ? addressee.getCity() : ""),
                                    (!Strings.isNullOrEmpty(addressee.getCounty()) && (!"null".equals(addressee.getCounty())) ? addressee.getCounty() : ""),
                                    (!Strings.isNullOrEmpty(addressee.getStreet()) && (!"null".equals(addressee.getStreet())) ? addressee.getStreet() : "")));
                            order.setMemo(memo);
                            Company company = this.companyService.getCompanyById(order.getScid());
                            if (company == null) {
                                throw new ParameterException("Company [" + order.getScid() + " is not found] ");
                            }
                            order.setSupplier_company_name(company.getName());

                            //完善OrderItem信息
                            if (order.getItems() != null) {
                                order.getItems().forEach(orderItem -> {
                                    if (prices != null && prices.get(orderItem.getGcode()) != null && orderItem.getBindId() <= 0) {
                                        orderItem.setPrice(prices.get(orderItem.getGcode()));
                                        orderItem.setBindTotal(orderItem.getTotal());
                                        orderItem.setTotal_amount(orderItem.getPrice() * orderItem.getTotal());
                                    }
                                });
                            }
                        });
                    }
                    //保存数据
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
            pe.printStackTrace();
        } catch (Exception e) {
            responseMessage.setMessage(e.getMessage());
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
                Order order = new Order();
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
}
