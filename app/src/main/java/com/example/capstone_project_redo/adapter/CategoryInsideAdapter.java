package com.example.capstone_project_redo.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capstone_project_redo.R;
import com.example.capstone_project_redo.model.CategoryInsideModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;

public class CategoryInsideAdapter extends FirebaseRecyclerAdapter<CategoryInsideModel, CategoryInsideAdapter.foodViewHolder> {

    private ArrayList<CategoryInsideModel> mFood = new ArrayList<>();
    private OnProductListener mOnProductListener;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CategoryInsideAdapter(@NonNull OnProductListener mOnFoodListener, FirebaseRecyclerOptions<CategoryInsideModel> options) {
        super(options);
        this.notifyDataSetChanged();
        this.mOnProductListener = mOnFoodListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull foodViewHolder holder, int position, @NonNull CategoryInsideModel model) {
        holder.name.setText(model.getName());
        holder.seller.setText(model.getSeller());
        holder.price.setText(model.getPrice()+" "+model.getPriceExtension());

        Glide.with(holder.imageUrl.getContext())
                .load(model.getImageUrl())
                .into(holder.imageUrl);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.imageUrl.getContext())
                        .setContentHolder(new ViewHolder(R.layout.viewholder_product_data))
                        .setGravity(Gravity.CENTER).create();

                View data = dialogPlus.getHolderView();

                TextView name = data.findViewById(R.id.tv_productName);
                TextView category = data.findViewById(R.id.tv_productCategory);
                TextView desc = data.findViewById(R.id.tv_productDesc);
                TextView seller = data.findViewById(R.id.tv_productSeller);
                TextView price = data.findViewById(R.id.tv_productPrice);
                ImageView product = data.findViewById(R.id.iv_productImage);

                name.setText(model.getName());
                category.setText(model.getCategory());
                desc.setText(model.getDescription());
                seller.setText(model.getSeller());
                price.setText(model.getPrice()+" "+model.getPriceExtension());
                Glide.with(product.getContext())
                        .load(model.getImageUrl())
                        .into(product);

                dialogPlus.show();
            }
        });
    }

    @NonNull
    @Override
    public foodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_inside_category, parent, false);
        return new foodViewHolder(view, mOnProductListener);
    }


    public class foodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageUrl;
        TextView name, seller, price;
        OnProductListener onProductListener;

        public foodViewHolder(@NonNull View itemView, OnProductListener onProductListener) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.nametext);
            seller = (TextView)itemView.findViewById(R.id.sellertext);
            price = (TextView)itemView.findViewById(R.id.pricetext);
            imageUrl = (ImageView)itemView.findViewById(R.id.img1);
            this.onProductListener = onProductListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onProductListener.onCategoryClick(getAbsoluteAdapterPosition());
        }
    }

    public interface OnProductListener {
        void onCategoryClick(int position);
    }
}
