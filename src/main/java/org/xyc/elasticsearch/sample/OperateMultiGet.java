package org.xyc.elasticsearch.sample;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.xyc.elasticsearch.api.ClientInstance;

/**
 * Created by CCC on 2016/6/22.
 */
public class OperateMultiGet {

    public static void get() {
        MultiGetResponse multiGetItemResponses = ClientInstance.getClient().prepareMultiGet()
                .add("twitter", "tweet", "1")
                .add("twitter", "tweet", "2", "3", "4")
                .add("another", "type", "foo")
                .get();

        for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
            GetResponse response = itemResponse.getResponse();
            if (response.isExists()) {
                String json = response.getSourceAsString();
            }
        }
    }

    public static void main(String[] args) {
        get();
    }
}
