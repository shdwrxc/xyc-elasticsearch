package org.xyc.elasticsearch.sample;

import java.util.Date;
import java.util.Map;

import com.google.common.collect.Maps;
import org.xyc.elasticsearch.api.ClientInstance;

/**
 * created by wks on date: 2017/5/25
 */
public class OperateUpdate {

    /**
     * 不能更新，只会覆盖
     */
    public static void update1() {
        Map<String, Object> map = Maps.newHashMap();
        //        map.put("name", "hello");
        //        map.put("age", 50);
        map.put("text1", "hello");
        ClientInstance.getClient().prepareIndex("book", "shuxue", "AVc78gIc6Dq0FJSIsGd0").setSource(map).get();

        System.out.println("");
    }

    /**
     * works
     */
    public static void update2() {
        Map<String, Object> map = Maps.newHashMap();
//        map.put("name", "hello");
//        map.put("age", 50);
//        map.put("firstDate", new Date());
        String str = "{\"text1\":\"change a text\"}";
        ClientInstance.getClient().prepareUpdate("book", "shuxue", "AVc78gIc6Dq0FJSIsGd0").setDoc(str).get();

        System.out.println();
    }

    public static void main(String[] args) {
        update2();
    }
}
