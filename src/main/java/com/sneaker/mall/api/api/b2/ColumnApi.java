package com.sneaker.mall.api.api.b2;

import com.sneaker.mall.api.exception.SessionException;
import com.sneaker.mall.api.model.Column;
import com.sneaker.mall.api.model.ResponseMessage;
import com.sneaker.mall.api.model.User;
import com.sneaker.mall.api.service.ColumnService;
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
@RequestMapping(value = "/b2/column")
public class ColumnApi extends BaseController {

    @Autowired
    private ColumnService columnService;

    @RequestMapping(value = "get.action")
    public void colums(HttpServletRequest request, HttpServletResponse response) {
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        ResponseMessage<List<Column>> responseMessage = new ResponseMessage<>(200, "success");
        try {
            String cid = RequestUtil.validateParam(request, "cid", responseMessage);
            long lcid = Long.valueOf(cid);
            List<Column> columns = this.columnService.getColumnByCompanyId(lcid);
            if (columns != null) {
                responseMessage.setData(columns);
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


    @RequestMapping(value = "getad.action")
    public void columsad(HttpServletRequest request, HttpServletResponse response) {
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        ResponseMessage<List<Column>> responseMessage = new ResponseMessage<>(200, "success");
        try {
            String cid = RequestUtil.validateParam(request, "cid", responseMessage);
            long lcid = Long.valueOf(cid);
            List<Column> columns  = this.columnService.getAdByCompanyId(lcid);
            if (columns != null) {
                responseMessage.setData(columns);
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
}
