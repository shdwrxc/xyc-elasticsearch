package org.xyc.elasticsearch.api;

import java.util.Calendar;
import java.util.Map;

import com.google.common.collect.Maps;
import org.junit.Test;

public class ParseABTest {

    @Test
    public void compare() {
        Calendar calendar = Calendar.getInstance();
        String json = "{\"name\":\"小明\",\"firstDate\":\"" + DateFormatUtils.formatDefault(calendar.getTime()) + "\",\"secondDate\":\"" + calendar.getTimeInMillis() + "\",\"content\":\"美国留给伊拉克的是个烂摊子吗\",\"contentNot\":\"美国留给伊拉克的是个烂摊子吗\",\"message\":\"find blue sky\",\"age\":15,\"newDay\":\"apple\"}";
        Map<String, Object> map = Maps.newHashMap();
        map.put("name", "小明");
        map.put("firstDate", calendar.getTime());
        map.put("secondDate", calendar.getTimeInMillis());
        map.put("content", "美国留给伊拉克的是个烂摊子吗");
        map.put("contentNot", "美国留给伊拉克的是个烂摊子吗");
        map.put("message", "find blue sky");
        map.put("age", 15);
        map.put("newDay", "apple");

        MoreResult mr1 = JsonUtils.parseObject(json, MoreResult.class);
        MoreResult mr2 = ElasticBeanUtils.copy(MoreResult.class, map);

        int count = 10000;
        long l = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            JsonUtils.parseObject(json, MoreResult.class);
        }
        System.out.println(System.currentTimeMillis() - l);
        l = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            ElasticBeanUtils.copy(MoreResult.class, map);
        }
        System.out.println(System.currentTimeMillis() - l);
    }
}
