package com.concept.test.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.concept.test.R;
import com.concept.test.model.PostClass;
import com.concept.test.rest.response.PostResponse;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<PostResponse> postList;
    int rowLayout;
    Context context;

    public PostAdapter(List<PostResponse> values, int rowLayout, Context context) {
        this.context = context;
        this.rowLayout=rowLayout;
        this.postList = values;


    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
//        View v =inflater.inflate(R.layout.post_list, viewGroup, false);
//        ViewHolder vh = new ViewHolder(v);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder viewHolder, int i) {
            //PostClass postClass = postList.get(i);
            PostResponse postResponse = postList.get( i );
            viewHolder.textTitle.setText( postResponse.getTitle() );
            viewHolder.textDetail.setText( postResponse.getPost() );
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
