package org.xyc.elasticsearch.api.operation;

/**
 *
 */
public class FailureMessage {

    private int itemId;
    private String message;

    public FailureMessage(int itemId, String message) {
        this.itemId = itemId;
        this.message = message;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
