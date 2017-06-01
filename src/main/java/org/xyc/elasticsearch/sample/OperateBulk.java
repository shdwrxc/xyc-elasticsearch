package org.xyc.elasticsearch.sample;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.xyc.elasticsearch.api.ClientInstance;

/**
 * Created by CCC on 2016/6/22.
 */
public class OperateBulk {

    public static void bulk() {
        BulkRequestBuilder bulkRequest = ClientInstance.getClient().prepareBulk();

        String json = "{\"hello\":\"今天周三\",\"odate\":\"2016-08-19\",\"whythis\":\"large large blank\"}";

        // either use client#prepare, or use Requests# to directly build index/delete requests
        bulkRequest.add(ClientInstance.getClient().prepareIndex("twitter", "tweet", "1")
                        .setSource(json));

        bulkRequest.add(ClientInstance.getClient().prepareIndex("twitter", "tweet", "2")
                        .setSource(json));

        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
        }
    }

    public static void main(String[] args) {
        bulk();
    }
}
