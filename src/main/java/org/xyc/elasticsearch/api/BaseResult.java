package org.xyc.elasticsearch.api;

/**
 * base result of elastic result
 * all result class must extends this class
 */
public class BaseResult extends BaseField {

    protected String index;
    protected String type;
    protected float score;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
