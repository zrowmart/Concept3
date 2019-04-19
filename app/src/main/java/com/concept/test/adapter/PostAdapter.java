package com.concept.test.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;
import com.concept.test.R;
import com.concept.test.activity.FullStoryActivity;
import com.concept.test.rest.response.PostResponse;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private final Context context;
    private List<PostResponse> postList;
    private int rowLayout;
    private String title = null;
    private String detail = null;

    public PostAdapter(List<PostResponse> postList, int rowLayout, Context context) {
        this.postList = postList;
        this.context = context;
        this.rowLayout = rowLayout;

    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from( viewGroup.getContext() ).inflate( rowLayout, viewGroup, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder viewHolder, final int position) {
        final PostResponse postResponse = postList.get( position );
        CardView cardView = viewHolder.cardView;

        viewHolder.textTitle.setText( postResponse.getTitle() );
        viewHolder.textDetail.setText( postResponse.getPost().substring( 0, 100 ) );
        title = postResponse.getTitle();
        detail = postResponse.getPost();
        cardView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context, FullStoryActivity.class );
//                intent.putExtra( "PostTitle", title );
//                intent.putExtra( "PostDetail", detail );
                intent.putExtra( "PostTitle" ,postResponse.getTitle());
                intent.putExtra( "PostDetail", postResponse.getPost());
                v.getContext().startActivity( intent );
            }
        } );
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textDetail;
        CardView cardView;
        View layout;

        ViewHolder(@NonNull View itemView) {
            super( itemView );
            layout = itemView;
            textDetail = itemView.findViewById( R.id.post_detail );
            textTitle = itemView.findViewById( R.id.post_title );
            cardView = itemView.findViewById( R.id.storyCard );
        }
    }

    public void setFilter(List<PostResponse> searchList){
        postList = new ArrayList<>(  );
        postList.addAll( searchList );
        notifyDataSetChanged();
    }



}
