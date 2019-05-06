package com.concept.test.rest.request;

import com.concept.test.model.BaseDomain;

import org.jetbrains.annotations.NotNull;

public class CategoryRequest extends BaseDomain {

    private String category;
    private String title;
    private String post;
    private String autoId;

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

    @NotNull
    @Override
    public String toString() {
        return "CategoryRequest{" +
                "title='" + title + '\'' +
                "post='" + post + '\'' +
                "category='" + category + '\'' +
                '}';
    }
}
