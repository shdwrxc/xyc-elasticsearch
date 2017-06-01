package org.xyc.elasticsearch.api.operation;

/**
 * Created by CCC on 2016/6/15.
 */
public class BaseCondition {

    protected int from = 0;
    protected int size = 10;

    public int getFrom() {
        return from;
    }

    public BaseCondition setFrom(int from) {
        this.from = from;
        return this;
    }

    public int getSize() {
        return size;
    }

    public BaseCondition setSize(int size) {
        this.size = size;
        return this;
    }
}
