package com.example.zeptobyme.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zeptobyme.CartManager;
import com.example.zeptobyme.models.Order;
import com.example.zeptobyme.OrderManager;
import com.example.zeptobyme.models.Product;
import com.example.zeptobyme.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class PaymentActivity extends AppCompatActivity {


    private int totalAmount;
    private TextView tvTotalAmount;
    private ImageView phonePayImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        phonePayImg = findViewById(R.id.phonePayImg);

        phonePayImg.setOnClickListener(v ->{
            Toast.makeText(this, "Fake payment!", Toast.LENGTH_SHORT).show();
            saveOrder();
        });

        tvTotalAmount = findViewById(R.id.total_amount_textview);

        totalAmount = getIntent().getIntExtra("TOTAL_AMOUNT", 0);
        tvTotalAmount.setText("Total Amount: ₹" + totalAmount);

//        The amount is passed from a previous screen via Intent.
//
//        It's displayed on the screen using a TextView.
    }

    public void payWithGPay(View view) {
        startUPIPayment("your_upi_id@okicici", "Google Pay");
    }

//    public void payWithPhonePe(View view) {
//        saveOrder();
//        startUPIPayment("6260646298@axl", "PhonePe");
//    }

    public void payWithPaytm(View view) {
        startUPIPayment("your_upi@ptsbi", "Paytm");
    }


    private void startUPIPayment(String upiId, String appName) {
        try {
            Uri uri = Uri.parse("upi://pay?pa=" + upiId +
                    "&pn=MerchantName" +
                    "&mc=0000" +
                    "&tid=021254" +
                    "&tr=123456789" +
                    "&tn=Payment" +
                    "&am=" + totalAmount +
                    "&cu=INR");

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, appName + " app not found", Toast.LENGTH_SHORT).show();
        }
    }


    private void saveOrder() {
        List<Product> cartItems = CartManager.getInstance().getCartItems();
        String orderId = UUID.randomUUID().toString();
        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        Order newOrder = new Order(orderId, cartItems, date, "Ordered");

        List<Order> existingOrders = OrderManager.loadOrders(this);
        existingOrders.add(newOrder);
        OrderManager.saveOrders(this, existingOrders);

        // Clear cart after order placed
        CartManager.getInstance().clearCart();

        Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
    }
//            🛒 Gets items from the cart.
//🆔 Generates a unique order ID using UUID.
//📅 Sets today’s date.
//            🗃️ Adds the order to existing orders (loaded from OrderManager).
//            💾 Saves all orders.
//🧹 Clears the cart.
//✅ Shows success message.

}