/**
 * Copyright (c) 2012 Sohu TV. All right reserved.
 */
package com.sneaker.mall.api.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

import java.util.Date;
import java.util.List;

/**
 * Json工具类
 *
 * @author jeffreywu 2013-5-27 下午3:42:20
 */
public class JsonParser {
    private static SerializeConfig mapping = new SerializeConfig();

    static {
        mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 最简单序列化为Json数据
     *
     * @param ojbObject
     * @return
     */
    public static String simpleJson(Object ojbObject) {
        return JSON.toJSONString(ojbObject, mapping, SerializerFeature.WriteMapNullValue);
    }


    /**
     * 返序列化对象
     *
     * @param content
     * @return
     */
    public static Object StringToJsonVideo(String content, Class classZ) {
        return JSON.parseObject(content, classZ);
    }


    /**
     * 返序列化数组
     *
     * @param text
     * @param tClass
     * @param <T>
     * @return
     */
    public static final <T> List<T> parseArray(String text, Class<T> tClass) {
        return JSON.parseArray(text, tClass);
    }

    public static Object parser(String content, TypeReference typeReference) {
        return JSON.parseObject(content, typeReference);
    }
}
