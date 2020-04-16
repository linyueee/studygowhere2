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


/**
 * <h1>Review RecyclerView controller</h1>
 * This class is responsible for putting the Review Objects in recycler view.
 *
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class ReviewRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * Instance variable context.
     */
    Context context;
    /**
     * Instance variable that contains the Review Objects passed in
     */
    private List<Review> ReviewList;

    /**
     * Constructor
     * @param context context of the activity that construct the adapter object
     * @param review list of Review objects
     */
    public ReviewRecyclerAdapter(Context context, List<com.example.studygowhere.Entity.Review> review) {
        this.context = context;
        ReviewList = review;
    }


    /**
     * This class is to assign value to instance variables.
     */
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


    /**
     * This is a override method to inflate the recycler view with layout review_list.
     * @param parent
     * @param viewType
     * @return Inflated View Holder
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list, parent, false);
        return new ItemViewHolder(view);
    }


    /**
     * This is a override method to set the content of each recyclerView item.
     * @param holder
     * @param position
     */
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


    /**
     * This is a override method to get the size of the instance variable ReviewList.
     * @return Review list size
     */
    @Override
    public int getItemCount() {
       if(ReviewList != null)
           return ReviewList.size();
       return 0;
    }
}
