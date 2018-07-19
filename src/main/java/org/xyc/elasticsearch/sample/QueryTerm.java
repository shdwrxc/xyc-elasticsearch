package org.xyc.elasticsearch.sample;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.xyc.elasticsearch.api.ClientInstance;

/**
 * Created by CCC on 2016/6/17.
 */
public class QueryTerm extends QueryCommon {

    public static void search() {
        SearchResponse response = ClientInstance.getClient()
                .prepareSearch(indexes)
                .setTypes(types)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                //内容：上海中环保时捷狂飙 英菲尼迪时速180公里跟拍，分词：standard（每个字一个term，另外保存一份原始文本）
                // 搜索“中华”是搜不到的，因为term查询必须要完全匹配，
                //上面的那句话是standard分词的，分出来每个字一个term，所以没有警察这个term，找不到
                //如果是送搜索“中”是可以搜索到的，因为分词出来有“中”这个字
                //查看分词情况可以这么看
                //http://192.168.200.9:9200/test/_analyze?analyzer=standard&pretty&text=上海中环保时捷狂飙 英菲尼迪时速180公里跟拍
                .setQuery(QueryBuilders.termQuery("remark", "中"))                //会按照分词处理，给出的字符串必须在搜索的文档中完全匹配才行
//                .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();

        System.out.println(response.getHits().totalHits());
    }

    public static void main(String[] args) {
        search();
    }
}
