package com.example.studygowhere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Review> ReviewList;

    public ReviewRecyclerAdapter(Context context, List<com.example.studygowhere.Review> review) {
        this.context = context;
        ReviewList = review;
    }



    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        View mview;
        TextView tvauthor, tvContent,tvRating;
        ImageView imsa;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mview = itemView;
            tvauthor = (TextView) itemView.findViewById(R.id.tvAuthor);
            tvContent = (TextView) itemView.findViewById(R.id.tvcontent);
            tvRating = (TextView) itemView.findViewById(R.id.tvrating);
            imsa = (ImageView) itemView.findViewById(R.id.rmimage);

        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ItemViewHolder ivh = (ItemViewHolder) holder;
            Review rev = ReviewList.get(position);
            ivh.tvauthor.setText(rev.getAuthor());
             ivh.tvContent.setText(rev.getContent());
             if(rev.getRating() != null) {
                 ivh.tvRating.setText(rev.getRating());
             }

    }

    @Override
    public int getItemCount() {
       if(ReviewList != null)
           return ReviewList.size();
       return 0;
    }




}
