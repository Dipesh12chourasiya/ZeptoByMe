package com.example.zeptobyme.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zeptobyme.CartManager;
import com.example.zeptobyme.R;
import com.example.zeptobyme.models.Product;


public class ProductDetails extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName, productPrice, productMRP, productQuantity, productDesc;
    private Button addToCartBtn;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        productMRP = findViewById(R.id.product_mrp);
        productQuantity = findViewById(R.id.product_quantity);
        productDesc = findViewById(R.id.product_description);
        addToCartBtn = findViewById(R.id.btn_add_to_cart);

        product = (Product) getIntent().getSerializableExtra("product");

        if (product != null) {
            productImage.setImageResource(product.getImageResId());
            productName.setText(product.getName());
            productPrice.setText("Price: " + product.getPrice());
            productMRP.setText(product.getMrp());
            productQuantity.setText("Quantity: " + product.getQuantity());
            productDesc.setText("This is a great product!"); // Static for now
        }

        addToCartBtn.setOnClickListener(v -> {
            CartManager.getInstance().addToCart(product);
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
        });
    }
}
