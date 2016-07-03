package com.sneaker.mall.api.api.b2;

import com.google.common.base.Strings;
import com.sneaker.mall.api.exception.ParameterException;
import com.sneaker.mall.api.exception.SessionException;
import com.sneaker.mall.api.model.Addressee;
import com.sneaker.mall.api.model.ResponseMessage;
import com.sneaker.mall.api.model.User;
import com.sneaker.mall.api.service.AddresseeService;
import com.sneaker.mall.api.util.CONST;
import com.sneaker.mall.api.util.JsonParser;
import com.sneaker.mall.api.util.RequestUtil;
import com.sneaker.mall.api.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@Controller
@RequestMapping(value = "/b2/addressee")
public class AddresseeApi extends BaseController {

    @Autowired
    private AddresseeService addresseeService;

    /**
     * 保存件人信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "save.action")
    public void saveAddressee(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<String> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            String province = RequestUtil.validateParam(request, "province", responseMessage);
            String city = RequestUtil.getString(request, "city", "");
            String county = RequestUtil.getString(request, "county", "");
            String street = RequestUtil.validateParam(request, "street", responseMessage);
            String contacts = RequestUtil.validateParam(request, "contacts", responseMessage);
            String phone = RequestUtil.validateParam(request, "phone", responseMessage);
            String memo = request.getParameter("memo");
            String def = request.getParameter("def");
            String id = request.getParameter("id");

            Addressee addressee = new Addressee();
            if (!Strings.isNullOrEmpty(id)) {
                addressee.setId(Long.valueOf(id));
            }
            addressee.setProvince(province);
            addressee.setCity(city);
            addressee.setCounty(county);
            addressee.setStreet(street);
            addressee.setContacts(contacts);
            addressee.setPhone(phone);
            addressee.setMemo(memo);
            addressee.setStatus(1);
            addressee.setUid(getLoginUser(request).getId());
            addressee.setCid(getLoginUser(request).getCid());
            if (!Strings.isNullOrEmpty(def)) {
                addressee.setDef(Integer.valueOf(def));
            }
            this.addresseeService.saveAddressee(addressee);
        } catch (ParameterException pe) {
            pe.printStackTrace();
        } catch (SessionException se) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("login fail");
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }

    /**
     * 删除一个收货地址
     *
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(value = "delete.action")
    public void deleteAddressee(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<String> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            String id = RequestUtil.validateParam(request, "id", responseMessage);
            this.addresseeService.deleteAddressee(Long.valueOf(id));
        } catch (ParameterException pe) {
            pe.printStackTrace();
        } catch (SessionException se) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("login fail");
            se.printStackTrace();
        } catch (Exception e) {
            responseMessage.setStatus(400);
            responseMessage.setMessage(e.getMessage());
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }

    /**
     * 设置为默认地址
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "setdefault.action")
    public void setDefaultAddress(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<String> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            long id = RequestUtil.getLong(request, "id", 0);
            if (id > 0) {
                Addressee addressee = this.addresseeService.getAddresseeById(id);
                addressee.setDef(1);
                this.addresseeService.saveAddressee(addressee);
            }
        } catch (SessionException se) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("login Fail");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }

    /**
     * 获取默认收货地址
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "defaddressee.action")
    public void getDefaultAddressee(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<Addressee> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            User user = getLoginUser(request);
            Addressee addressee = this.addresseeService.getDefaultAddresseeByCid(user.getCid());
            if (addressee != null) {
                responseMessage.setData(addressee);
            }
        } catch (SessionException se) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("login Fail");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }


    /**
     * 读取收货地址列表
     *
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(value = "get.action")
    public void getAddressee(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<List<Addressee>> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            User user = this.getLoginUser(request);
            List<Addressee> addressees = this.addresseeService.getAddresseeByCid(user.getCid());
            responseMessage.setData(addressees);
        } catch (SessionException se) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("login Fail");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }
}
