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
import com.example.capstone_project_redo.model.CraftedGoodsModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class CraftedGoodsAdapter extends FirebaseRecyclerAdapter<CraftedGoodsModel, CraftedGoodsAdapter.craftedGoodsViewHolder> {

    private ArrayList<CraftedGoodsModel> mCraft = new ArrayList<>();
    private OnCraftedGoodsListener mOnCraftedGoodsListener;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CraftedGoodsAdapter(@NonNull OnCraftedGoodsListener mOnCraftedGoodsListener, FirebaseRecyclerOptions<CraftedGoodsModel> options) {
        super(options);
        this.mOnCraftedGoodsListener = mOnCraftedGoodsListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull craftedGoodsViewHolder holder, int position, @NonNull CraftedGoodsModel model) {
        holder.name.setText(model.getName());
        holder.seller.setText(model.getSeller());
        holder.price.setText(model.getPrice());

        Glide.with(holder.imageUrl.getContext())
                .load(model.getImageUrl())
                .into(holder.imageUrl);
    }

    @NonNull
    @Override
    public craftedGoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_inside_category, parent, false);
        return new craftedGoodsViewHolder(view, mOnCraftedGoodsListener);
    }


    public class craftedGoodsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageUrl;
        TextView name, seller, price;
        OnCraftedGoodsListener onCraftedGoodsListener;

        public craftedGoodsViewHolder(@NonNull View itemView, OnCraftedGoodsListener onCraftedGoodsListener) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.tv_craftedGoodsName);
            seller = (TextView)itemView.findViewById(R.id.tv_craftedGoodsSeller);
            price = (TextView)itemView.findViewById(R.id.tv_craftedGoodsPrice);
            imageUrl = (ImageView)itemView.findViewById(R.id.iv_craftedGoodsImage);
            this.onCraftedGoodsListener = onCraftedGoodsListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onCraftedGoodsListener.onCategoryClick(getAbsoluteAdapterPosition());
        }
    }

    public interface OnCraftedGoodsListener {
        void onCategoryClick(int position);
    }
}
