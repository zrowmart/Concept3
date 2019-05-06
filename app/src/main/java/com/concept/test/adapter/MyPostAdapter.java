package com.concept.test.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.concept.test.R;
import com.concept.test.activity.FullStoryActivity;
import com.concept.test.rest.request.ShowMyPost;
import com.concept.test.rest.response.PostResponse;

import java.util.List;

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.ViewHolder> {

    private List<ShowMyPost> myPostList;
    int rowLayout;
    Context context;
    private String title = null;
    private String detail = null;

    public MyPostAdapter(List<ShowMyPost> values, int rowLayout, Context context) {
        this.context = context;
        this.rowLayout = rowLayout;
        this.myPostList = values;


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
        final ShowMyPost myPostResponse = myPostList.get( i );
        CardView cardView = viewHolder.cardView;
        viewHolder.textTitle.setText( myPostResponse.getTitle() );
        viewHolder.textDetail.setText( myPostResponse.getPost().substring( 0,100 ) );
        title = myPostResponse.getTitle();
        detail = myPostResponse.getPost();
        cardView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context, FullStoryActivity.class );
//                intent.putExtra( "PostTitle", title );
//                intent.putExtra( "PostDetail", detail );
                intent.putExtra( "PostTitle" ,myPostResponse.getTitle());
                intent.putExtra( "PostDetail", myPostResponse.getPost());
                v.getContext().startActivity( intent );
            }
        } );
    }

    @Override
    public int getItemCount() {
        return myPostList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textDetail;
        View layout;
        CardView cardView;
        ViewHolder(@NonNull View itemView) {
            super( itemView );
            layout = itemView;
            textDetail = itemView.findViewById( R.id.post_detail );
            textTitle = itemView.findViewById( R.id.post_title );
            cardView = itemView.findViewById( R.id.storyCard );

        }
    }


}

