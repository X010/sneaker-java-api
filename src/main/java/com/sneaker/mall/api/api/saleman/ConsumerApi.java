package com.sneaker.mall.api.api.saleman;

import com.sneaker.mall.api.exception.ParameterException;
import com.sneaker.mall.api.exception.SessionException;
import com.sneaker.mall.api.model.*;
import com.sneaker.mall.api.service.GTypeService;
import com.sneaker.mall.api.service.StoreService;
import com.sneaker.mall.api.service.impl.B2CompanyService;
import com.sneaker.mall.api.util.CONST;
import com.sneaker.mall.api.util.JsonParser;
import com.sneaker.mall.api.util.RequestUtil;
import com.sneaker.mall.api.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
 * 客户资料录入
 */
@Controller("salemancustomerapi")
@RequestMapping(value = "/saleman/comsumer")
public class ConsumerApi extends BaseController {

    @Autowired
    private B2CompanyService companyService;

    @Autowired
    private GTypeService gTypeService;

    @Autowired
    private StoreService storeService;


    /**
     * @param request
     * @param response
     * @apiDescription 保存申请客户信息
     * @api {get} /saleman/comsumer/save.action [保存客户信息]
     * @apiGroup saleman
     * @apiName 保存申请客户信息
     * @apiParam {Number} [period] 基准日
     * @apiParam {String} [cname]  客户公司名称
     * @apiParam {String} [province] 公司地址省
     * @apiParam {String} [country] 公司地址市
     * @apiParam {String} [city] 公司地址县
     * @apiParam {String} [street] 公司地址街道
     * @apiParam {String} [address] 公司详情地址
     * @apiParam {String} [contractor] 联系人
     * @apiParam {String} [phone] 电话
     * @apiParam {String} [account] 登陆采购平台帐号
     * @apiParam {String} [password] 登陆采购平台密码
     * @apiParam {String} [areatype] 客户类型
     * @apiParam {String} [areapro] 客户经营省
     * @apiParam {String} [areacity] 客户经营市
     * @apiParam {String} [areazone] 客户经营县
     * @apiParam {String} [gtids] 客户经营类型
     * @apiParam {String} [urgent] 是否加急处理
     * @apiParam {String} [ctype] 客户类型
     * @apiSuccess {Number} [status] 状态
     * @apiSuccess {String} [message] 消息
     */
    @RequestMapping(value = "save.action")
    public void saveApplyCustomer(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<String> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            long suid = getLoginUser(request).getId(); //业务员ID
            long cid = getLoginUser(request).getCid();//公司ID
            int period = RequestUtil.getInt(request, "period", 0); //帐期
            String cname = RequestUtil.validateParam(request, "cname", responseMessage); //公司名称
            String province = RequestUtil.validateParam(request, "province", responseMessage);//省
            String country = RequestUtil.getString(request, "country", null); //市
            String city = RequestUtil.getString(request, "city", ""); //县
            String street = RequestUtil.getString(request, "street", ""); //街道
            String address = RequestUtil.getString(request, "address", ""); //详细地址
            String contractor = RequestUtil.validateParam(request, "contractor", responseMessage); //联系人
            String phone = RequestUtil.validateParam(request, "phone", responseMessage); //联系电话
            String account = RequestUtil.getString(request, "account", null); //帐号
            String password = RequestUtil.getString(request, "password", null); //密码
            int ctype = RequestUtil.getInt(request, "ctype", 1); //公司类型
            String areapro = RequestUtil.getString(request, "areapro", null); //经营省
            String areacity = RequestUtil.getString(request, "areacity", null); //经营市
            String areazone = RequestUtil.getString(request, "areazone", ""); //经营县
            String gtids = RequestUtil.getString(request, "gtids", null); //经营类型
            int urgent = RequestUtil.getInt(request, "urgent", 0); //0表示
            String latitude = RequestUtil.getString(request, "latitude", null);
            String longgitude = RequestUtil.getString(request, "longgitude", null);
            String altitude = RequestUtil.getString(request, "altitude", null);
            String accuracy = RequestUtil.getString(request, "accuracy", null);
            String altitudeAccuracy = RequestUtil.getString(request, "altitudeAccuracy", null);
            String heading = RequestUtil.getString(request, "heading", null);
            String speed = RequestUtil.getString(request, "speed", null);
            String timestamp = RequestUtil.getString(request, "timestamp", null);
            long sid = RequestUtil.getLong(request, "sid", 0);
            ApplyCustomer applyCustomer = new ApplyCustomer();
            applyCustomer.setSuid(suid);
            applyCustomer.setSid(sid);
            applyCustomer.setCid(cid);
            applyCustomer.setStatus(0);
            applyCustomer.setCname(cname);
            applyCustomer.setPeriod(period);
            applyCustomer.setProvince(province);
            applyCustomer.setCountry(country);
            applyCustomer.setCity(city);
            applyCustomer.setStreet(street);
            applyCustomer.setAddress(address);
            applyCustomer.setContractor(contractor);
            applyCustomer.setPhone(phone);
            applyCustomer.setAccount(account);
            applyCustomer.setPassword(password);
            applyCustomer.setCtype(ctype);
            applyCustomer.setAreapro(areapro);
            applyCustomer.setAreacity(areacity);
            applyCustomer.setAreazone(areazone);
            applyCustomer.setGtids(gtids);
            applyCustomer.setUrgent(urgent);
            applyCustomer.setCreatetime(new Date());
            GeoLocation geoLocation = new GeoLocation();
            geoLocation.setLatitude(latitude);
            geoLocation.setLonggitude(longgitude);
            geoLocation.setAltitude(altitude);
            geoLocation.setAccuracy(accuracy);
            geoLocation.setAltitudeAccuracy(altitudeAccuracy);
            geoLocation.setHeading(heading);
            geoLocation.setSpeed(speed);
            geoLocation.setTimestamp(timestamp);
            geoLocation.setCreatetime(new Date());
            this.companyService.saveApplyCustomer(applyCustomer, geoLocation);
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
     * @apiDescription 根据状态获取客户申请信息
     * @api {get} /saleman/comsumer/getconsumerbystatus.action [获取申请客户情况]
     * @apiGroup saleman
     * @apiName 获取申请客户情况
     * @apiParam {Number} [status] 状态
     * @apiSuccess {Number} [period] 基准日
     * @apiSuccess {String} [cname]  客户公司名称
     * @apiSuccess {String} [province] 公司地址省
     * @apiSuccess {String} [country] 公司地址市
     * @apiSuccess {String} [city] 公司地址县
     * @apiSuccess {String} [street] 公司地址街道
     * @apiSuccess {String} [address] 公司详情地址
     * @apiSuccess {String} [contractor] 联系人
     * @apiSuccess {String} [phone] 电话
     * @apiSuccess {String} [account] 登陆采购平台帐号
     * @apiSuccess {String} [password] 登陆采购平台密码
     * @apiSuccess {String} [areatype] 客户类型
     * @apiSuccess {String} [areapro] 客户经营省
     * @apiSuccess {String} [areacity] 客户经营市
     * @apiSuccess {String} [areazone] 客户经营县
     * @apiSuccess {String} [gtids] 客户经营类型
     * @apiSuccess {String} [urgent] 是否加急处理
     */
    @RequestMapping(value = "getconsumerbystatus.action")
    public void getApplyCustomerByStatus(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<List<ApplyCustomer>> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            List<ApplyCustomer> applyCustomers = this.companyService.getApplyCustomerByStatus(getLoginUser(request).getId());
            if (applyCustomers != null) {
                applyCustomers.stream().forEach(applyCustomer -> {
                    if (applyCustomer.getCtype() != 0) {
                        applyCustomer.setCtype(applyCustomer.getCtype() - 1);
                    }
                });
                responseMessage.setData(applyCustomers);
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
     * @apiDescription 获取公司经营范围
     * @api {get} /saleman/comsumer/managescope.action [获取公司经营范围]
     * @apiGroup saleman
     * @apiName 获取公司经营范围
     * @apiSuccess {Number} [status] [状态]
     * @apiSuccess {String} [message] [状态]
     */
    @RequestMapping(value = "managescope.action")
    public void managescope(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<List<GType>> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            //经营范围 实际为系统级别的一级分类
            List<GType> gTypes = this.gTypeService.getPlatformGTypeForFirst();
            if (gTypes != null) {
                responseMessage.setData(gTypes);
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
     * 获取仓库列表
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "store.action")
    public void storelist(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<List<Store>> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            long cid = getLoginUser(request).getCid();
            List<Store> stores = this.storeService.getStoreByCid(cid);
            if (stores != null) {
                responseMessage.setData(stores);
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
