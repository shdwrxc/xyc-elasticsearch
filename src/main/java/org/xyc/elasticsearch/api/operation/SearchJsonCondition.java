package org.xyc.elasticsearch.api.operation;

/**
 * Created by CCC on 2016/6/15.
 */
public class SearchJsonCondition extends BaseCondition {

    private String jsonString;

    private SearchJsonCondition() {

    }

    public static SearchJsonCondition searchJsonCondition() {
        return new SearchJsonCondition();
    }

    public String getJsonString() {
        return jsonString;
    }

    public SearchJsonCondition setJsonString(String jsonString) {
        this.jsonString = jsonString;
        return this;
    }

}
