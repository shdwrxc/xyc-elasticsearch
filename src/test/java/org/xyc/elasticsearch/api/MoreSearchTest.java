package org.xyc.elasticsearch.api;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xyc.elasticsearch.api.operation.CrudOperation;
import org.xyc.elasticsearch.api.operation.SearchCondition;
import org.xyc.elasticsearch.api.operation.SearchOperation;
import org.xyc.elasticsearch.api.operation.SimpleQueryBuilder;
import org.xyc.elasticsearch.api.operation.SimpleSortBuilder;

/**
 * Created by CCC on 2016/6/16.
 */
public class MoreSearchTest {

    CrudOperation crudOperation;
    SearchOperation searchOperation;

    @Before
    public void before() {
        crudOperation = new CrudOperation("book", "yuwen");
//        searchOperation = new SearchOperation(new String[]{"book", "devclaim"}, new String[]{"shuxue", "devclaimtask"});
        searchOperation = new SearchOperation(new String[]{"book"}, new String[]{"shuxue"});
    }

    @Test
    public void addData() {
        //first
        Calendar calendar = Calendar.getInstance();
        String json = "{\"name\":\"小明\",\"firstDate\":\"" + DateFormatUtils.formatDefault(calendar.getTime()) + "\",\"secondDate\":\"" + calendar.getTimeInMillis() + "\",\"content\":\"01\",\"contentNot\":\"美国留给伊拉克的是个烂摊子吗\",\"message\":\"find blue sky\",\"age\":15}";
        crudOperation.add("1", json);
        //second
        calendar.add(Calendar.DAY_OF_YEAR, -10);
        json = "{\"name\":\"小明他哥\",\"firstDate\":\"" + DateFormatUtils.formatDefault(calendar.getTime()) + "\",\"secondDate\":\"" + calendar.getTimeInMillis() + "\",\"content\":\"04\",\"contentNot\":\"公安部：各地校车将享最高路权\",\"message\":\"today is a sunny day\",\"age\":30}";
        crudOperation.add("2", json);
        //third
        calendar.add(Calendar.MONTH, -3);
        json = "{\"name\":\"小刚\",\"firstDate\":\"" + DateFormatUtils.formatDefault(calendar.getTime()) + "\",\"secondDate\":\"" + calendar.getTimeInMillis() + "\",\"content\":\"05\",\"contentNot\":\"中韩渔警冲突调查：韩警平均每天扣1艘中国渔船\",\"message\":\"today we will travel to china\",\"age\":18}";
        crudOperation.add("3", json);
        //fourth
        calendar.add(Calendar.MONTH, -2);
        json = "{\"name\":\"小明的弟弟\",\"firstDate\":\"" + DateFormatUtils.formatDefault(calendar.getTime()) + "\",\"secondDate\":\"" + calendar.getTimeInMillis() + "\",\"content\":\"07\",\"contentNot\":\"中国驻洛杉矶领事馆遭亚裔男子枪击 嫌犯已自首\",\"message\":\"i am very happy in holiday\",\"age\":40}";
        crudOperation.add("4", json);
        //fifth
        calendar.add(Calendar.MONTH, 8);
        json = "{\"name\":\"小刚的同事\",\"firstDate\":\"" + DateFormatUtils.formatDefault(calendar.getTime()) + "\",\"secondDate\":\"" + calendar.getTimeInMillis() + "\",\"content\":\"15\",\"contentNot\":\"莱斯特城成功的拿到了英超冠军\",\"message\":\"travel to england is a happy idea\",\"age\":55}";
        crudOperation.add("5", json);
        System.out.println();
    }

    @Test
    public void delete() {
        crudOperation.delete("1", "2", "3", "4", "5");
        System.out.println();
    }

    @Test
    public void search() {
        //年龄在25-35
        SimpleQueryBuilder innerQueryBuilder1 = SimpleQueryBuilder.simpleQuery().andRange("age", 25, 35);
        //message包含very happy
        SimpleQueryBuilder innerQueryBuilder2 = SimpleQueryBuilder.simpleQuery().andString("message", "very happy");
        //把上面的两个条件或操作
        SimpleQueryBuilder innerQueryBuilder3 = SimpleQueryBuilder.simpleQuery().andChild(innerQueryBuilder1).orChild(innerQueryBuilder2);
        //把上面的复合条件做与操作
        SimpleQueryBuilder queryBuilder = SimpleQueryBuilder.simpleQuery().andString("name", "小明他哥").orChild(innerQueryBuilder3);
        SimpleSortBuilder sortBuilder = SimpleSortBuilder.simpleSort().sortAsc("age").sortDesc("name");
        SearchCondition condition = SearchCondition.searchCondition().setQueryBuilder(queryBuilder.build()).setSimpleSortBuilder(sortBuilder).setFrom(0).setSize(10);
        QueryResult<MoreResult> queryResult = searchOperation.search(condition, MoreResult.class);
        Assert.assertNotNull(queryResult);
    }

    @Test
    public void search1() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -30);
        Date start = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 60);
        Date end = calendar.getTime();
        //年龄在25-35
//        SimpleQueryBuilder innerQueryBuilder1 = SimpleQueryBuilder.simpleQuery().andRangeDate("firstDate", start, end);
//        SimpleQueryBuilder innerQueryBuilder1 = SimpleQueryBuilder.simpleQuery().andMoreThan("age", 30);
        SimpleQueryBuilder innerQueryBuilder1 = SimpleQueryBuilder.simpleQuery().andString("contentNot", "中国发财");
//        SimpleQueryBuilder innerQueryBuilder1 = SimpleQueryBuilder.simpleQuery().andString("message", "very happy");
        SimpleSortBuilder sortBuilder = SimpleSortBuilder.simpleSort().sortAsc("age").sortDesc("name");
        SearchCondition condition = SearchCondition.searchCondition().setQueryBuilder(innerQueryBuilder1.build()).setSimpleSortBuilder(sortBuilder).setFrom(0).setSize(10);
        QueryResult<MoreResult> queryResult = searchOperation.search(condition, MoreResult.class);
        Assert.assertNotNull(queryResult);
    }

    @Test
    public void search2() {
        SearchCondition condition = SearchCondition.searchCondition()
                .setQueryBuilder(SimpleQueryBuilder.simpleQuery().andString("hello", "很热").build());
        QueryResult queryResult1 = searchOperation.search(condition);
        QueryResult<MoreResult> queryResult = searchOperation.search(condition, MoreResult.class);
        System.out.println();
    }

    @Test
    public void search3() {
        List<Long> list = Lists.newArrayList(1l,2l);
        List<Long> list1 = null;
        SearchCondition condition = SearchCondition.searchCondition()
                .setQueryBuilder(SimpleQueryBuilder.simpleQuery().andIn("status", list1).build());
        QueryResult<MoreResult> queryResult = searchOperation.search(condition, MoreResult.class);
        System.out.println();
    }

    @Test
    public void search4() {
        SearchCondition condition = SearchCondition.searchCondition()
                .setQueryBuilder(SimpleQueryBuilder.simpleQuery().andWholeString("message", "ea").build());
        QueryResult<MoreResult> queryResult = searchOperation.search(condition, MoreResult.class);
        System.out.println();
    }

    @Test
     public void search5() {
        SearchCondition condition = SearchCondition.searchCondition().setSimpleSortBuilder(SimpleSortBuilder.simpleSort().sortTop("claimStatus", "19", "27", "200").sortTop("estimatorUserId", 3200).sortDesc("estimatorUserId")).setSize(500);
        QueryResult<MoreResult> queryResult = searchOperation.search(condition, MoreResult.class);
        System.out.println();
        for (MoreResult mr : queryResult.getList()) {
            System.out.println(mr.getClaimStatus() + "," + mr.getEstimatorUserId());
        }
    }

    @Test
    public void search6() {
        SearchCondition condition = SearchCondition.searchCondition().setSimpleSortBuilder(SimpleSortBuilder.simpleSort().sortAsc("text2")).setSize(500);
        QueryResult<MoreResult> queryResult = searchOperation.search(condition, MoreResult.class);
        System.out.println();
        for (MoreResult mr : queryResult.getList()) {
            System.out.println(mr.getText1() + "," + mr.getText2() + "," + mr.getText3());
        }
    }
}
