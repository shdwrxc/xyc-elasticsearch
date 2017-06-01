package org.xyc.elasticsearch.sample;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.xyc.elasticsearch.api.ClientInstance;

/**
 * Created by CCC on 2016/6/17.
 *
 * 讲下分词
 * 一个字段进入索引后，是预先被分好词的，之后搜索的时候会根据切分好的词来匹配
 *
 *
 */
public class QueryMatch extends QueryCommon {

    public static void search() {
        SearchResponse response = ClientInstance.getClient()
                .prepareSearch(indexes)
                .setTypes(types)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                //这个地方说明下，content是ik分词，contentNot是默认standard分词，内容都是“公安部：各地校车将享最高路权”
                //standard分词是把中文每个子拆开，然后保留原始字符串
                //matchQuery是分词搜索，es中的内容是分好词的，方法中的关键字也会分词
                //matchPhraseQuery是不分词搜索，es中的还是以分好词的为准，方法中的关键词不分词
                //matchPhrasePrefixQuery是前缀搜索，es中的以分好词的为准备，方法中的关键词分词
                //matchQuery，contentNot=地校车最，能搜到，因为contentNot是standard分词
                //matchQuery, content=地校车最，也能搜到，ik分词会分词分出校车两字，然后靠这个词匹配到，但是分数很低，如果再加上.minimumShouldMatch("70%")，就搜索不到了因为匹配度很低
                //matchPhraseQuery, contentNot=地校车最, 搜不到，因为是整段的搜，不匹配，如果加上.slop(2)，就匹配了，移动2位可以对得上
                //matchPhraseQuery, content=地校车最, 搜不到，即使加上去了slop(2)也搜不到，这个是因为ik和standard的区别，standard会保留原始的句子，而ik不会，只会保存分好的词，所以是没把分匹配到“地校车最”这个短语的
                //matchPhrasePrefixQuery, contentNot=地校车最, 搜不到，但是如果搜索单个子，比如“安”，“车”都能搜到，因为是standard分词
                //matchPhrasePrefixQuery, content=地校车最, 搜不到，但是如果搜索ik的分词是可以搜到的，比如“校”，因为ik有“校车”的分词
                //boost是权重值，boost值越大，相应的那个查询得到的分数就越高。当然最终分数不是简单的乘以boost，有内部机制调节，这个机制我不知道
                //setExplain方法，返回顺序按照分数值从大到小排序
//                .setQuery(QueryBuilders.matchQuery("name", "小明"))
//                .setQuery(QueryBuilders.matchPhraseQuery("content", "地校车最"))
//                .setQuery(QueryBuilders.matchPhrasePrefixQuery("content", "校"))
//                .setQuery(QueryBuilders.matchAllQuery())
                //update at 20160721
                //上海中环保时捷狂飙 英菲尼迪时速180公里跟拍，分词：standard（每个词一个term，另外保存一份原始文本）
                //matchQuery搜索“中环”可以中，搜索“环”也中，搜索“外环”可以中，因为standard分词对输入关键字分出了“外”和“环”，匹配到了。
                //matchPhraseQuery搜索“中环”可以中，因为standard的分词保留有原句，搜索“环”也中，因为standard是每个词分出来的，“外环”不中，因为输入关键字没分词，匹配不到
                //matchPhraseQuery搜索“中保”不中，如果加上slop(1)可以中，“中上”slop(3)才中
                //matchPhrasePrefixQuery搜素“中环”可以中，搜索“环”也中，搜索“外环”不中
                //在中文的时候，matchPhraseQuery和matchPhrasePrefixQuery是没有区别的
                //message：sky blue tea air sea
                //matchPhraseQuery的text是一个英文单词，而matchPhrasePrefixQuery的text则无这一约束
                //matchPhraseQuery搜索“blue tea air”可以中，搜索“blue te”不中，搜索“te”不中
                //matchPhrasePrefixQuery搜索“blue tea air”可以中，搜索“blue te”可以中，搜索“te”可以中
                //matchPhraseQuery对英文查询是以单词为准的。搜索“blue tea”可以中，“tea blue”不中，如果加slop(2)则可以中，如果“teablue”是不行的，变成一个单词了
                //matchPhraseQuery，搜索“tea”可以中，搜索“tae”不中，slop(10)都不行，因为默认分析器是以单词为准的，更改分析器拆分每个字母后，可能可以
                .setPostFilter(QueryBuilders.matchPhraseQuery("message", "tea blue").slop(2)).setFrom(0).setSize(60).setExplain(true)
//                .setPostFilter(QueryBuilders.matchPhraseQuery("remark", "中上").slop(3)).setFrom(0).setSize(60).setExplain(true)
                .execute().actionGet();

        System.out.println(response.getHits().totalHits());
    }

    public static void main(String[] args) {
        search();
    }
}
