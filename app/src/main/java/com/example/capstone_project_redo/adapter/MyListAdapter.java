package com.example.capstone_project_redo.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capstone_project_redo.R;
import com.example.capstone_project_redo.model.MyListModel;
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

public class MyListAdapter extends FirebaseRecyclerAdapter<MyListModel, MyListAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyListAdapter(@NonNull FirebaseRecyclerOptions<MyListModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull MyListModel model) {
        holder.name.setText(model.getName());
        holder.category.setText(model.getCategory());
        holder.price.setText(model.getPrice());
        holder.priceExtension.setText(model.getPriceExtension());
        holder.description.setText(model.getDescription());

        Glide.with(holder.imageUrl.getContext())
                .load(model.getImageUrl())
                .into(holder.imageUrl);

        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        holder.showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.description.getVisibility() == view.GONE) {
                    holder.description.setVisibility(view.VISIBLE);
                    holder.showMore.setText("Show Less");
                }
                else {
                    holder.description.setVisibility(view.GONE);
                    holder.showMore.setText("Show More");
                }
            }
        });

        holder.productEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.imageUrl.getContext())
                        .setContentHolder(new ViewHolder(R.layout.activity_edit_product))
                        .create();

                View data = dialogPlus.getHolderView();

                EditText name = data.findViewById(R.id.et_eProductName);
                EditText desc = data.findViewById(R.id.et_eProductDescription);
                EditText price = data.findViewById(R.id.et_eProductPrice);
                EditText priceEx = data.findViewById(R.id.et_eProductPriceEx);

                Button update = data.findViewById(R.id.btn_updateProduct);

                name.setText(model.getName());
                desc.setText(model.getDescription());
                price.setText(model.getPrice());
                priceEx.setText(model.getPriceExtension());

                dialogPlus.show();

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("name", name.getText().toString());
                        map.put("description", desc.getText().toString());
                        map.put("price", price.getText().toString());
                        map.put("priceExtension", priceEx.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("products").child(currentUser).child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        FirebaseDatabase.getInstance().getReference().child("categories").child(getRef(position).getKey()).updateChildren(map)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(holder.name.getContext(), "Product Data Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                                        dialogPlus.dismiss();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(holder.name.getContext(), "Failed to Update Product Data", Toast.LENGTH_SHORT).show();
                                                        dialogPlus.dismiss();
                                                    }
                                                });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.name.getContext(), "Failed to Update Product Data", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });

        holder.productDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted Data Cannot be Retrieved");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("products").child(currentUser)
                                .child(getRef(position).getKey()).removeValue();

                        FirebaseDatabase.getInstance().getReference().child("categories")
                                .child(getRef(position).getKey()).removeValue();

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.name.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_myproducts,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        ImageView imageUrl;
        TextView name, category, price, priceExtension, description;

        Button productEdit, productDelete, showMore;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.tv_myProductName);
            category = (TextView)itemView.findViewById(R.id.tv_myProductCategory);
            price = (TextView)itemView.findViewById(R.id.tv_myProductPrice);
            priceExtension = (TextView)itemView.findViewById(R.id.tv_myProductPriceEx);
            description = (TextView)itemView.findViewById(R.id.tv_myProductDesc);
            description.setVisibility(View.GONE);
            imageUrl = (ImageView)itemView.findViewById(R.id.iv_myProductImage);

            showMore = itemView.findViewById(R.id.btn_showMore);

            productEdit = (Button)itemView.findViewById(R.id.btn_productEdit);
            productDelete = (Button)itemView.findViewById(R.id.btn_productDelete);
        }
    }
}
