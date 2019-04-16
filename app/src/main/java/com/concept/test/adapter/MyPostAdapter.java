package com.concept.test.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.concept.test.R;
import com.concept.test.rest.response.PostResponse;

import java.util.List;

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.ViewHolder> {

    private List<PostResponse> postList;
    int rowLayout;
    Context context;

    public MyPostAdapter(List<PostResponse> values, int rowLayout, Context context) {
        this.context = context;
        this.rowLayout = rowLayout;
        this.postList = values;


    }

    @NonNull
    @Override
    public MyPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostAdapter.ViewHolder viewHolder, int i) {
        PostResponse postResponse = postList.get( i );
        viewHolder.textTitle.setText( postResponse.getTitle() );
        viewHolder.textDetail.setText( postResponse.getPost().substring( 0,100 ) );
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textDetail;
        View layout;
        ViewHolder(@NonNull View itemView) {
            super( itemView );
            layout = itemView;
            textDetail = itemView.findViewById( R.id.post_detail );
            textTitle = itemView.findViewById( R.id.post_title );
        }
    }


}

