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
import com.example.capstone_project_redo.model.HouseholdEssentialsModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class HouseholdEssentialsAdapter extends FirebaseRecyclerAdapter<HouseholdEssentialsModel, HouseholdEssentialsAdapter.householdEssentialsViewHolder> {

    private ArrayList<HouseholdEssentialsModel> mHousehold = new ArrayList<>();
    private OnHouseholdEssentialsListener mOnHouseholdEssentialsListener;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public HouseholdEssentialsAdapter(@NonNull OnHouseholdEssentialsListener mOnHouseholdEssentialsListener, FirebaseRecyclerOptions<HouseholdEssentialsModel> options) {
        super(options);
        this.mOnHouseholdEssentialsListener = mOnHouseholdEssentialsListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull householdEssentialsViewHolder holder, int position, @NonNull HouseholdEssentialsModel model) {
        holder.name.setText(model.getName());
        holder.seller.setText(model.getSeller());
        holder.price.setText(model.getPrice());

        Glide.with(holder.imageUrl.getContext())
                .load(model.getImageUrl())
                .into(holder.imageUrl);
    }

    @NonNull
    @Override
    public householdEssentialsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_inside_category, parent, false);
        return new householdEssentialsViewHolder(view, mOnHouseholdEssentialsListener);
    }


    public class householdEssentialsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageUrl;
        TextView name, seller, price;
        OnHouseholdEssentialsListener onHouseholdEssentialsListener;

        public householdEssentialsViewHolder(@NonNull View itemView, OnHouseholdEssentialsListener onHouseholdEssentialsListener) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.tv_craftedGoodsName);
            seller = (TextView)itemView.findViewById(R.id.tv_craftedGoodsSeller);
            price = (TextView)itemView.findViewById(R.id.tv_craftedGoodsPrice);
            imageUrl = (ImageView)itemView.findViewById(R.id.iv_craftedGoodsImage);
            this.onHouseholdEssentialsListener = onHouseholdEssentialsListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onHouseholdEssentialsListener.onCategoryClick(getAbsoluteAdapterPosition());
        }
    }

    public interface OnHouseholdEssentialsListener {
        void onCategoryClick(int position);
    }
}
