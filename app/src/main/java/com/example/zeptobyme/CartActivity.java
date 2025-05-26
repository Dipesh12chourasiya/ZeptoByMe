package com.example.zeptobyme;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvTotalPrice;
    private Button btnCheckout;
    private CartAdapter cartAdapter;
    private List<Product> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_list);
        tvTotalPrice = findViewById(R.id.total_price);
        btnCheckout = findViewById(R.id.btn_checkout);

        CartManager.getInstance().loadCartFromPreferences(this); // getting cart items from sharedprefrenses

        cartItems = CartManager.getInstance().getCartItems();
        cartAdapter = new CartAdapter(this, cartItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartAdapter);

        int totalAmount = calculateTotal(); // Calculate total once and store it


        btnCheckout.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(CartActivity.this, "Your cart is empty. Add products to continue.", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                intent.putExtra("TOTAL_AMOUNT", totalAmount);
                startActivity(intent);
            }
        });
    }

    private int calculateTotal() {
        int total = 0;
        for (Product product : cartItems) {
            total += Integer.parseInt(product.getPrice().replace("₹", ""));
        }
        tvTotalPrice.setText("Total: ₹" + total);
        return total;
    }
}