package org.xyc.elasticsearch.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by CCC on 2016/6/15.
 */
public class QueryResult<T> {

    private List<T> list = new ArrayList<T>();
    private long count;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void addList(Collection<T> elements) {
        list.addAll(elements);
    }

    public void addObj(T t) {
        list.add(t);
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
