package com.example.badmintoncourtfinderapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class ReviewHolder extends RecyclerView.ViewHolder {

    ImageView holderreviewpic;
    TextView holderreviewname, holderreviewlocation, holderreviewcontact;
    View v;

    public ReviewHolder(@NonNull View itemView){
        super(itemView);
        holderreviewpic = itemView.findViewById(R.id.reviewimage);
        holderreviewname = itemView.findViewById(R.id.reviewname);
        holderreviewlocation = itemView.findViewById(R.id.reviewlocation);
        holderreviewcontact = itemView.findViewById(R.id.reviewcontact);
        v=itemView;
    }
}
