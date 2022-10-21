package com.example.cuba2.HelperClasses.HomeAdapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuba2.R;

import java.util.ArrayList;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> {

    ArrayList<FeaturedHelper> featuredLocations;

    //hold data
    public FeaturedAdapter(ArrayList<FeaturedHelper> featuredLocations) {
        this.featuredLocations = featuredLocations;
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card_design,parent,false);
        FeaturedViewHolder featuredViewHolder=new FeaturedViewHolder(view);
        return featuredViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {

      FeaturedHelper featuredHelper=featuredLocations.get(position);
      holder.image.setImageResource(featuredHelper.getImage());
      holder.title.setText(featuredHelper.getTitle());
      holder.desc.setText(featuredHelper.getDescription());

    }

    @Override
    public int getItemCount() {
        return featuredLocations.size();
    }


    public static class FeaturedViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView title,desc;

        public FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);


            //hooks
            image=itemView.findViewById(R.id.featured_image);
            title=itemView.findViewById(R.id.featured_title);
            desc=itemView.findViewById(R.id.featured_desc);

        }
    }
}
