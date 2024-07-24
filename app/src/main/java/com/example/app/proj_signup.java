package com.example.app;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class proj_signup extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    private Button signUpButton;
    private TextView loginLinkTextView;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proj_signup);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        emailEditText = findViewById(R.id.emailEditText);
        signUpButton = findViewById(R.id.signUpButton);
        loginLinkTextView = findViewById(R.id.loginLinkTextView);

        databaseHelper = new DatabaseHelper(this);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String email = emailEditText.getText().toString();

                if (!username.isEmpty() && !password.isEmpty() && !email.isEmpty()) {
                    // Add the username and password to the "users" table.
                    databaseHelper.addUser(username, password);
                    // Add the email to the "signup_details" table.
                    databaseHelper.addSignupDetails(username, email, null, null);
                    Toast.makeText(proj_signup.this, "Registration successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(proj_signup.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });


        loginLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(proj_signup.this, MainActivity.class));
            }
        });
    }
}