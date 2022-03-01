package com.example.capstone_project_redo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capstone_project_redo.R;
import com.example.capstone_project_redo.model.MyListModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

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
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MyListModel model) {
        holder.name.setText(model.getName());
        holder.category.setText(model.getCategory());
        holder.price.setText(model.getPrice());
        holder.priceExtension.setText(model.getPriceExtension());

        Glide.with(holder.imageUrl.getContext())
                .load(model.getImageUrl())
                .into(holder.imageUrl);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_myproducts,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        ImageView imageUrl;
        TextView name, category, price, priceExtension;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.tv_myProductName);
            category = (TextView)itemView.findViewById(R.id.tv_myProductCategory);
            price = (TextView)itemView.findViewById(R.id.tv_myProductPrice);
            priceExtension = (TextView)itemView.findViewById(R.id.tv_myProductPriceEx);
            imageUrl = (ImageView)itemView.findViewById(R.id.iv_myProductImage);
        }
    }
}
