package com.example.laffayeh.model;

public class Comments {
    private String Comment;
    private String name;
    private String key;
    private int report;
    private String CommentKey;


    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getReport() {
        return report;
    }

    public void setReport(int report) {
        this.report = report;
    }

    public String getCommentKey() {
        return CommentKey;
    }

    public void setCommentKey(String commentKey) {
        CommentKey = commentKey;
    }
}
