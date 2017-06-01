package org.xyc.elasticsearch.api.operation;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptService;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

/**
 * Created by CCC on 2016/6/15.
 */
public class SimpleSortBuilder {

    private List<FieldSortBuilder> sortFields = Lists.newArrayList();
    private List<ScriptSortBuilder> scriptSortFields = Lists.newArrayList();

    private SimpleSortBuilder() {

    }

    public static SimpleSortBuilder simpleSort() {
        return new SimpleSortBuilder();
    }

    public SimpleSortBuilder sortAsc(String field) {
        sortFields.add(SortBuilders.fieldSort(field).order(SortOrder.ASC));
        return this;
    }

    public SimpleSortBuilder sortDesc(String field) {
        sortFields.add(SortBuilders.fieldSort(field).order(SortOrder.DESC));
        return this;
    }

    public SimpleSortBuilder sort(String field, String sort) {
        SortOrder sortOrder = SortOrder.ASC;
        if (SortOrder.DESC.toString().equals(sort))
            sortOrder = SortOrder.DESC;
        sortFields.add(SortBuilders.fieldSort(field).order(sortOrder));
        return this;
    }

    public SimpleSortBuilder sortTop(String field, Object value) {
        Map<String, Object> params = Maps.newHashMap();
        String fieldParam = "param" + field;
        params.put(fieldParam, value);
        String scriptString = "fieldValue = doc['" + field + "'].value; if (fieldValue == " + fieldParam + ") return 1; else return 0;";
        Script script = new Script(scriptString, ScriptService.ScriptType.INLINE, "groovy", params);
        scriptSortFields.add(SortBuilders.scriptSort(script, "number").order(SortOrder.DESC));
        return this;
    }

    public SimpleSortBuilder sortTop(String field, Object... value) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("onefield", value);
        //这里特殊的地方，在each的时候，一定要套个();否则会报错
        String scriptString = "fieldValue=doc['" + field + "'].value;def hasit=false;onefield.each({if(fieldValue==it) hasit=true;});if(hasit) return 1;else return 0;";
        //not work. return in the loop
//        String scriptString = "fieldValue=doc['" + field + "'].value;def hasit=false;onefield.each({if(fieldValue==it) return 1;});return 0;";
        Script script = new Script(scriptString, ScriptService.ScriptType.INLINE, "groovy", params);
        scriptSortFields.add(SortBuilders.scriptSort(script, "number").order(SortOrder.DESC));
        return this;
    }

    public List<FieldSortBuilder> sortBuilders() {
        return sortFields;
    }

    public List<ScriptSortBuilder> scriptSortBuilders() {
        return scriptSortFields;
    }
}
