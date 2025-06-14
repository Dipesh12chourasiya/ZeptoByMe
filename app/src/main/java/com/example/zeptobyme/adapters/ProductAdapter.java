package com.example.zeptobyme.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zeptobyme.CartManager;
import com.example.zeptobyme.models.Product;
import com.example.zeptobyme.activities.ProductDetails;
import com.example.zeptobyme.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context mContext;
    private List<Product> mProductList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.mContext = context;
        this.mProductList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = mProductList.get(position);

        holder.tvProductName.setText(product.getName());
        holder.tvProductPrice.setText(product.getPrice());
        holder.tvProductMrp.setText(product.getMrp());

        holder.tvProductMrp.setPaintFlags(holder.tvProductMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvQuantity.setText(product.getQuantity());
        holder.imageView.setImageResource(product.getImageResId());

        holder.btnAddToCart.setOnClickListener(v -> {
            holder.btnAddToCart.setVisibility(View.GONE);
            holder.cartContainer.setVisibility(View.VISIBLE);
        });

        holder.btnIncrease.setOnClickListener(v -> {
            int count = Integer.parseInt(holder.tvItemCount.getText().toString());
            count++;
            holder.tvItemCount.setText(String.valueOf(count));
        });

        holder.btnDecrease.setOnClickListener(v -> {
            int count = Integer.parseInt(holder.tvItemCount.getText().toString());
            if (count > 1) {
                count--;
                holder.tvItemCount.setText(String.valueOf(count));
            } else {
                // Remove from cart or hide UI
                holder.cartContainer.setVisibility(View.GONE);
                holder.btnAddToCart.setVisibility(View.VISIBLE);

                CartManager.getInstance().removeFromCart(product);
            }

            CartManager.getInstance().saveCartToPreferences(mContext); // Save any change
        });


        holder.btnAddToCart.setOnClickListener(v -> {

            CartManager.getInstance().addToCart(product);

            // Save updated cart to SharedPreferences
            CartManager.getInstance().saveCartToPreferences(mContext);

            holder.btnAddToCart.setVisibility(View.GONE);
            holder.cartContainer.setVisibility(View.VISIBLE);
        });


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ProductDetails.class);
            intent.putExtra("product", mProductList.get(position));
            mContext.startActivity(intent);
        });

    }


    @Override
    public int getItemCount() {
        return mProductList.size();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView tvProductName, tvProductPrice, tvProductMrp, tvQuantity, tvItemCount;
        ImageView imageView;
        Button btnAddToCart, btnIncrease, btnDecrease;
        LinearLayout cartContainer;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.product_name);
            tvProductPrice = itemView.findViewById(R.id.product_price);
            tvProductMrp = itemView.findViewById(R.id.product_mrp);
            tvQuantity = itemView.findViewById(R.id.quantity);
            imageView = itemView.findViewById(R.id.product_image);
            btnAddToCart = itemView.findViewById(R.id.add_to_cart);
            btnIncrease = itemView.findViewById(R.id.btn_increase);
            btnDecrease = itemView.findViewById(R.id.btn_decrease);
            tvItemCount = itemView.findViewById(R.id.item_count);
            cartContainer = itemView.findViewById(R.id.cart_container);
        }
    }
}
