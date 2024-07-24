package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {
    EditText nameEditText, emailEditText, phoneEditText, addressEditText;
    Button saveButton,backButton;
    String username; // Store the username
    DatabaseHelper databaseHelper; // Reuse the database helper instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        databaseHelper = new DatabaseHelper(this); // Initialize the database helper once

        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        addressEditText = findViewById(R.id.addressEditText);
        saveButton = findViewById(R.id.saveButton);
        backButton=findViewById(R.id.backButton);
        // Retrieve the username passed from MainActivity
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        // Set the username as default text in the corresponding EditText field
        nameEditText.setText(username);

        // Retrieve the email from the database for the logged-in user
        String email = databaseHelper.getEmailForUsername(username);
        emailEditText.setText(email);

        // Retrieve and populate phone number and address
        final String savedPhoneNumber = databaseHelper.getPhoneNumber(username);
        final String savedAddress = databaseHelper.getAddress(username);

        if (savedPhoneNumber != null) {
            phoneEditText.setText(savedPhoneNumber);
        }

        if (savedAddress != null) {
            addressEditText.setText(savedAddress);
        }

        // Set a click listener for the "Save" button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the values from the EditText fields
                String newPhoneNumber = phoneEditText.getText().toString();
                String newAddress = addressEditText.getText().toString();

                // Update the user's phone number and address in the database
                databaseHelper.updatePhoneNumber(username, newPhoneNumber);
                databaseHelper.updateAddress(username, newAddress);

                // Display a message upon saving
                Toast.makeText(EditProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();

                // Update the displayed fields directly after saving
                phoneEditText.setText(newPhoneNumber);
                addressEditText.setText(newAddress);
            }
        });
    }
    public void onBack(View view){
        Intent intent = new Intent(EditProfileActivity.this, account.class);
        //intent.putExtra("username", username);
        startActivity(intent);
    }
}