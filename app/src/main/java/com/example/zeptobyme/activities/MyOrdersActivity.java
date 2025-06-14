package com.example.zeptobyme.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zeptobyme.models.Order;
import com.example.zeptobyme.OrderManager;
import com.example.zeptobyme.R;
import com.example.zeptobyme.adapters.OrderAdapter;

import java.util.List;

public class MyOrdersActivity extends AppCompatActivity {

    private RecyclerView rvOrders;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        rvOrders = findViewById(R.id.rvOrders);
        rvOrders.setLayoutManager(new LinearLayoutManager(this));

        orderList = OrderManager.loadOrders(this); // Loads from SharedPreferences
        orderAdapter = new OrderAdapter(this, orderList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        rvOrders.setLayoutManager(layoutManager);
        rvOrders.setAdapter(orderAdapter);
    }
}
