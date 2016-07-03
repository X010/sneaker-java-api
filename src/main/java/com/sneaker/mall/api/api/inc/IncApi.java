package com.sneaker.mall.api.api.inc;

import com.google.common.base.Strings;
import com.sneaker.mall.api.model.*;
import com.sneaker.mall.api.service.*;
import com.sneaker.mall.api.service.impl.B2CompanyService;
import com.sneaker.mall.api.util.JsonParser;
import com.sneaker.mall.api.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
 * 内部API
 */
@Controller
@RequestMapping(value = "/inc")
public class IncApi {

    private Logger logger = LoggerFactory.getLogger(IncApi.class);

    @Autowired
    private StoreService storeService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private B2CompanyService companyService;

    @Autowired
    private GTypeService gTypeService;

    /**
     * @param request
     * @param response
     * @return
     * @apiDescription 同步ERP订单信息到商城
     * @api {get} /inc/orderStatus.do
     * @apiName 同步ERP订单信息到商城
     * @apiGroup inc
     * @apiParam {String} [orderNo] 订单的ERP编号
     * @apiParam {Number} [status] 同步状态  3 表示审核订单 4表示创建出库单 5表示审核出库单 8表示取消订单 9表示结算 10表示未结算,11表示已派车,12表示取消派车,13 表示没货待配,99表示错误
     * @apiParam {String} [action] 操作详细信息,第一步都应该记录详细信息
     * @apiSuccess {Number} [status] 状态
     * @apiSuccess {string} [message] 消息
     * <p>
     * 回调通知订单
     */
    @ResponseBody
    @RequestMapping(value = "orderStatus.do")
    public String callbackOrder(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<String> responseMessage = new ResponseMessage<>(300, "parameter is error");
        String orderNo = request.getParameter("orderno"); //商城订单编号
        String status = request.getParameter("status"); //订单状态
        String action = request.getParameter("action");// 执行操作说明
        String erpOrderNo = request.getParameter("erporderno");//ERP订单号
        String sign = request.getParameter("sign"); //签名
        logger.info(erpOrderNo + "," + orderNo + "," + action + "," + status);
        if (!Strings.isNullOrEmpty(orderNo) && !Strings.isNullOrEmpty(status)) {
            int iStatus = Integer.parseInt(status);
            //3 表示审核订单 4表示创建出库单 5表示审核出库单 8表示取消订单 9表示结算 10表示未结算,11表示已派车,12表示取消派车，13表示没货待配
            if (iStatus == 3 || iStatus == 4 || iStatus == 5 || iStatus == 8 || iStatus == 9 || iStatus == 10 || iStatus == 11 || iStatus == 12 || iStatus == 13||iStatus==99) {
                if (iStatus == 9 || iStatus == 10) {
                    this.orderService.updateOrderIsPay(orderNo, iStatus);
                } else if (iStatus == 11 || iStatus == 12) {
                    //派车字段更新
                    this.orderService.updateSendCar(orderNo, (iStatus == 11 ? 1 : 0));
                } else {
                    this.orderService.updateOrderStatus(orderNo, iStatus);
                }
                OrderOper orderOper = new OrderOper();
                orderOper.setStatus(iStatus);
                orderOper.setOper_time(new Date());
                orderOper.setAction(action);
                this.orderService.addOrderOper(orderNo, orderOper);
            }

            if (!Strings.isNullOrEmpty(erpOrderNo)) {
                //当ERP订单号不为空时，则更新ERP的订单号
                this.orderService.updateErpOrderId(orderNo, erpOrderNo);
            }
            responseMessage.setStatus(200);
            responseMessage.setMessage("success");
        }
        return JsonParser.simpleJson(responseMessage);
    }

    /**
     * 读取品牌信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getBrand.action")
    public String getBrand(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<List<Brand>> responseMessage = new ResponseMessage<>(200, "success");
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonParser.simpleJson(responseMessage);
    }

    /**
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getGtype.action")
    public String getGType(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<List<GType>> responseMessage = new ResponseMessage<>(200, "success");
        try {
            long cid = RequestUtil.getLong(request, "cid", 0); //公司ID
            String parentCode = RequestUtil.getString(request, "code", null); //父级分类的Code
            List<GType> gTypes = null;
            if (!Strings.isNullOrEmpty(parentCode)) {
                //查询子父栏目
                gTypes = this.gTypeService.getGtypeForCid(cid, parentCode + "__");
            } else {
                //查询顶级栏目
                gTypes = this.gTypeService.getGtypeForCid(cid, "__");
            }
            responseMessage.setData(gTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonParser.simpleJson(responseMessage);
    }


    /**
     * 业务管理后台登陆
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "businessadminlogin.action")
    public String businessAdminLogin(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<User> responseMessage = new ResponseMessage<User>(200, "success");
        try {
            String username = RequestUtil.validateParam(request, "username", responseMessage);
            String password = RequestUtil.validateParam(request, "password", responseMessage);
            User user = this.userService.loginForSalesAdminMan(username, password);
            if (user != null) {
                user.setCom(this.companyService.getCompanyById(user.getCid()));
            }
            responseMessage.setData(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonParser.simpleJson(responseMessage);
    }


    /**
     * 根据公司查询仓库
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getstorebycid.action")
    public String getStoreByCid(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<List<Store>> responseMessage = new ResponseMessage<>(200, "success");
        try {
            long cid = RequestUtil.getLong(request, "cid", 0);
            List<Store> stores = this.storeService.getStoreByCid(cid);
            if (stores != null) {
                responseMessage.setData(stores);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonParser.simpleJson(responseMessage);
    }
}
