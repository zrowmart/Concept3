package com.concept.test.model;

public class PostClass {

    private String post_title;
    private String post_Detail;
    private String post_category;
    public PostClass(){

    }

    public PostClass(String post_title, String post_Detail, String post_category) {
        this.post_title = post_title;
        this.post_Detail = post_Detail;
        this.post_category = post_category;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_Detail() {
        return post_Detail;
    }

    public void setPost_Detail(String post_Detail) {
        this.post_Detail = post_Detail;
    }

    public String getPost_category() {
        return post_category;
    }

    public void setPost_category(String post_category) {
        this.post_category = post_category;
    }
}
