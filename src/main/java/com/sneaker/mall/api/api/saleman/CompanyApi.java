package com.sneaker.mall.api.api.saleman;

import com.google.common.base.Strings;
import com.sneaker.mall.api.exception.SessionException;
import com.sneaker.mall.api.model.Company;
import com.sneaker.mall.api.model.Customer;
import com.sneaker.mall.api.model.ResponseMessage;
import com.sneaker.mall.api.service.impl.B2CompanyService;
import com.sneaker.mall.api.service.impl.B2UserServiceImpl;
import com.sneaker.mall.api.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
@Controller("salemancompanyapi")
@RequestMapping(value = "/saleman/com")
public class CompanyApi extends BaseController {
    @Autowired
    private B2CompanyService companyService;

    /**
     * 获取客户列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "customer.action")
    public void getCustomer(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<List<Company>> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            List<Company> companies = this.companyService.getCustomForCompanyAndUser(getLoginUser(request));
            if (companies != null) {
                responseMessage.setData(companies);
            }
        } catch (SessionException e) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("need login");
        } catch (Exception e1) {
            responseMessage.setStatus(400);
            responseMessage.setMessage("service is error");
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }

    /**
     * 客户搜索
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "searchcustomer.action")
    public void searchCustomer(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<Set<Map.Entry<String, List<Company>>>> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            String k = RequestUtil.validateParam(request, "k", responseMessage);
            List<Company> companies = this.companyService.searchCustomForUserAndKeyword(getLoginUser(request), k);
            if (companies != null) {
                companies.stream().forEach(com -> {
                    com.setPinyin(PingYinUtil.getFirstSpellForSentence(com.getName()).toUpperCase());
                    //com.setTypeStr(TypeUtil.getTranslate(com.getType()));
                });

                Map<String, List<Company>> noSortMap = companies.stream().collect(Collectors.groupingBy(Company::getPinyin));

                //对Map排序
                Map<String, List<Company>> sortMap = CollectUtil.sortMapByKey(noSortMap);

                responseMessage.setData(sortMap.entrySet());
            }
        } catch (SessionException e) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("need login");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }

    /**
     * 获取公司信息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "info.action")
    public void getCompany(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<Company> responseMessage = new ResponseMessage<Company>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        String id = RequestUtil.validateParam(request, "id", null);
        if (!Strings.isNullOrEmpty(id)) {
            try {
                Company company = this.companyService.getCompanyById(Long.valueOf(id));
                if (company != null) {
                    responseMessage.setData(company);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }

    /**
     * 保存公司信息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "edit.action")
    public void editCompanyInfo(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<String> responseMessage = new ResponseMessage<String>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        String phone = RequestUtil.getString(request, "phone", null);
        String province = RequestUtil.validateParam(request, "province", responseMessage);//省
        String country = RequestUtil.getString(request, "country", null); //市
        String city = RequestUtil.getString(request, "city", ""); //县
        String street = RequestUtil.getString(request, "street", ""); //街道
        String address = RequestUtil.getString(request, "address", ""); //详细地址
        long id = RequestUtil.getLong(request, "id", 0);
        String contractor = RequestUtil.validateParam(request, "contractor", responseMessage); //联系人
        try {
            if (id > 0 && !Strings.isNullOrEmpty(contractor) && !Strings.isNullOrEmpty(phone) && !Strings.isNullOrEmpty(address) && !Strings.isNullOrEmpty(province)) {
                //更新
                address = String.format("%s %s %s %s %s", province, city, country, street, address);
                Company company = new Company();
                company.setId(id);
                company.setContactor(contractor);
                company.setContactor_phone(phone);
                company.setAddress(address);
                this.companyService.saveCompany(company);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setStatus(400);
            responseMessage.setMessage("接口异常");
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }

    /**
     * 获取拼音列表
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "customerpinyin.action")
    public void getCustomerByPinyin(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<Set<Map.Entry<String, List<Company>>>> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);

        try {
            List<Company> companies = this.companyService.getCustomForCompanyAndUser(getLoginUser(request));
            if (companies != null) {
                companies.stream().forEach(com -> {
                    com.setPinyin(PingYinUtil.getFirstSpellForSentence(com.getName()).toUpperCase());
                    //com.setTypeStr(TypeUtil.getTranslate(com.getType()));
                });

                Map<String, List<Company>> noSortMap = companies.stream().collect(Collectors.groupingBy(Company::getPinyin));

                //对Map排序
                Map<String, List<Company>> sortMap = CollectUtil.sortMapByKey(noSortMap);

                if (sortMap != null && sortMap.size() > 0) {
                    responseMessage.setData(sortMap.entrySet());
                }
            }
        } catch (SessionException e) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("need login");
        } catch (Exception e1) {
            responseMessage.setStatus(400);
            responseMessage.setMessage("service is error");
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }

    /**
     * 获取拼音列表
     * 从用户关系中获取
     * @param request
     * @param response
     */
    @RequestMapping(value = "customerpy.action")
    public void getCustomerByPy(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<Set<Map.Entry<String, List<Company>>>> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);

        try {
            List<Customer> companies = this.companyService.getCustomerForCompanyAndUser(getLoginUser(request));
            if (companies != null) {
                companies.stream().forEach(com -> {
                    //com.setPinyin(PingYinUtil.getFirstSpellForSentence(com.getCcname()).toUpperCase());
                    com.setPinyin(com.getCcpyname().substring(0,1).toUpperCase());
                    com.setType(Integer.valueOf(com.getCctype()));
                    com.setId(com.getCcid());
                    com.setName(com.getCcname());
                    //com.setTypeStr(TypeUtil.getTranslate(com.getType()));
                });

                Map<String, List<Company>> noSortMap = companies.stream().collect(Collectors.groupingBy(Company::getPinyin));

                //对Map排序
                Map<String, List<Company>> sortMap = CollectUtil.sortMapByKey(noSortMap);

                if (sortMap != null && sortMap.size() > 0) {
                    responseMessage.setData(sortMap.entrySet());
                }
            }
        } catch (SessionException e) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("need login");
        } catch (Exception e1) {
            responseMessage.setStatus(400);
            responseMessage.setMessage("service is error");
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }

    /**
     * 客户搜索  从关系中获取
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "searchcust.action")
    public void searchCust(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<Set<Map.Entry<String, List<Company>>>> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            String k = RequestUtil.validateParam(request, "k", responseMessage);
            List<Customer> companies = this.companyService.searchCustomerForUserAndKeyword(getLoginUser(request), k);
            if (companies != null) {
                companies.stream().forEach(com -> {
                    //com.setPinyin(PingYinUtil.getFirstSpellForSentence(com.getName()).toUpperCase());
                    com.setPinyin(com.getCcpyname().substring(0,1).toUpperCase());
                    com.setType(Integer.valueOf(com.getCctype()));
                    com.setName(com.getCcname());
                });

                Map<String, List<Company>> noSortMap = companies.stream().collect(Collectors.groupingBy(Company::getPinyin));

                //对Map排序
                Map<String, List<Company>> sortMap = CollectUtil.sortMapByKey(noSortMap);

                responseMessage.setData(sortMap.entrySet());
            }
        } catch (SessionException e) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("need login");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }
}
