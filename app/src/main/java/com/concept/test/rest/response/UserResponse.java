package com.concept.test.rest.response;

import com.concept.test.model.BaseDomain;

public class UserResponse extends BaseDomain {

    String post,username;

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "username='" + username + '\'' +
                ", post='" + post + '\'' +
                '}';
    }
}
