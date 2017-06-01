package org.xyc.elasticsearch.api;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;

public class OriginSearchTest {

    @Test
    public void search() {
        SearchResponse response = ClientInstance.getClient()
                .prepareSearch("sample")
                .setTypes("fulltext")
//                .setQuery(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("hello", "第一届")).must(QueryBuilders.matchQuery("hello", "不知道")))
//                .setQuery(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("hello", "不知道")).must(QueryBuilders.matchQuery("hello", "第一届")))
                .setPostFilter(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("hello", "不知道")).must(QueryBuilders.matchQuery("hello", "第一届")))
//                .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("hello", "第一届")).should(QueryBuilders.matchQuery("hello", "不知道")))
//                .setPostFilter(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("hello", "第一届")).should(QueryBuilders.matchQuery("whythis", "larage")))
                .execute()
                .actionGet();
        System.out.println();
    }

    @Test
    public void search1() {
        long l = System.currentTimeMillis();
        SearchResponse response = ClientInstance.getClient()
                .prepareSearch("book")
                .setTypes("yuwen")
//                .setPostFilter(QueryBuilders.wildcardQuery("content", "*国"))    //不写*不行的
                .execute()
                .actionGet();
        System.out.println(System.currentTimeMillis() - l);
        System.out.println();
    }
}
