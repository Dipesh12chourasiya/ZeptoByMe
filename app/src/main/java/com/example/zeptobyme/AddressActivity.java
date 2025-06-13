package com.example.zeptobyme;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddressActivity extends AppCompatActivity {

    private EditText etAddress;
    private Button btnSave, btnDelete;
    private SharedPreferences preferences;
    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_ADDRESS = "user_address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        etAddress = findViewById(R.id.etAddress);
        btnSave = findViewById(R.id.btnSaveAddress);
        btnDelete = findViewById(R.id.btnDeleteAddress);

        preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Load existing address if any
        String savedAddress = preferences.getString(KEY_ADDRESS, "");
        etAddress.setText(savedAddress);

        btnSave.setOnClickListener(v -> {
            String newAddress = etAddress.getText().toString();
            preferences.edit().putString(KEY_ADDRESS, newAddress).apply();
            Toast.makeText(this, "Address saved", Toast.LENGTH_SHORT).show();
            finish();
        });

        btnDelete.setOnClickListener(v -> {
            preferences.edit().remove(KEY_ADDRESS).apply();
            etAddress.setText("");
            Toast.makeText(this, "Address deleted", Toast.LENGTH_SHORT).show();
        });
    }
}
