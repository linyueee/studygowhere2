package com.example.studygowhere;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyReviewRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Review> ReviewList;

    public MyReviewRecyclerAdapter(Context context, List<com.example.studygowhere.Review> review) {
        this.context = context;
        ReviewList = review;
        }



public class ItemViewHolder extends RecyclerView.ViewHolder
{
    View mview;
    TextView tvrmcontent, tvrmstudyarea, tvrmrating;
    ImageView imsa;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        mview = itemView;
        imsa = (ImageView) itemView.findViewById(R.id.rmimage);
        tvrmcontent = (TextView) itemView.findViewById(R.id.tvrmcontent);
        tvrmstudyarea = (TextView) itemView.findViewById(R.id.tvrmstudyarea);
        tvrmrating = (TextView) itemView.findViewById(R.id.tvrmrating);

    }
}
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_made, parent, false);
        return new MyReviewRecyclerAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyReviewRecyclerAdapter.ItemViewHolder ivh = (MyReviewRecyclerAdapter.ItemViewHolder) holder;
        Review rev = ReviewList.get(position);
        ivh.tvrmcontent.setText(rev.getContent());
        ivh.tvrmstudyarea.setText(rev.getStudyAreaName());
        for(int i = 0; i < Datahandler.studyAreaList.size(); i++)
        {
            if(Datahandler.studyAreaList.get(i).getName().equals(rev.getStudyAreaName()))
            {
                Picasso.get().load(Datahandler.studyAreaList.get(i).getImageurl()).placeholder(R.drawable.ic_launcher_background).into(ivh.imsa);
            }
        }
        if(rev.getRating() != null) {
            ivh.tvrmrating.setText(rev.getRating());
        }

    }

    @Override
    public int getItemCount() {
        if(ReviewList != null)
            return ReviewList.size();
        return 0;
    }




}
