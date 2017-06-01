package org.xyc.elasticsearch.sample;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.xyc.elasticsearch.api.ClientInstance;

/**
 * Created by CCC on 2016/6/17.
 * 这个query主要是lucne的原始语法，AND OR NOT之类的语法
 * "query": "(content:this OR name:this) AND (content:that OR name:that)"
 * 对于 not_analysis 的字段，他与 wildcard 是一样的
 */
public class QueryStringQuery extends QueryCommon {

    public static void search() {
        SearchResponse response = ClientInstance.getClient()
                .prepareSearch(indexes)
                .setTypes(types)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.queryStringQuery("name:*小明*"))
                .setQuery(QueryBuilders.queryStringQuery("name:小明"))
                .execute()
                .actionGet();

        System.out.println(response.getHits().totalHits());
    }

    public static void main(String[] args) {
        search();
    }
}
