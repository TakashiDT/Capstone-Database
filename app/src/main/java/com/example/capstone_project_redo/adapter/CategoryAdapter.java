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
import com.example.capstone_project_redo.model.CategoryModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class CategoryAdapter extends FirebaseRecyclerAdapter<CategoryModel, CategoryAdapter.categoryViewHolder> {

    private ArrayList<CategoryModel> mCategory = new ArrayList<>();
    private OnCategoryListener mOnCategoryListener;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CategoryAdapter(@NonNull OnCategoryListener mOnCategoryListener, FirebaseRecyclerOptions<CategoryModel> options) {
        super(options);
        this.mOnCategoryListener = mOnCategoryListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull categoryViewHolder holder, final int position, @NonNull CategoryModel model) {
        holder.name.setText(model.getName());

        Glide.with(holder.imageUrl.getContext())
                .load(model.getImageUrl())
                .into(holder.imageUrl);
    }

    @NonNull
    @Override
    public categoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);
        return new categoryViewHolder(view, mOnCategoryListener);
    }


    public class categoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageUrl;
        TextView name;
        OnCategoryListener onCategoryListener;

        public categoryViewHolder(@NonNull View itemView, OnCategoryListener onCategoryListener) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.tv_categoryName);
            imageUrl = (ImageView)itemView.findViewById(R.id.iv_categoryImage);
            this.onCategoryListener = onCategoryListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onCategoryListener.onCategoryClick(getAbsoluteAdapterPosition());
        }
    }

    public interface OnCategoryListener {
        void onCategoryClick(int position);
    }
}
