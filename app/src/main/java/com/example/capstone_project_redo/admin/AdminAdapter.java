package com.example.capstone_project_redo.admin;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capstone_project_redo.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;


public class AdminAdapter extends FirebaseRecyclerAdapter<AdminModel, AdminAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdminAdapter(@NonNull FirebaseRecyclerOptions<AdminModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull AdminModel model) {
        holder.FullName.setText(model.getFirstName()+" "+model.getLastName());
        holder.EmailAddress.setText(model.getEmailAddress());
        holder.activate.setText(model.getActivate());

        holder.showImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.showImage.getContext())
                        .setContentHolder(new ViewHolder(R.layout.viewholder_showimage))
                        .setGravity(Gravity.CENTER).setExpanded(true, 1200).create();

                View data = dialogPlus.getHolderView();

                ImageView imageProof = data.findViewById(R.id.iv_adminProof);
                Glide.with(imageProof.getContext())
                        .load(model.getImageProof())
                        .into(imageProof);

                dialogPlus.show();
            }
        });
        holder.acctActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String activate = "true";

                Map<String,Object> map = new HashMap<>();
                map.put("activate", activate);
                FirebaseDatabase.getInstance().getReference().child("users").child(getRef(position).getKey()).updateChildren(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(holder.FullName.getContext(), "Successfully Activated Account", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(holder.FullName.getContext(), "Failed to Activate Account", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_admin_enable,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        TextView FullName, EmailAddress, activate;

        Button showImage, acctActivate;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            FullName = (TextView)itemView.findViewById(R.id.tv_aName);
            EmailAddress = (TextView)itemView.findViewById(R.id.tv_aEmail);
            activate = (TextView)itemView.findViewById(R.id.tv_aStatus);

            showImage = itemView.findViewById(R.id.btn_showImage);

            acctActivate = (Button)itemView.findViewById(R.id.btn_acctActivate);
        }
    }
}
