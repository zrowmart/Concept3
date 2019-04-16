package com.concept.test.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.concept.test.R;
import com.concept.test.activity.FullStoryActivity;
import com.concept.test.rest.response.PostResponse;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>  implements View.OnClickListener {

    private List<PostResponse> postList;
    private int rowLayout;
    private final Context context;
    private String title = null;
    private String detail=null;
    private CardView cardView;

    public PostAdapter(List<PostResponse> values, int rowLayout, Context context) {
        this.context = context;
        this.rowLayout=rowLayout;
        this.postList = values;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostAdapter.ViewHolder viewHolder, int i) {
            PostResponse postResponse = postList.get( i );
            viewHolder.textTitle.setText( postResponse.getTitle() );
            viewHolder.textDetail.setText( postResponse.getPost().substring( 0,100 ) );

            title = postResponse.getTitle();
            detail = postResponse.getPost();

            cardView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent (context, FullStoryActivity.class);
                    intent.putExtra("PostTitle",title);
                    intent.putExtra("PostDetail",detail);
                    v.getContext().startActivity(intent);
                }
            } );
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    @Override
    public void onClick(View v) {

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
            cardView = itemView.findViewById( R.id.storyCard );
        }
    }
}
