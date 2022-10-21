package com.example.badmintoncourtfinderapp;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentHolder extends RecyclerView.ViewHolder {

    ImageView holdercompic;
    TextView holdercomname, holdercomtime, holdercomment;
    RatingBar holderrating;
    View v2;

    public CommentHolder(@NonNull View itemView2){
        super(itemView2);
        holdercompic = itemView2.findViewById(R.id.userimage);
        holdercomname = itemView2.findViewById(R.id.usernamecomment);
        holdercomtime = itemView2.findViewById(R.id.timecomment);
        holdercomment = itemView2.findViewById(R.id.commentdesc);
        holderrating = itemView2.findViewById(R.id.reviewrating);
        v2 = itemView2;
    }
}
