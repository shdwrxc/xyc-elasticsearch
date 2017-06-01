package org.xyc.elasticsearch.api.operation;

import org.elasticsearch.index.query.QueryBuilder;

/**
 * 查询部分分为query和filter
 * filter是为了过滤掉无关的数据，比如数字的比较，日期的比较都可以作为filter
 * query是查询记录的相关度
 */
public class SearchCondition extends BaseCondition {

    private QueryBuilder queryBuilder;
    private QueryBuilder filterBuilder;
    private SimpleSortBuilder simpleSortBuilder;

    private SearchCondition() {

    }

    public static SearchCondition searchCondition() {
        return new SearchCondition();
    }

    public QueryBuilder getQueryBuilder() {
        return queryBuilder;
    }

    public SearchCondition setQueryBuilder(QueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
        return this;
    }

    public QueryBuilder getFilterBuilder() {
        return filterBuilder;
    }

    public SearchCondition setFilterBuilder(QueryBuilder filterBuilder) {
        this.filterBuilder = filterBuilder;
        return this;
    }

    public SimpleSortBuilder getSimpleSortBuilder() {
        return simpleSortBuilder;
    }

    public SearchCondition setSimpleSortBuilder(SimpleSortBuilder simpleSortBuilder) {
        this.simpleSortBuilder = simpleSortBuilder;
        return this;
    }

    @Override
    public SearchCondition setFrom(int from) {
        super.setFrom(from);
        return this;
    }

    @Override
    public SearchCondition setSize(int size) {
        super.setSize(size);
        return this;
    }
}
