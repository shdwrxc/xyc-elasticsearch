package org.xyc.elasticsearch.api.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Iterators;

public class AddMessage {

    private List<FailureMessage> failures = new ArrayList<FailureMessage>();

    public static AddMessage createMessages() {
        return new AddMessage();
    }

    public void addFailure(FailureMessage failure) {
        failures.add(failure);
    }

    public boolean hasFailures() {
        return !failures.isEmpty();
    }

    public List<FailureMessage> getFailures() {
        return failures;
    }

    public Iterator<FailureMessage> iterator() {
        return Iterators.forArray(failures.toArray(new FailureMessage[failures.size()]));
    }
}
