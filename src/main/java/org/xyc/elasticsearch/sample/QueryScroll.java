package org.xyc.elasticsearch.sample;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.sort.SortParseElement;
import org.xyc.elasticsearch.api.ClientInstance;

/**
 * Created by CCC on 2016/6/17.
 *
 * 用于重导数据等情况
 * 读取批量数据
 */
public class QueryScroll extends QueryCommon {

    public static void search() {
        int i = 0;
        SearchResponse scrollResp = ClientInstance.getClient()
                .prepareSearch(indexes)
                .setTypes(types)
                .addSort(SortParseElement.DOC_FIELD_NAME, SortOrder.ASC)
                .setScroll(new TimeValue(5000))
                .setSize(100).execute().actionGet(); //100 hits per shard will be returned for each scroll
        //Scroll until no hits are returned
        while (true) {
            for (SearchHit hit : scrollResp.getHits().getHits()) {
                System.out.println(i++ + "--" + hit.getSourceAsString());
                //Handle the hit...
            }
            scrollResp = ClientInstance.getClient().prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(5000)).execute().actionGet();
            //Break condition: No hits are returned
            if (scrollResp.getHits().getHits().length == 0) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        search();
    }
}
