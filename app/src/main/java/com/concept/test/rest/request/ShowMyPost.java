package com.concept.test.rest.request;

import com.concept.test.model.BaseDomain;
import com.concept.test.util.UserLocalStore;

public class ShowMyPost extends BaseDomain {

    private String title,post,autoId;

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

    @Override
    public String toString() {
        return "ShowMyPost{" +
                "autoId='" + autoId +
                '}';
    }
}
