package com.concept.test.rest.response;

import com.concept.test.model.BaseDomain;

public class PostResponse extends BaseDomain {


    //String postDetail,postTitle;
    String category ,postId,title,post;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

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



    @Override
    public String toString() {
        return "PostResponse{" +
                "category='" + category + '\'' +
                ", postId='" + postId + '\'' +
                ", title='" + title + '\'' +
                ", post='" + post + '\'' +

                '}';
    }
}



