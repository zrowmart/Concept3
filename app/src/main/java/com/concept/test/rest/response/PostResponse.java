package com.concept.test.rest.response;

import com.concept.test.model.BaseDomain;

public class PostResponse extends BaseDomain {

    String postDetail,postTitle;

    public PostResponse() {
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



