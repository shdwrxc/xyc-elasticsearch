package org.xyc.elasticsearch.sample;

import java.util.Map;

import com.google.common.collect.Maps;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptService;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.xyc.elasticsearch.api.ClientInstance;

public class QueryFunctionScore extends QueryCommon {

    /**
     * 要开启这个功能，要先在配置文件中添加如下参数
     *  script.inline: true
     *  script.indexed: true
     *  SortBuilders.scriptSort用这个方法加到sort里更方便
     *  functionScoreQuery，要单独的建立query才有效好像，不太方便
     *
     *
     //that the doc[...] notation only allows for simple valued fields (can’t return a json object from it) and makes sense only on non-analyzed or single term based fields.
     //好像doc这个只能取出不分词和简单term的值，有分词的不能取出来。
     */
    public static void search() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("thisAge", 30);
        String scriptString = "age = doc['age'].value;if (age==thisAge) return 100; else return 1;";
        Script script = new Script(scriptString, ScriptService.ScriptType.INLINE, "groovy", params);

        SearchResponse response = ClientInstance.getClient().prepareSearch(indexes).setTypes(types).setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
//                .setQuery(QueryBuilders.functionScoreQuery(ScoreFunctionBuilders.scriptFunction(script)))
                .addSort(SortBuilders.fieldSort("firstDate").order(SortOrder.DESC))
                .addSort(SortBuilders.scriptSort(script, "number").order(SortOrder.DESC))
                .setFrom(0).setSize(60)
                .execute()
                .actionGet();

        System.out.println(response.getHits().totalHits());
    }

    public static void main(String[] args) {
        search();
    }
}
