package org.xyc.elasticsearch.api.operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.elasticsearch.common.Nullable;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;

/**
 * Created by CCC on 2016/6/15.
 */
public class SimpleQueryBuilder {

    public static final String WILDCARD_ALL = "*";

    private final List<SimpleQueryBuilder> mustClauses = new ArrayList<SimpleQueryBuilder>();
    private final List<SimpleQueryBuilder> shouldClauses = new ArrayList<SimpleQueryBuilder>();
    private final List<SimpleQueryBuilder> mustNotClauses = new ArrayList<SimpleQueryBuilder>();

    private final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

    private SimpleQueryBuilder() {

    }

    public static SimpleQueryBuilder simpleQuery() {
        return new SimpleQueryBuilder();
    }

    public SimpleQueryBuilder andChild(SimpleQueryBuilder child) {
        mustClauses.add(child);
        return this;
    }

    public SimpleQueryBuilder orChild(SimpleQueryBuilder child) {
        shouldClauses.add(child);
        return this;
    }

    public SimpleQueryBuilder notChild(SimpleQueryBuilder child) {
        mustNotClauses.add(child);
        return this;
    }

    public SimpleQueryBuilder queryAll() {
        queryBuilder.must(QueryBuilders.matchAllQuery());
        return this;
    }

    public SimpleQueryBuilder andString(String name, String value) {
        queryBuilder.must(QueryBuilders.matchQuery(name, value));
        return this;
    }

    public SimpleQueryBuilder andWholeString(String name, String value) {
        queryBuilder.must(QueryBuilders.matchPhraseQuery(name, value));
        return this;
    }

    public SimpleQueryBuilder orString(String name, String value) {
        queryBuilder.should(QueryBuilders.matchQuery(name, value));
        return this;
    }

    public SimpleQueryBuilder orWholeString(String name, String value) {
        queryBuilder.should(QueryBuilders.matchPhraseQuery(name, value));
        return this;
    }

    public SimpleQueryBuilder notString(String name, String value) {
        queryBuilder.mustNot(QueryBuilders.matchQuery(name, value));
        return this;
    }

    public SimpleQueryBuilder notWholeString(String name, String value) {
        queryBuilder.mustNot(QueryBuilders.matchPhraseQuery(name, value));
        return this;
    }

    public SimpleQueryBuilder andMoreThan(String name, double i) {
        queryBuilder.must(QueryBuilders.rangeQuery(name).gt(i));
        return this;
    }

    public SimpleQueryBuilder andMoreThanEquals(String name, double i) {
        queryBuilder.must(QueryBuilders.rangeQuery(name).gte(i));
        return this;
    }

    public SimpleQueryBuilder andLessThan(String name, double i) {
        queryBuilder.must(QueryBuilders.rangeQuery(name).lt(i));
        return this;
    }

    public SimpleQueryBuilder andLessThanEquals(String name, double i) {
        queryBuilder.must(QueryBuilders.rangeQuery(name).lte(i));
        return this;
    }

    public SimpleQueryBuilder andRange(String name, double start, double end) {
        queryBuilder.must(QueryBuilders.rangeQuery(name).from(start).to(end));
        return this;
    }

    public SimpleQueryBuilder andEquals(String name, String value) {
        queryBuilder.must(QueryBuilders.termQuery(name, value));
        return this;
    }

    public SimpleQueryBuilder andEquals(String name, int i) {
        queryBuilder.must(QueryBuilders.termQuery(name, i));
        return this;
    }

    public SimpleQueryBuilder orMoreThan(String name, double i) {
        queryBuilder.should(QueryBuilders.rangeQuery(name).gt(i));
        return this;
    }

    public SimpleQueryBuilder orMoreThanEquals(String name, double i) {
        queryBuilder.should(QueryBuilders.rangeQuery(name).gte(i));
        return this;
    }

    public SimpleQueryBuilder orLessThan(String name, double i) {
        queryBuilder.should(QueryBuilders.rangeQuery(name).lt(i));
        return this;
    }

    public SimpleQueryBuilder orLessThanEquals(String name, double i) {
        queryBuilder.should(QueryBuilders.rangeQuery(name).lte(i));
        return this;
    }

    public SimpleQueryBuilder orRange(String name, double start, double end) {
        queryBuilder.should(QueryBuilders.rangeQuery(name).from(start).to(end));
        return this;
    }

    public SimpleQueryBuilder orEquals(String name, String value) {
        queryBuilder.should(QueryBuilders.termQuery(name, value));
        return this;
    }

    public SimpleQueryBuilder orEquals(String name, int i) {
        queryBuilder.should(QueryBuilders.termQuery(name, i));
        return this;
    }

    public SimpleQueryBuilder notMoreThan(String name, double i) {
        queryBuilder.mustNot(QueryBuilders.rangeQuery(name).gt(i));
        return this;
    }

    public SimpleQueryBuilder notMoreThanEquals(String name, double i) {
        queryBuilder.mustNot(QueryBuilders.rangeQuery(name).gte(i));
        return this;
    }

    public SimpleQueryBuilder notLessThan(String name, double i) {
        queryBuilder.mustNot(QueryBuilders.rangeQuery(name).lt(i));
        return this;
    }

    public SimpleQueryBuilder notLessThanEquals(String name, double i) {
        queryBuilder.mustNot(QueryBuilders.rangeQuery(name).lte(i));
        return this;
    }

    public SimpleQueryBuilder notRange(String name, double start, double end) {
        queryBuilder.mustNot(QueryBuilders.rangeQuery(name).from(start).to(end));
        return this;
    }

    public SimpleQueryBuilder notEquals(String name, String value) {
        queryBuilder.mustNot(QueryBuilders.termQuery(name, value));
        return this;
    }

    public SimpleQueryBuilder notEquals(String name, int i) {
        queryBuilder.mustNot(QueryBuilders.termQuery(name, i));
        return this;
    }

    public SimpleQueryBuilder andRangeDate(String name, @Nullable Date start, @Nullable Date end) {
        if (start == null && end == null)
            return this;
        queryBuilder.must(buildDate(name, start, end));
        return this;
    }

    private RangeQueryBuilder buildDate(String name, Date start, Date end) {
        RangeQueryBuilder builder = QueryBuilders.rangeQuery(name);
        if (start != null)
            builder.from(start);
        if (end != null)
            builder.to(end);
        return builder;
    }

    public SimpleQueryBuilder orRangeDate(String name, @Nullable Date start, @Nullable Date end) {
        if (start == null && end == null)
            return this;
        queryBuilder.should(buildDate(name, start, end));
        return this;
    }

    public SimpleQueryBuilder notRangeDate(String name, @Nullable Date start, @Nullable Date end) {
        if (start == null && end == null)
            return this;
        queryBuilder.mustNot(buildDate(name, start, end));
        return this;
    }

    public SimpleQueryBuilder andIn(String name, long... values) {
        queryBuilder.must(QueryBuilders.termsQuery(name, values));
        return this;
    }

    public SimpleQueryBuilder orIn(String name, long... values) {
        queryBuilder.should(QueryBuilders.termsQuery(name, values));
        return this;
    }

    public SimpleQueryBuilder notIn(String name, long... values) {
        queryBuilder.mustNot(QueryBuilders.termsQuery(name, values));
        return this;
    }

    public SimpleQueryBuilder andIn(String name, Collection<?> values) {
        if (isEmpty(values))
            return this;
        queryBuilder.must(QueryBuilders.termsQuery(name, values));
        return this;
    }

    public SimpleQueryBuilder orIn(String name, Collection<?> values) {
        if (isEmpty(values))
            return this;
        queryBuilder.should(QueryBuilders.termsQuery(name, values));
        return this;
    }

    public SimpleQueryBuilder notIn(String name, Collection<?> values) {
        if (isEmpty(values))
            return this;
        queryBuilder.mustNot(QueryBuilders.termsQuery(name, values));
        return this;
    }

    public SimpleQueryBuilder andLike(String name, String value) {
        if (isEmpty(value))
            return this;
        queryBuilder.must(QueryBuilders.wildcardQuery(name, WILDCARD_ALL + value.toLowerCase() + WILDCARD_ALL));
        return this;
    }

    public SimpleQueryBuilder orLike(String name, String value) {
        if (isEmpty(value))
            return this;
        queryBuilder.should(QueryBuilders.wildcardQuery(name, WILDCARD_ALL + value.toLowerCase() + WILDCARD_ALL));
        return this;
    }

    public SimpleQueryBuilder notLike(String name, String value) {
        if (isEmpty(value))
            return this;
        queryBuilder.mustNot(QueryBuilders.wildcardQuery(name, WILDCARD_ALL + value.toLowerCase() + WILDCARD_ALL));
        return this;
    }

    private boolean isEmpty(Collection<?> values) {
        return values == null || values.isEmpty();
    }

    private boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public QueryBuilder build() {
        if (mustClauses.size() > 0) {
            for (SimpleQueryBuilder simpleQuery : mustClauses) {
                queryBuilder.must(simpleQuery.build());
            }
        }
        if (shouldClauses.size() > 0) {
            for (SimpleQueryBuilder simpleQuery : shouldClauses) {
                queryBuilder.should(simpleQuery.build());
            }
        }
        if (mustNotClauses.size() > 0) {
            for (SimpleQueryBuilder simpleQuery : mustNotClauses) {
                queryBuilder.mustNot(simpleQuery.build());
            }
        }
        return queryBuilder;
    }
}
