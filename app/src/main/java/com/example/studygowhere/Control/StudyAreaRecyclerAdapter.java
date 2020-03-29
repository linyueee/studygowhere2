package com.example.studygowhere.Control;

import android.content.Context;
import android.content.Intent;
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

import com.example.studygowhere.Boundary.DetailActivity;
import com.example.studygowhere.Entity.StudyArea;
import com.example.studygowhere.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StudyAreaRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private Context context;
    private List<StudyArea> studyArea;
    private List<StudyArea> fullList;
    private List<StudyArea> studyAreaFullList;
    //private List<StudyArea> fullList;
/*    private OnItemClickListener mListener;*/

    public StudyAreaRecyclerAdapter(Context context, List<StudyArea> studyArea)
    {
        this.context = context;
        this.fullList = new ArrayList<>(studyArea);
        //this.studyAreaFullList = studyArea;
        this.studyArea = new ArrayList<>(studyArea);
        //this.studyArea.addAll(studyArea);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        TextView studyName, distance;
        ImageView saImage;
        RelativeLayout relative;
        TextView address;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            studyName = (TextView) itemView.findViewById(R.id.tvStudyArea);
            saImage = (ImageView) itemView.findViewById(R.id.image);
            relative = (RelativeLayout) itemView.findViewById(R.id.relativeLO);
            address = (TextView) itemView.findViewById(R.id.tvAddress);
            distance = (TextView) itemView.findViewById(R.id.tvDistance);

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
        ivh.studyName.setText(sa.getName());

        ivh.distance.setText(sa.getDistance());
        if(sa.getAddress() != null) {
            ivh.address.setText(sa.getAddress());
        }
        if(((StudyArea) studyArea.get(position)).getImageURL() != null) {
            Picasso.get().load(sa.getImageURL()).placeholder(R.drawable.ic_launcher_background).into(ivh.saImage);
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

                if(((StudyArea) studyArea.get(position)).getImageURL() != null) {
                    selected.putExtra("Image", ((StudyArea) studyArea.get(position)).getImageURL());
                }
                if(((StudyArea) studyArea.get(position)).getLatLng() != null) {
                    selected.putExtra("LatLng", ((StudyArea) studyArea.get(position)).getLatLng());
                }
                selected.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(selected);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(studyArea != null) {
            return studyArea.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter(){
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<StudyArea> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(fullList);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(StudyArea obj : fullList){
                    if(obj.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(obj);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values =filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            studyArea.clear();
            studyArea.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}