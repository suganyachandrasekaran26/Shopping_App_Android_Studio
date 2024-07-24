package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class account extends AppCompatActivity {
    private TextView welcomeText;
    private Button editProfileButton;
    private Button cartButton;
    private Button orderHistoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Initialize views

        welcomeText = findViewById(R.id.txtview2);
        editProfileButton = findViewById(R.id.editProfileButton);
        cartButton = findViewById(R.id.cartButton);
        orderHistoryButton = findViewById(R.id.orderHistoryButton);

        // Get the username from the intent (you should pass the username from the previous activity)
        String username = getIntent().getStringExtra("username");
        String username1 = getIntent().getStringExtra("username");
        String userEmail = getIntent().getStringExtra("userEmail");
        TextView text=findViewById(R.id.txtview2) ;
        text.setText("Welcome "+username1+" !! ") ;
        // Display the welcome message with the username// welcomeText.setText("Welcome, " + username + "!");

        // Set click listeners for the buttons
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(account.this, EditProfileActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("email", userEmail);
                startActivity(intent);
            }
        });

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(account.this,cart.class);
                startActivity(intent);

            }
        });

        orderHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the Order History button click
                // You can navigate to the Order History activity here
                // Example: startActivity(new Intent(AccountOptionsActivity.this, OrderHistoryActivity.class));
            }
        });
    }
    public void onBack(View view){
        Intent intent = new Intent(account.this, MainActivity3.class);
        startActivity(intent);
    }
}