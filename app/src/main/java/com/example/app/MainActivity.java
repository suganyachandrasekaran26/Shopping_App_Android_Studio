package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView signUpLinkTextView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        signUpLinkTextView = findViewById(R.id.signUpLinkTextView);
        databaseHelper = new DatabaseHelper(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (!username.isEmpty() && !password.isEmpty()) {
                    // In this version, we'll directly check the user's password.
                    String storedPassword = databaseHelper.getPasswordForUsername(username);

                    if (storedPassword != null && storedPassword.equals(password)) {
                        String userEmail = databaseHelper.getEmailForUsername(username);
                        Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                        intent.putExtra("username", username);
                        intent.putExtra("email", userEmail);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Login failed. Invalid credentials.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUpLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, proj_signup.class));
            }
        });
    }
}
