package com.example.cuba2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.example.cuba2.Results;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HistoryAdapter extends FirebaseRecyclerAdapter <ResultConstruct,HistoryAdapter.HistoryHolder> {

    DatabaseReference reference;
    private FirebaseAuth firebaseAuth;






    public HistoryAdapter(@NonNull FirebaseRecyclerOptions<ResultConstruct> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull HistoryAdapter.HistoryHolder holder, int position, @NonNull ResultConstruct model) {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser users = firebaseAuth.getCurrentUser();
        final String finaluser = users.getUid();

        holder.bmr1.setText(model.getBmr());
        holder.tdee1.setText(model.getTdee());
        holder.bmi1.setText(model.getBmi());
        holder.bmidetail1.setText(model.getBmidet());
        holder.tty1.setText(model.getTty());

        holder.delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.delete1.getContext());
                builder.setTitle("Delete Panel");
                builder.setMessage("Delete...?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference("user").child(finaluser).child("health info").child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });


       

    }

    @NonNull
    @Override
    public HistoryAdapter.HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_layout, parent, false);
        return new HistoryAdapter.HistoryHolder(view);
    }

    class HistoryHolder
            extends RecyclerView.ViewHolder {
        public ImageView delete1;

        TextView bmr1,tdee1,bmi1,bmidetail1,tty1;
        public HistoryHolder(@NonNull View itemView)
        {
            super(itemView);
          ;
            tdee1= itemView.findViewById(R.id.tdeeValue1);
            bmi1= itemView.findViewById(R.id.bmi11);
            bmidetail1= itemView.findViewById(R.id.bmidetail11);
            delete1=itemView.findViewById(R.id.delete);






        }
    }
}
