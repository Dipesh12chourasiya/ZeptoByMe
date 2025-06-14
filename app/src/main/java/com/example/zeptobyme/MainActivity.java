package com.example.zeptobyme;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.zeptobyme.activities.ProductPage;

//This activity acts like a login/start screen where the user:
//Enters their phone number.
//Taps "Continue" btn.
//Gets redirected to the ProductPage screen with their phone number.
public class MainActivity extends AppCompatActivity {

    public EditText phoneInput;
    public Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Runtime permission for notification
//        Android 13 (API 33) and above require runtime permission for posting notifications.
//        This code checks and asks for permission if not granted.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }


//        Checks if the user wants dark mode from SharedPreferences.
//        Applies that setting using AppCompatDelegate.
        boolean isDark = getSharedPreferences("settings", MODE_PRIVATE).getBoolean("dark_mode", false);
        AppCompatDelegate.setDefaultNightMode(
                isDark ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );

        phoneInput = findViewById(R.id.phoneInput);
        continueButton = findViewById(R.id.continueButton);

//        Sets a styled message in the TextView.
//        Highlights "Terms of Use & Privacy Policy" in orange (#FF7050).
        TextView termsText = findViewById(R.id.termText);
        String fullText = "By continuing, you agree to our \n Terms of Use & Privacy Policy";
        SpannableString spannable = new SpannableString(fullText);

        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#FF7050")),
                32, fullText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        termsText.setText(spannable);


//        When the button is clicked:
//        Checks if the phone number is empty.
//                Ensures it’s at least 10 digits long.
//                If valid:
//        Shows a toast.
//                Sends the phone number to ProductPage activity using Intent.
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