package com.example.cuba2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class FoodAdapter extends FirebaseRecyclerAdapter <FoodConstructor,FoodAdapter.FoodViewHolder>{


    public FoodAdapter(@NonNull FirebaseRecyclerOptions<FoodConstructor> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FoodAdapter.FoodViewHolder holder, int position, @NonNull FoodConstructor model) {

        holder.name.setText(model.getName());
        holder.calories.setText(model.getCalories());
        holder.type.setText(model.getType());
        Picasso.get().load(model.getImageurl()).into(holder.food);
    }

    @NonNull
    @Override
    public FoodAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_layout, parent, false);
        return new FoodAdapter.FoodViewHolder(view);
    }

    class FoodViewHolder
            extends RecyclerView.ViewHolder {
        ImageView food;
        TextView name, calories, type;
        public FoodViewHolder(@NonNull View itemView)
        {
            super(itemView);

            food=itemView.findViewById(R.id.foodimage);
            name = itemView.findViewById(R.id.foodname);
            calories = itemView.findViewById(R.id.foodcalories);
            type = itemView.findViewById(R.id.foodtype);
        }
    }
}
