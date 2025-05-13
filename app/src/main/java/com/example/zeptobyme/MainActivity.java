package com.example.zeptobyme;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public EditText phoneInput;
    public Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isDark = getSharedPreferences("settings", MODE_PRIVATE).getBoolean("dark_mode", false);
        AppCompatDelegate.setDefaultNightMode(
                isDark ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );

        phoneInput = findViewById(R.id.phoneInput);
        continueButton = findViewById(R.id.continueButton);

        TextView termsText = findViewById(R.id.termText);
        String fullText = "By continuing, you agree to our \n Terms of Use & Privacy Policy";
        SpannableString spannable = new SpannableString(fullText);


        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#FF7050")),
                32, fullText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        termsText.setText(spannable);


        continueButton.setOnClickListener(v -> {

            String phoneNumber = phoneInput.getText().toString().trim();

            if (TextUtils.isEmpty(phoneNumber)) {

                Toast.makeText(MainActivity.this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
            } else if (phoneNumber.length() < 10) {

                Toast.makeText(MainActivity.this, "Phone number must be at least 10 digits", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(MainActivity.this, "Phone number entered: " + phoneNumber, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, ProductPage.class);
                intent.putExtra("phone_number", phoneNumber);
                startActivity(intent);
            }
        });
    }
}