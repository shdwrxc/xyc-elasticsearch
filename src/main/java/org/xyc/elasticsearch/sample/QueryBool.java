package org.xyc.elasticsearch.sample;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.xyc.elasticsearch.api.ClientInstance;

/**
 * Created by CCC on 2016/6/17.
 *
 */
public class QueryBool extends QueryCommon {

    public static void search() {
        //bool组合应用在query时，如果已经must存在的情况下，should不会影响到query的值，会影响到query结果的分数
        //bool组合应该在filter时，每个层次的must,should,mustnot都是平级的，也就是说每个条件都是要满足的。
        //这里的should是指内部，其实是指and (a=1 or b=1)。should在这里就是内部有2个条件，a=1和b=1两个表达式
        //如果should内部只有1个表达式，那和must没啥区别
        QueryBuilder queryBuilder1 = QueryBuilders.termQuery("name", "小明");
        QueryBuilder queryBuilder2 = QueryBuilders.termQuery("message", "happy");
        SearchResponse response = ClientInstance.getClient()
                .prepareSearch(indexes)
                .setTypes(types)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.boolQuery().must(queryBuilder1).must(queryBuilder2).minimumShouldMatch("50%")).setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();

        System.out.println(response.getHits().totalHits());
    }

    public static void main(String[] args) {
        search();
    }
}
