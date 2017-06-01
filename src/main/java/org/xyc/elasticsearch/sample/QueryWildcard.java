package org.xyc.elasticsearch.sample;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.xyc.elasticsearch.api.ClientInstance;

/**
 * Created by CCC on 2016/6/17.
 *
 * Supported wildcards are <tt>*</tt>, which
 * matches any character sequence (including the empty one), and <tt>?</tt>,
 * which matches any single character. Note this query can be slow, as it
 * needs to iterate over many terms. In order to prevent extremely slow WildcardQueries,
 * a Wildcard term should not start with one of the wildcards <tt>*</tt> or
 * <tt>?</tt>.
 */
public class QueryWildcard extends QueryCommon {

    /**
     * 2个字段，内容都为PICC0525-001
     * text1的mapping:"type": "string"，会进行分词，变成picc0525（注意下，es会把大写字母转为小写），和001
     * text2的mapping:"type": "string","index": "not_analyzed"，不会分词，就是一个term，PICC0525-001
     *
     * 搜索“picc0525”，text1中，因为分词后，全文匹配了，text2不中，没有分词，不能完全匹配，另外大小写不一样
     * 搜搜“picc0525*”，text1中，text2不中，因为大小写不一样，
     * 搜索“PICC0525*”，text1不中，因为大小写问题，text2中，因为就是这个样子的。
     * 搜索“PICC0525”，text1不中，大小写问题，text2不中，因为没分词，不能完全匹配，需要加上*才行
     * 搜索“001”，text1中，因为分词后命中了，text2不中，不能完全匹配
     * 搜索“*001”，text1中，text2中，因为符合条件
     * 搜索“01”，text1不中，计算分词了，还是不匹配啊，text2不中
     * 搜搜“*01”，text1中，匹配到了分词后的“001”，text2也中。
     *
     * 总结下，text2要么完全匹配输入的，要么要加*和？等通配符来匹配
     * text1，分词后的每个term要么完全匹配，要么加通配符。
     * text1分词后得到的term，每个term就是一个text2
     *
     * "analysis": {
     "analyzer": {
     "keyword_lowercase": {
     "type": "custom",
     "filter": "lowercase",
     "tokenizer": "keyword"
     }
     }
     keyword_lowercase这个analyzer和index;not_analyzed区别就是前者字母转小写，然后整个作为一个分词，后者不分词，效果上，和整个作为一个分词差不多
     * 说下analyzer和search_analyzer的区别
     * 默认analyzer会赋值给index_analyzer和search_analyzer
     * 如果设置analyzer:keyword_lowercase，又设置了search_analyzer为standard
     * 插入数据为：AbC-HdK-123
     * 因为没有设定index_analyzer，那边建立索引按照analyzer，插入数据为abc-hdk-123。
     * 搜索“abc-hdk-123”可以搜到（为什么。。搜索应该是standard的，理论上，搜索的字符串被分词了，分成了[abc,hdk,123]，分别去匹配，应该匹配不了"abc-hdk-123"这个整句的）
     * 搜搜“AbC-HdK-123”是搜不到的。
     * 搜索“abc*”可以搜到，貌似search_analyzer没啥作用。
     * 去除search_analyzer后，同样可以搜到“abc-hdk-123”，搜不到“AbC-HdK-123”，也可以搜到“a*c-hdk-123”
     *
     * 难道是因为是wildcard，不会对输入去进行分词吗？可能吧，换成QueryBuilders.matchQuery("field2", "abc-hdk-123")，的确是搜不到的。
     *
     */
    public static void search() {
        SearchResponse response = ClientInstance.getClient()
                .prepareSearch(indexes)
                .setTypes(types)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.wildcardQuery("field2", "a*c-hdk-123"))
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();

        System.out.println(response.getHits().totalHits());
    }

    public static void main(String[] args) {
        search();
    }
}
