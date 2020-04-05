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
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * <h1>My Review RecyclerView controller</h1>
 * This class is responsible for putting the Review Objects in recycler view.
 *
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class MyReviewRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * Instance variable context.
     */
    private Context context;

    /**
     * Instance variable that contains the Review Objects passed in
     */
    private List<Review> ReviewList;

    /**
     * Constructor
     * @param context context of the activity that construct the adapter object
     * @param review list of Review objects
     */
    public MyReviewRecyclerAdapter(Context context, List<com.example.studygowhere.Entity.Review> review) {
        this.context = context;
        ReviewList = review;
        }


    /**
     * This class is to assign value to instance variables.
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        TextView tvrmContent, tvrmStudyArea, tvrmRating;
        ImageView imSA;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            imSA = (ImageView) itemView.findViewById(R.id.rmImage);
            tvrmContent = (TextView) itemView.findViewById(R.id.tvRmContent);
            tvrmStudyArea = (TextView) itemView.findViewById(R.id.tvRmStudyArea);
            tvrmRating = (TextView) itemView.findViewById(R.id.tvRmRating);

        }
    }

    /**
     * This is a override method to inflate the recycler view with layout reviews_made.
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_made, parent, false);
        return new MyReviewRecyclerAdapter.ItemViewHolder(view);
    }


    /**
     * This is a override method to set the content of each recyclerView item.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyReviewRecyclerAdapter.ItemViewHolder ivh = (MyReviewRecyclerAdapter.ItemViewHolder) holder;
        Review rev = ReviewList.get(position);
        ivh.tvrmContent.setText(rev.getContent());
        ivh.tvrmStudyArea.setText(rev.getStudyAreaName());
        for(int i = 0; i < DataHandler.studyAreaList.size(); i++)
        {
            if(DataHandler.studyAreaList.get(i).getName().equals(rev.getStudyAreaName()))
            {
                Picasso.get().load(DataHandler.studyAreaList.get(i).getImageURL()).placeholder(R.drawable.ic_launcher_background).into(ivh.imSA);
            }
        }
        if(rev.getRating() != null) {
            ivh.tvrmRating.setText(rev.getRating());
        }

    }


    /**
     * This is a override method to get the size of the instance variable ReviewList.
     * @return
     */
    @Override
    public int getItemCount() {
        if(ReviewList != null)
            return ReviewList.size();
        return 0;
    }




}
