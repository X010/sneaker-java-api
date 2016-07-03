package com.sneaker.mall.api.api.saleman;

import com.google.common.base.Preconditions;
import com.sneaker.mall.api.exception.SessionException;
import com.sneaker.mall.api.model.User;

import javax.servlet.http.HttpServletRequest;

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
public abstract class BaseController {

    protected static final String LOIGIN_SALEMAN_FLAG = "USER_SALEMAN";
    protected static final String ROLE = "GATHERING";

    protected User getLoginUser(HttpServletRequest request) {
        Preconditions.checkNotNull(request);
        Object user = request.getSession().getAttribute(LOIGIN_SALEMAN_FLAG);
        if (user == null) {
            throw new SessionException("session no effect");
        }
        return (User) user;
    }

    protected void setLoginUser(HttpServletRequest request, User user) {
        Preconditions.checkNotNull(request);
        Preconditions.checkNotNull(user);
        request.getSession().setAttribute(LOIGIN_SALEMAN_FLAG, user);
    }

    /**
     * 设置是否是收款专用人员
     *
     * @param request
     * @param user
     * @param gathering
     */
    protected void setGatheringLoginUser(HttpServletRequest request, User user, boolean gathering) {
        Preconditions.checkNotNull(request);
        Preconditions.checkNotNull(user);
        request.getSession().setAttribute(LOIGIN_SALEMAN_FLAG, user);
        if (gathering)
            request.getSession().setAttribute(ROLE, true);
    }

    /**
     * 判断是否是收款人员
     *
     * @param request
     * @return
     */
    protected boolean getGathering(HttpServletRequest request) {
        boolean res = false;
        Object object = request.getSession().getAttribute(ROLE);
        if (object != null) {
            res = Boolean.valueOf(object.toString());
        }
        return res;
    }


    protected void clearLoginUser(HttpServletRequest request) {
        Preconditions.checkNotNull(request);
        try {
            request.getSession().removeAttribute(LOIGIN_SALEMAN_FLAG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
