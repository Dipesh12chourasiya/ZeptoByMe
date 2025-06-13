package com.example.zeptobyme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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

}