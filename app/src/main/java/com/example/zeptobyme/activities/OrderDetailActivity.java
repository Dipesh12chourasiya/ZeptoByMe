package com.example.zeptobyme.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zeptobyme.models.Order;
import com.example.zeptobyme.models.Product;
import com.example.zeptobyme.R;
import com.example.zeptobyme.adapters.ProductAdapter;

public class OrderDetailActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_ADDRESS = "user_address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order_detail);

        Order order = (Order) getIntent().getSerializableExtra("order");

        preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String Address = preferences.getString(KEY_ADDRESS, "");

        TextView tvOrderId = findViewById(R.id.tvOrderId);
        TextView tvOrderDate = findViewById(R.id.tvOrderDate);
        TextView tvDeliveryAddress = findViewById(R.id.tvDeliveryAddress);
        TextView tvOrderTotal = findViewById(R.id.tvOrderTotal);
        TextView tvTrackingStatus = findViewById(R.id.tvTrackingStatus);
        RecyclerView rvOrderItems = findViewById(R.id.rvOrderItems);

        tvOrderId.setText("Order ID: " + order.getOrderId());
        tvOrderDate.setText("Date: " + order.getOrderDate());
        tvDeliveryAddress.setText("Address: " + Address);

        Double totalPrice = 0.0;

        for(Product product: order.getProductList()){
            String priceStr = product.getPrice();

            // Ensure it's in the right format
            if (priceStr != null && priceStr.length() > 1) {
                try {
                    double price = Double.parseDouble(priceStr.substring(1)); // remove 'R'
                    totalPrice += price;
                } catch (NumberFormatException e) {
                    e.printStackTrace(); // or log the error
                }
            }
        }

        tvOrderTotal.setText("Total: ₹" + totalPrice);
        tvTrackingStatus.setText("Status: " + order.getStatus());

        // Setup RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvOrderItems.setLayoutManager(layoutManager);
        rvOrderItems.setAdapter(new ProductAdapter(this ,order.getProductList()));
    }
}