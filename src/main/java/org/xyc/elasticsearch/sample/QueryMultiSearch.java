package org.xyc.elasticsearch.sample;

import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.xyc.elasticsearch.api.ClientInstance;

/**
 * Created by CCC on 2016/6/10.
 */
public class QueryMultiSearch extends QueryCommon {

    public static void search() {

        SearchRequestBuilder srb1 =  ClientInstance.getClient()
                .prepareSearch(indexes)
                .setTypes(types)
                .setQuery(QueryBuilders.queryStringQuery("name:小明")).setSize(1);
        SearchRequestBuilder srb2 = ClientInstance.getClient()
                .prepareSearch(indexes)
                .setTypes(types)
                .setQuery(QueryBuilders.matchQuery("name", "小刚")).setSize(1);

        MultiSearchResponse sr = ClientInstance.getClient().prepareMultiSearch()
                .add(srb1)
                .add(srb2)
                .execute().actionGet();

        // You will get all individual responses from MultiSearchResponse#getResponses()
        long nbHits = 0;
        for (MultiSearchResponse.Item item : sr.getResponses()) {
            SearchResponse response = item.getResponse();
            nbHits += response.getHits().getTotalHits();
        }
    }

    public static void main(String[] args) {
        search();
    }
}
