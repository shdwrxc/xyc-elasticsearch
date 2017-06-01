package org.xyc.elasticsearch.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xyc.elasticsearch.api.operation.CrudOperation;
import org.xyc.elasticsearch.api.operation.IndexOperation;
import org.xyc.elasticsearch.api.operation.MappingBuilder;

public class IndexOperationTest {

    IndexOperation operation;
    CrudOperation crudOperation;

    @Before
    public void before() {
        operation = new IndexOperation();
        crudOperation = new CrudOperation("test2", "type1");
    }

    @Test
    public void testAddIndex() {
        boolean result = operation.createIndex("test1");
        Assert.assertTrue(result);
    }

    @Test
    public void testAddIndexWithMapping() {
        MappingBuilder mappingFields = MappingBuilder.getInstance();
        mappingFields.addFieldUsingLike("field2", "String");
        boolean result = operation.createIndex("test1", "type1", mappingFields);
        Assert.assertTrue(result);
    }

    @Test
    public void testAddMapping() {
        MappingBuilder mappingFields = MappingBuilder.getInstance();
        mappingFields.addFieldNotAnalyzed("column1", "String");
        mappingFields.addFieldUsingLike("column2", "String");
        boolean result = operation.addMapping("test2", "type1", mappingFields);
        Assert.assertTrue(result);
    }

    @Test
    public void testDeleteIndex() {
        boolean result = operation.deleteIndex("drp");
        Assert.assertTrue(result);
    }

    @Test
    public void testAdd() {
        String record1 = "{\"field1\":\"aBc_123_IoP\",\"field2\":\"AbC-HdK-123\"}";
        crudOperation.add(record1);
    }

}
