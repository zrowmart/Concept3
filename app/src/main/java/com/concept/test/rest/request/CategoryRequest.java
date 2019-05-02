package com.concept.test.rest.request;

import com.concept.test.model.BaseDomain;

public class CategoryRequest extends BaseDomain {

    String category;
    String title;
    String post;
    String autoId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getAutoId() {
        return autoId;
    }

    public void setAutoId(String autoId) {
        this.autoId = autoId;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "CategoryRequest{" +
                "title='" + title + '\'' +
                "post='" + post + '\'' +
                "category='" + category + '\'' +
                '}';
    }
}
