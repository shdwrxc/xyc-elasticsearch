package org.xyc.elasticsearch.api.operation;

import java.util.ArrayList;
import java.util.List;

public class DeleteMessage {

    private List<String> failures = new ArrayList<String>();

    public static DeleteMessage createMessages() {
        return new DeleteMessage();
    }

    public void addFailure(String id) {
        failures.add(id);
    }

    public boolean hasFailures() {
        return !failures.isEmpty();
    }

    public List<String> getFailures() {
        return failures;
    }
}
