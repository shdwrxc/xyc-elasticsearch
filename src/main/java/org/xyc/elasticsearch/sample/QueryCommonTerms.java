package org.xyc.elasticsearch.sample;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.xyc.elasticsearch.api.ClientInstance;

/**
 * Created by CCC on 2016/6/17.
 */
public class QueryCommonTerms extends QueryCommon {

    public static void search() {
        /**
         * 常用词查询是在没有使用停用词的情况下， Elasticsearch为了提高常用词的查询相关性和精确性而提供的一个现代解决方案。
         例如，“ crime and punishment”可以翻译成3个词查询，每一个都有性能上的成本（词越多，查询性能越低）。但“ and”这个词非常常见，对文档得分的影响非常低。
         解决办法是常用词查询，将查询分为两组。第一组包含重要的词，出现的频率较低。第二组包含较高频率的、不那么重要的词。先执行第一个查询，Elasticsearch从第一组的所有词中计算分数。这样，通常都很重要的低频词总是被列入考虑范围。然后，Elasticsearch对第二组中的词执行二次查询，但只为与第一个查询中匹配的文档计算得分。这样只计算了相关文档的得分，实现了更高的性能。
         查询语法
         {
         “query” : {
         “common” : {
         “title” : {
         “query” : “crime and punishment”,
         “cutoff_frequency” : 0.001
         }}}
        参数highFreqCccur:为高频词组构建查询时用到的布尔运算符SHOULD/MUST
        参数lowFreqOccur:为低频词组构建查询时用到的布尔运算符SHOULD/MUST
        参数maxTermFrequency:用来构建高、低频词组，小于设定值的词将出现在低频词组中
        参数disableCoords: 是否启用分数因子计算'''

         一种略高级的查询，充分考虑了stop-word的低优先级，提高了查询精确性。
         将terms分为了两种：more-importent（low-frequency） and less important（high-frequency）。less-important比如stop-words，eg：the and。
         分组标准由cutoff_frequence决定。两组query构成bool query。must应用于low_frequence，should应用high_frequence。
         每一组内部都可以指定operator和mini_should_match。
         如果group后只有一组，则默认退化为单组的子查询

         analyzed类型的query，故可指定analyzer
         operator可指定or/and
         zero-terms-query可指定none/all
         cutoff-frequency可指定absolute值或者relative值
         match-phase query可指定slot值，参见后续的search-in-depth
         match-phase-prefix query可指定max_expansion
         */
        SearchResponse response = ClientInstance.getClient().prepareSearch(indexes).setTypes(types).setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.commonTermsQuery("message", "today is a"))
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();

        System.out.println(response.getHits().totalHits());
    }

    public static void main(String[] args) {
        search();
    }
}
