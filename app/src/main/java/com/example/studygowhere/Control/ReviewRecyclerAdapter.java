package com.example.studygowhere.Control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studygowhere.Entity.Review;
import com.example.studygowhere.R;

import java.util.List;

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Review> ReviewList;

    public ReviewRecyclerAdapter(Context context, List<com.example.studygowhere.Entity.Review> review) {
        this.context = context;
        ReviewList = review;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        TextView tvAuthor, tvContent,tvRating;
        ImageView imSA;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            tvAuthor = (TextView) itemView.findViewById(R.id.tvAuthor);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
            tvRating = (TextView) itemView.findViewById(R.id.tvRating);
            imSA = (ImageView) itemView.findViewById(R.id.rmImage);
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
        ivh.tvAuthor.setText(rev.getAuthor());
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
