package com.example.studygowhere;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<Object> studyArea;
/*    private OnItemClickListener mListener;*/

    public RecyclerAdapter(Context context, List<Object> studyArea)
    {
        this.context = context;
        this.studyArea = studyArea;
    }
/*    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }*/
    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        View mview;
        TextView studyname;
        ImageView saImage;
        RelativeLayout relative;
        TextView address;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mview = itemView;
            studyname = (TextView) itemView.findViewById(R.id.tvStudyArea);
            saImage = (ImageView) itemView.findViewById(R.id.image);
            relative = (RelativeLayout) itemView.findViewById(R.id.relativelo);
            address = (TextView) itemView.findViewById(R.id.tvaddress);


        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.study_area_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        ItemViewHolder ivh = (ItemViewHolder) holder;
        StudyArea sa = (StudyArea) studyArea.get(position);
        ivh.studyname.setText(sa.getName());
        if(sa.getAddress() != null) {
            ivh.address.setText(sa.getAddress());
        }
        if(((StudyArea) studyArea.get(position)).getImageurl() != null) {
            Picasso.get().load(sa.getImageurl()).placeholder(R.drawable.ic_launcher_background).into(ivh.saImage);
        }

        ivh.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selected = new Intent(context, DetailActivity.class);
                selected.putExtra("Name", ((StudyArea) studyArea.get(position)).getName());

                if(((StudyArea) studyArea.get(position)).getAddress() != null)
                {
                    selected.putExtra("Address", ((StudyArea) studyArea.get(position)).getAddress());
                }

                if(((StudyArea) studyArea.get(position)).getImageurl() != null) {
                    selected.putExtra("Image", ((StudyArea) studyArea.get(position)).getImageurl());
                }
                selected.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(selected);
            }
        });

    }

    @Override
    public int getItemCount() {
        return studyArea.size();
    }
}
