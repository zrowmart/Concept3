package com.concept.test.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.concept.test.R;
import com.concept.test.rest.request.CategoryRequest;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryRequest> myPostList;
    int rowLayout;
    Context context;

    public CategoryAdapter(List<CategoryRequest> values, int rowLayout, Context context) {
        this.context = context;
        this.rowLayout = rowLayout;
        this.myPostList = values;


    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder viewHolder, int i) {
        CategoryRequest categoryRequest = myPostList.get( i );
        viewHolder.textTitle.setText( categoryRequest.getTitle() );
        viewHolder.textDetail.setText( categoryRequest.getPost().substring( 0,100 ) );
    }

    @Override
    public int getItemCount() {
        return myPostList.size();
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


