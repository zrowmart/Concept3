package com.concept.test.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.concept.test.R;
import com.concept.test.model.PostClass;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<PostClass> postList;

    public PostAdapter(List<PostClass> values) {
        this.postList = values;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v =inflater.inflate(R.layout.post_list, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder viewHolder, int i) {
            PostClass postClass = postList.get(i);
            viewHolder.textTitle.setText( postClass.getPost_title() );
            viewHolder.textDetail.setText( postClass.getPost_Detail() );
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
