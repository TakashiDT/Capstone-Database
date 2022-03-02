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
import com.example.capstone_project_redo.model.FoodModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class FoodAdapter extends FirebaseRecyclerAdapter<FoodModel, FoodAdapter.foodViewHolder> {

    private ArrayList<FoodModel> mFood = new ArrayList<>();
    private OnFoodListener mOnFoodListener;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FoodAdapter(@NonNull OnFoodListener mOnFoodListener, FirebaseRecyclerOptions<FoodModel> options) {
        super(options);
        this.notifyDataSetChanged();
        this.mOnFoodListener = mOnFoodListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull foodViewHolder holder, int position, @NonNull FoodModel model) {
        holder.name.setText(model.getName());
        holder.seller.setText(model.getSeller());
        holder.price.setText(model.getPrice());

        Glide.with(holder.imageUrl.getContext())
                .load(model.getImageUrl())
                .into(holder.imageUrl);
    }

    @NonNull
    @Override
    public foodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_inside_category, parent, false);
        return new foodViewHolder(view, mOnFoodListener);
    }


    public class foodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageUrl;
        TextView name, seller, price;
        OnFoodListener onFoodListener;

        public foodViewHolder(@NonNull View itemView, OnFoodListener onFoodListener) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.tv_craftedGoodsName);
            seller = (TextView)itemView.findViewById(R.id.tv_craftedGoodsSeller);
            price = (TextView)itemView.findViewById(R.id.tv_craftedGoodsPrice);
            imageUrl = (ImageView)itemView.findViewById(R.id.iv_craftedGoodsImage);
            this.onFoodListener = onFoodListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onFoodListener.onCategoryClick(getAbsoluteAdapterPosition());
        }
    }

    public interface OnFoodListener {
        void onCategoryClick(int position);
    }
}
