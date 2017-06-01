package org.xyc.elasticsearch.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xyc.elasticsearch.api.operation.AddMessage;
import org.xyc.elasticsearch.api.operation.CrudOperation;

/**
 * Created by CCC on 2016/6/13.
 */
public class CrudOperationTest {

    CrudOperation operation;

    @Before
    public void before() {
        operation = new CrudOperation("book", "shuxue");
    }

    @Test
    public void testAdd() {
        String json = "{\"hello\":\"今天很热\",\"odate\":\"2016-08-19\",\"whythis\":\"large large blank\", \"newDay\":\"apple\"}";
        operation.add(json);
    }

    @Test
    public void testAdd1() {
        String json = "{\"hello\":\"今天周san\",\"odate\":\"2016-08-19\",\"whythis\":\"large large blank\"}";
        String json1 = "{\"hello\":\"今天周si\",\"odate\":\"2016-01-19\",\"whythis\":\"large small blank\"}";
        List<String> list = Lists.newArrayList(json, json1);
        AddMessage messages = operation.add(list);
    }

    @Test
    public void testAdd2() {
        String json1 = "{\"name\":\"今天不知道1\",\"status\":[1,6,8]}";
        String json2 = "{\"name\":\"今天不知道2\",\"status\":[2,18,20]}";
        List<String> list = Lists.newArrayList(json1, json2);
        AddMessage messages = operation.add(list);
    }

    @Test
    public void testAdd3() {
        String json1 = "{\"message\":\"today have a heavy rain\",\"address\":\"shanghai xuhui everywhere\"}";
        String json2 = "{\"message\":\"today is a pay day\",\"address\":\"china and europe\"}";
        List<String> list = Lists.newArrayList(json1, json2);
        AddMessage messages = operation.add(list);
    }

    @Test
    public void testAdd4() {
        String json1 = "{\"text1\":\"today have a heavy rain\",\"text2\":\"today have a heavy rain\"}";
        String json2 = "{\"text1\":\"i have a\",\"text2\":\"i have a\"}";
        String json3 = "{\"text1\":\"12345abc\",\"text2\":\"12345abc\"}";
        String json4 = "{\"text1\":\"今天吃饭\",\"text2\":\"今天吃饭\"}";
        String json5 = "{\"text1\":\"再见\",\"text2\":\"再见\"}";
        String json6 = "{\"text1\":\"12345zzz\",\"text2\":\"12345zzz\"}";
        String json7 = "{\"text1\":\"789 在家\",\"text2\":\"789 在家\"}";
        String json8 = "{\"text1\":\"Zz大写字母\",\"text2\":\"Zz大写字母\"}";
        String json9 = "{\"text1\":\"zZ小写字母\",\"text2\":\"zZ小写字母\"}";
        String json10 = "{\"text3\":\"hunxiaode\"}";
        String json11 = "{\"text1\":\"     \",\"text2\":\"     \"}";
        String json12 = "{\"text1\":\"\",\"text2\":\"\"}";
        String json13 = "{\"text1\":\"   \",\"text2\":\"   \"}";
//        List<String> list = Lists.newArrayList(json1, json2, json3, json4, json5, json6, json7);
        List<String> list = Lists.newArrayList(json13);
        AddMessage messages = operation.add(list);
        Assert.assertNotNull(messages);
    }

    @Test
    public void testBatchAdd() {
        String json = "{" +
                "\"user\":\"abc_{1}\"," +
                "\"postDate\":\"2016-07-{2}\"," +
                "\"message\":\"find {3} sky\"," +
                "\"age\":{4}" +
                "}";
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            String newJson = json.replace("{1}", String.valueOf(random.nextInt(10000)))
                    .replace("{2}", String.valueOf(random.nextInt(31)))
                    .replace("{3}", String.valueOf(random.nextInt(1000)))
                    .replace("{4}", String.valueOf(random.nextInt(60)));

            operation.add(newJson);
        }
    }

    @Test
    public void testBatchAdd1() {
        List<HelloWorld> list = Lists.newArrayList();

        String name = "abc_{1}";
        String message = "find {1} sky";
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            HelloWorld helloWorld = new HelloWorld();
            helloWorld.setName(name.replace("{1}", String.valueOf(random.nextInt(10000))));
            helloWorld.setAge(random.nextInt(100));
            helloWorld.setMessage(message.replace("{1}", String.valueOf(random.nextInt(10000))));
            list.add(helloWorld);
        }

        operation.addObject(list);
    }

    @Test
    public void testGet() {
        operation.get("AVVJATsBdWGxvaSMGJA-");
    }

    @Test
    public void testDelete() {
        operation.delete("AVVJATsBdWGxvaSMGJA-");
    }
}
