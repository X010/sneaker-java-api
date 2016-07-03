package com.sneaker.mall.api.util;

import com.google.common.base.Strings;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Copyright (c) 2014, Sohu.com All Rights Reserved.
 * <p/>
 * User: jeffreywu  MailTo:jeffreywu@sohu-inc.com
 * Date: 14-7-30
 * Time: AM9:52
 */
public class ResponseUtil {
    public static void writeResult(HttpServletResponse response, String result, String encoder) {
        try {
            if (encoder == null)
                encoder = "UTF-8";
            response.setCharacterEncoding(encoder);
            response.setContentType("textml;charset=" + encoder);
            //response.addHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Accept-Charset", "utf-8");
            ServletOutputStream out = response.getOutputStream();
            byte[] b = result.getBytes(encoder);
            out.write(b);
        } catch (IOException e) {
        }
    }

    /**
     * callback数据处理
     *
     * @param result
     * @param callback
     * @return
     */
    public static String CallBackResult(String result, String callback) {
        if (Strings.isNullOrEmpty(callback))
            callback = "a";
        result = String.format("var %s=%s", callback, result);
        return result;
    }

    /**
     * callback数据处理
     *
     * @param result
     * @param callback
     * @return
     */
    public static String CallBackResultJsonP(String result, String callback) {
        if (Strings.isNullOrEmpty(callback))
            callback = "a";
        result = String.format("%s(%s)", callback, result);
        return result;
    }
}
