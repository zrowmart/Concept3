package com.concept.test.rest.request;

import android.util.Log;

import com.concept.test.util.UserLocalStore;

public class ShowMyPost {

    private String title,post;
    private UserLocalStore userLocalStore;
    private String autoId = userLocalStore.fetchUserData().getAutoId() ;

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
                "autoId='" + autoId + '\'' +
                ", title='" + title + '\'' +
                ", post='" + post + '\'' +
                '}';
    }
}
