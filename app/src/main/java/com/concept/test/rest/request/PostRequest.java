package com.concept.test.rest.request;

public class PostRequest {


    String postDetail,postTitle;

    public PostRequest() {
    }

    public String getPostDetail() {
        return postDetail;
    }

    public void setPostDetail(String postDetail) {
        this.postDetail = postDetail;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "posttitle='" + postTitle + '\'' +
                ", postdetail='" + postDetail + '\'' +
                '}';
    }
}



