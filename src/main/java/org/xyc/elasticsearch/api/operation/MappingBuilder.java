package org.xyc.elasticsearch.api.operation;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

public class MappingBuilder {

    private static final String ANALYZER_KEYWORD_LOWERCASE = "keyword_lowercase";

    private static final String INDEX_NOT_ANALYZED = "not_analyzed";

    private List<MappingField> mappingFields = new ArrayList<MappingField>();

    private MappingBuilder() {

    }

    public static MappingBuilder getInstance() {
        return new MappingBuilder();
    }

    public void addField(String name, String type) {
        mappingFields.add(new MappingField(name, type));
    }

    public void addFieldUsingLike(String name, String type) {
        mappingFields.add(new MappingField(name, type).setAnalyzer(ANALYZER_KEYWORD_LOWERCASE));
    }

    public void addFieldNotAnalyzed(String name, String type) {
        mappingFields.add(new MappingField(name, type).setIndex(INDEX_NOT_ANALYZED));
    }

    public void addField(String name, String type, String analyzer) {
        mappingFields.add(new MappingField(name, type).setAnalyzer(analyzer));
    }

    public XContentBuilder buildDefaultSetting() {
        try {
            XContentBuilder contentBuilder = XContentFactory.jsonBuilder();
            contentBuilder.startObject();   //start all

            contentBuilder.startObject("analysis");
            contentBuilder.startObject("analyzer");
            contentBuilder.startObject(ANALYZER_KEYWORD_LOWERCASE).field("type", "custom").field("tokenizer", "keyword").field("filter", "lowercase").endObject();
            contentBuilder.endObject(); //end analyzer
            contentBuilder.endObject(); //end analysis

            contentBuilder.endObject(); //end all
            return contentBuilder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public XContentBuilder buildPropertyMapping() {
        try {
            XContentBuilder contentBuilder = XContentFactory.jsonBuilder();
            contentBuilder.startObject();   //start all

            contentBuilder.startObject("properties");
            for (MappingField mf : mappingFields) {
                contentBuilder.startObject(mf.getName()).field("type", mf.getType());
                if (mf.hasIndex())
                    contentBuilder.field("index", mf.getIndex());
                if (mf.hasAnalyzer())
                    contentBuilder.field("analyzer", mf.getAnalyzer());
                contentBuilder.endObject();
            }
            contentBuilder.endObject(); //end properties

            contentBuilder.endObject(); //end all
            return contentBuilder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private class MappingField {
        private String name;
        private String type;
        private String index;
        private String analyzer;

        private MappingField(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public MappingField setIndex(String index) {
            this.index = index;
            return this;
        }

        public MappingField setAnalyzer(String analyzer) {
            this.analyzer = analyzer;
            return this;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIndex() {
            return index;
        }

        public boolean hasIndex() {
            return index != null && !index.isEmpty();
        }

        public String getAnalyzer() {
            return analyzer;
        }

        public boolean hasAnalyzer() {
            return analyzer != null && !analyzer.isEmpty();
        }
    }
}
