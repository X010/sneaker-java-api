package com.sneaker.mall.api.api.saleman;

import com.google.common.base.Strings;
import com.sneaker.mall.api.exception.ParameterException;
import com.sneaker.mall.api.exception.SessionException;
import com.sneaker.mall.api.model.GeoLocation;
import com.sneaker.mall.api.model.ResponseMessage;
import com.sneaker.mall.api.service.GeoLocationService;
import com.sneaker.mall.api.util.*;
import com.sneaker.mall.api.util.geo.GeoPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 */
@Controller("salemangeoapi")
@RequestMapping("/saleman/geo")
public class GeoLocationApi extends BaseController {

    @Autowired
    private GeoLocationService geoLocationService;

    private Logger logger = LoggerFactory.getLogger(GeoLocationApi.class);

    /**
     * @param request
     * @param response
     * @apiDescription 记录业务员上报的地址位置
     * @api {get} /saleman/geo/geo.action  [上报地址位置]
     * @apiGroup saleman
     * @apiName 记录业务员上报地址位置
     * @apiParam {String} [latitude] 维度
     * @aipParam {String} [longgitude] 经度
     * @apiParam {String} [altitude] 高度
     * @apiParam {String} [accuracy] 精准度
     * @apiParam {String} [altitudeAccuracy] 重直度高
     * @apiParam {String} [heading] 水平高度
     * @apiParam {String} [speed] 速度
     * @apiParam {String} [timestamp] 时间截
     */
    @RequestMapping("geo.action")
    public void saveAddressee(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<String> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            logger.info(request.getRequestURI());
            long ccid = RequestUtil.getLong(request, "ccid", 0);

            if (ccid > 0) {
                long cid = getLoginUser(request).getCid();
                long uid = getLoginUser(request).getId();
                String latitude = RequestUtil.getString(request, "latitude", null);
                String longgitude = RequestUtil.getString(request, "longgitude", null);
                String altitude = RequestUtil.getString(request, "altitude", null);
                String accuracy = RequestUtil.getString(request, "accuracy", null);
                String altitudeAccuracy = RequestUtil.getString(request, "altitudeAccuracy", null);
                String heading = RequestUtil.getString(request, "heading", null);
                String speed = RequestUtil.getString(request, "speed", null);
                String timestamp = RequestUtil.getString(request, "timestamp", null);
                String memo = RequestUtil.getString(request, "memo", null);

                GeoLocation geoLocation = new GeoLocation();
                geoLocation.setCid(cid);
                geoLocation.setCcid(ccid);
                geoLocation.setUid(uid);
                geoLocation.setLatitude(latitude);
                geoLocation.setLonggitude(longgitude);
                geoLocation.setAltitude(altitude);
                geoLocation.setAccuracy(accuracy);
                geoLocation.setAltitudeAccuracy(altitudeAccuracy);
                geoLocation.setHeading(heading);
                geoLocation.setSpeed(speed);
                geoLocation.setTimestamp(timestamp);
                geoLocation.setCreatetime(new Date());
                if (!Strings.isNullOrEmpty(memo)) {
                    geoLocation.setSource(3);
                    geoLocation.setMemo(memo);
                } else {
                    geoLocation.setSource(1); //签到上报
                }
                this.geoLocationService.saveGeoLocation(geoLocation);
            }
        } catch (ParameterException pe) {
            pe.printStackTrace();
        } catch (SessionException se) {
            responseMessage.setStatus(306);
            responseMessage.setMessage("login fail");
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("XDomainRequestAllowed", "1");
        ResponseUtil.writeResult(response, ResponseUtil.CallBackResultJsonP(JsonParser.simpleJson(responseMessage), callback), encode);
    }


    @RequestMapping("track.action")
    public void track(HttpServletRequest request, HttpServletResponse response) {
        ResponseMessage<List<GeoLocation>> responseMessage = new ResponseMessage<>(200, "success");
        String callback = RequestUtil.getString(request, "callback", CONST.CALLBACK_VAR);
        String encode = RequestUtil.getString(request, "encode", CONST.ENCODE);
        try {
            long uid = getLoginUser(request).getId();
            List<GeoLocation> geoLocations = this.geoLocationService.findGeoLocationByUid(uid);
            if (geoLocations != null) {
                responseMessage.setData(geoLocations);
            }
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
}
