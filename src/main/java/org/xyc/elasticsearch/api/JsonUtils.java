package org.xyc.elasticsearch.api;

import com.alibaba.fastjson.JSON;

public class JsonUtils {

    public static <T> String toString(T obj) {
        return JSON.toJSONString(obj);
    }

    public static <T> T parseObject(String str, Class<T> clazz) {
        return JSON.parseObject(str, clazz);
    }
}
