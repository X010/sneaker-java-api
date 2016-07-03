package com.sneaker.mall.api.util;

/**
 * Created by liyi on 16/4/11.
 *
 * 转换固定的类型,数字id转换为字符串
 *
 */
public class TypeUtil {

    /**
     * 转换类型 从数字到字符串
     * @param type
     * @return
     */
    public static String getTranslate(int type) {

        String output = "";

        if (type == 1) {
            output = "经销商";
        } else if (type == 2) {
            output = "酒店饭店";
        } else if (type == 3) {
            output = "商场超市";
        } else if (type == 4) {
            output = "便利店";
        } else {
            output = "经销商";
        }

        return output;
    }
}
