package org.xyc.elasticsearch.api.operation;

import java.util.ArrayList;
import java.util.List;

public class GetMessage {

    private List<String> results = new ArrayList<String>();

    public static GetMessage createMessages() {
        return new GetMessage();
    }

    public void addElement(String jsonString) {
        results.add(jsonString);
    }

    public List<String> getJsonStrings() {
        return results;
    }
}
