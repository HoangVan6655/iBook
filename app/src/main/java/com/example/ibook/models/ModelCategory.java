package com.example.ibook.models;

public class ModelCategory {
    String category, id, uid, timestamp;

    public ModelCategory() {
    }

    public ModelCategory(String category, String id, String uid, String timestamp) {
        this.category = category;
        this.id = id;
        this.uid = uid;
        this.timestamp = timestamp;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
