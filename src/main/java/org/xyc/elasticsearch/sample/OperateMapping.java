package org.xyc.elasticsearch.sample;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.xyc.elasticsearch.api.ClientInstance;


import static org.elasticsearch.common.xcontent.XContentFactory.*;

/**
 * Created by CCC on 2016/6/22.
 */
public class OperateMapping {

    public static void mapping() throws Exception {
        XContentBuilder content = XContentFactory.jsonBuilder().startObject().startObject("page").startObject("properties").startObject("title")
                .field("type", "string").field("indexAnalyzer", "ik")
                .field("searchAnalyzer", "ik")
                .endObject().startObject("code").field("type", "string").field("indexAnalyzer", "ik")
                .field("searchAnalyzer", "ik")
                .endObject()
                .endObject()
                .endObject()
                .endObject();
    }

        private static String getAnalysisSettings() throws Exception {
                XContentBuilder mapping = jsonBuilder()
                        .startObject()
                                //主分片数量
                        .field("number_of_shards",5)
                        .field("number_of_replicas",0)
                        .startObject("analysis")
                        .startObject("filter")
                                //创建分词过滤器
                        .startObject("pynGram")
                        .field("type","nGram")
                                //从1开始
                        .field("min_gram",1).field("max_gram",15)
                        .endObject()
                        .endObject()

                        .startObject("analyzer")
                                //拼音analyszer
                        .startObject("pyAnalyzer").field("type","custom").field("tokenizer", "standard").field("filter", new String[]{ "lowercase","pynGram"}).endObject()
                        .endObject()
                        .endObject()
                        .endObject();
                System.out.println(mapping.string());
                return mapping.string();
        }
        /**
         * mapping 一旦定义，之后就不能修改。
         * @return
         * @throws Exception
         */
        private static XContentBuilder getMapping() throws Exception{
                XContentBuilder mapping = jsonBuilder()
                        .startObject()
                        .startObject("icd")
                                //指定分词器
                        .field("index_analyzer","pyAnalyzer").startObject("properties").startObject("id").field("type", "long")
                        .field("store", "yes")
                        .endObject()

                        .startObject("code").field("type", "string").field("store", "yes")
                        .field("index", "analyzed")
                        .endObject()

                        .startObject("diseaseName").field("type", "string").field("store", "yes")
                        .field("index", "analyzed")
                        .endObject()

                        .startObject("mergeName").field("type", "string").field("store", "yes")
                        .field("index", "analyzed")
                        .endObject()

                        .startObject("pinyin").field("type", "string").field("store", "yes")
                        .field("index", "analyzed")
                        .endObject()

                        .startObject("isTherioma").field("type", "boolean")
                        .field("store", "yes")
                        .endObject()

                        .startObject("isSpecialDisease").field("type", "boolean")
                        .field("store", "yes")
                        .endObject()

                        .endObject()
                        .endObject()
                        .endObject();
                return mapping;
        }

        private static void createMapping() throws Exception{
                IndexResponse response = ClientInstance.getClient().prepareIndex("twitter", "tweet", "1").setSource(getMapping()).get();
        }
}
