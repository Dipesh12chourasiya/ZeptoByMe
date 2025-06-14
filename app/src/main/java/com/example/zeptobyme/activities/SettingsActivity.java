package com.example.zeptobyme.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.zeptobyme.R;

//This screen is Settings page in  app. It allows the user to:
//        Toggle between Light & Dark Mode (using a switch)
// Navigate to their Saved Addresses
// Navigate to their Past Orders
public class SettingsActivity extends AppCompatActivity {
    private Switch themeToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


//        Before loading the UI, the app checks the saved preference from SharedPreferences:
//        If dark_mode = true, it applies dark theme.
//                Else, it applies light theme.
        // i have set theme based on saved preference before super.onCreate
        if (getSharedPreferences("settings", MODE_PRIVATE).getBoolean("dark_mode", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings); // or your layout

        themeToggle = findViewById(R.id.themeToggle);

        // setting initial state
        boolean isDark = getSharedPreferences("settings", MODE_PRIVATE).getBoolean("dark_mode", false);
        themeToggle.setChecked(isDark);

//        When the switch is toggled:
//        Saves the new preference.
//                Changes the app’s theme immediately (no restart needed).
        themeToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            getSharedPreferences("settings", MODE_PRIVATE)
                    .edit()
                    .putBoolean("dark_mode", isChecked)
                    .apply();

            AppCompatDelegate.setDefaultNightMode(
                    isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
            );
        });

      // this Will let us navigate to Address fragmnet
        LinearLayout llAddress = findViewById(R.id.llAddress);
        llAddress.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddressActivity.class);
            startActivity(intent);
        });


        LinearLayout llOrders = findViewById(R.id.llOrders);

        llOrders.setOnClickListener(v ->{
            Intent intent = new Intent(this, MyOrdersActivity.class);
            startActivity(intent);
        });

//        llOrders.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SettingsActivity.this, MyOrdersActivity.class);
//                startActivity(intent);
//            }
//        });

    }
}
