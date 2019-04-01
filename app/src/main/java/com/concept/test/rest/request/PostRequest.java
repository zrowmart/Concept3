package com.concept.test.rest.request;

public class PostRequest {

    String autoId,postId,post;

    public String getAutoId() {
        return autoId;
    }

    public void setAutoId(String autoId) {
        this.autoId = autoId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "PostRequest{" +
                "autoId='" + autoId + '\'' +
                ", postId='" + postId + '\'' +
                ", post='" + post + '\'' +
                '}';
    }
}



