package org.xyc.elasticsearch.api;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

/**
 * parse
 */
public class ElasticBeanUtils {

    private static final Map<String, Field[]> FIELD_LIST_MAP = new HashMap<String, Field[]>();

    private static Field[] getFieldArray(Class clazz) {
        if (clazz == null)
            return new Field[0];
        String className = clazz.getName();
        Field[] fields = FIELD_LIST_MAP.get(className);
        synchronized (FIELD_LIST_MAP) {
            if (fields == null) {
                fields = clazz.getDeclaredFields();
                FIELD_LIST_MAP.put(className, fields);
            }
        }
        return fields;
    }

    public static <T extends BaseResult> List<T> copyByJson(SearchHits hits, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (hits.totalHits() == 0)
            return result;
        for (SearchHit hit : hits.getHits()) {
            T t = copyByJson(hit, clazz);
            if (t != null) {
                result.add(t);
            }
        }
        return result;
    }

    public static <T extends BaseResult> T copyByJson(SearchHit hit, Class<T> clazz) {
        T t = JsonUtils.parseObject(hit.getSourceAsString(), clazz);
        if (t != null) {
            t.setIndex(hit.getIndex());
            t.setType(hit.getType());
            t.setId(hit.getId());
            t.setScore(hit.getScore());
        }
        return t;
    }

    public static <T extends BaseResult> List<T> copy(SearchHits hits, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (hits.totalHits() == 0)
            return result;
        for (SearchHit hit : hits.getHits()) {
            T t = copy(clazz, hit.sourceAsMap());
            if (t != null) {
                t.setIndex(hit.getIndex());
                t.setType(hit.getType());
                t.setId(hit.getId());
                t.setScore(hit.getScore());
                result.add(t);
            }
        }
        return result;
    }

    public static <T> T copy(Class<T> clazz, Map<String, Object> map) {
        if (clazz == null || map == null)
            return null;
        try {
            T t = clazz.newInstance();
            Field[] fields = getFieldArray(clazz);
            for (String keyName : map.keySet()) {
                for (Field field : fields) {
                    String fieldName = field.getName();
                    ElasticField elasticField = field.getAnnotation(ElasticField.class);
                    if (elasticField != null)
                        fieldName = elasticField.field();
                    if (fieldName.equalsIgnoreCase(keyName)) {
                        setValue(t, field, map.get(keyName));
                        break;
                    }
                }
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <T> void setValue(T t, Field field, Object value) {
        try {
            field.setAccessible(true);
            Class<?> type = field.getType();
            if (type == Long.class) {
                field.set(t, Long.valueOf((String)value));
            } else if (type == Date.class) {
                field.set(t, DateFormatUtils.parseDefault((String)value));
            } else {
                field.set(t, value);
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    public static List<String> copyJson(SearchHits hits) {
        List<String> result = new ArrayList<String>();
        if (hits.totalHits() == 0)
            return result;
        for (SearchHit hit : hits.getHits()) {
            result.add(hit.getSourceAsString());
        }
        return result;
    }
}
