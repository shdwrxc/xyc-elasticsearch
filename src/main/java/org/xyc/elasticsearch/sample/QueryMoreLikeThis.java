package org.xyc.elasticsearch.sample;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.xyc.elasticsearch.api.ClientInstance;

/**
 * Created by CCC on 2016/6/17.
 *
 * 使用More like this实现基于内容的推荐
 elasticsearch MoreLikeThisQueryBuilder查询主要实现对一句话相似文章的查询。
 在推荐系统中，通常要用到与一篇文章相似度高的一组文章。
 */
public class QueryMoreLikeThis extends QueryCommon {

    public static void search() {
        SearchResponse response = ClientInstance.getClient()
                .prepareSearch(indexes)
                .setTypes(types)
                .setQuery(QueryBuilders.moreLikeThisQuery("content").addLikeText("莱斯特城成功拿到英超冠军"))
                .setFrom(0)
                .setSize(10)
                .execute()
                .actionGet();

        System.out.println(response.getHits().totalHits());
    }

    public static void main(String[] args) {
        search();
    }
}
