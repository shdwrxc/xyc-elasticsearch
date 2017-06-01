package org.xyc.elasticsearch.sample;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.xyc.elasticsearch.api.ClientInstance;

/**
 * Created by CCC on 2016/6/17.
 *
 *  SpanQuery是按照词在文章中的距离或者查询几个相邻词的查询。打个比方：如“中华人民共和国”    用“中国“做为关键字， 跨度为某个值，如5。跨度代表 中 和国之间的长度。这是比较底层的位置查询，通常用于实现非常具体的法律文件或专利的查询
 */
public class QuerySpan extends QueryCommon {

    public static void search() {
        SearchResponse response = ClientInstance.getClient()
                .prepareSearch(indexes)
                .setTypes(types)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
//                .setQuery(QueryBuilders.spanTermQuery("name", "小明"))
                //查询的几个短语保持一定的距离
                .setQuery(QueryBuilders.spanNearQuery().clause(QueryBuilders.spanTermQuery("name", "小明")).clause(QueryBuilders.spanTermQuery("name","弟弟")).slop(0))
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();

        System.out.println(response.getHits().totalHits());
    }

    public static void main(String[] args) {
        search();
    }
}
