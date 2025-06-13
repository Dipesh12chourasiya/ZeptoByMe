package com.example.zeptobyme;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        rvOrderItems.setLayoutManager(new LinearLayoutManager(this));
        rvOrderItems.setAdapter(new ProductAdapter(this ,order.getProductList()));
    }
}