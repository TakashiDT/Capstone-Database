package com.example.capstone_project_redo.forRecyclerViews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capstone_project_redo.InsideCategoryActivity;
import com.example.capstone_project_redo.R;
import com.example.capstone_project_redo.nav.CategoryActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class CategoryAdapter extends FirebaseRecyclerAdapter<CategoryModel, CategoryAdapter.categoryViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CategoryAdapter(@NonNull FirebaseRecyclerOptions<CategoryModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull categoryViewHolder holder, int position, @NonNull CategoryModel model) {
        holder.name.setText(model.getName());

        Glide.with(holder.imageUrl.getContext())
                .load(model.getImageUrl())
                .into(holder.imageUrl);
    }

    @NonNull
    @Override
    public categoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);
        return new categoryViewHolder(view);
    }


    public class categoryViewHolder extends RecyclerView.ViewHolder {

        ImageView imageUrl;
        TextView name;

        public categoryViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.tv_categoryName);
            imageUrl = (ImageView)itemView.findViewById(R.id.iv_categoryImage);

        }
    }
}
