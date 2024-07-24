package com.example.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class payment  extends AppCompatActivity {

    private RadioGroup paymentOptionsRadioGroup;
    private RadioButton creditCardRadioButton, debitCardRadioButton, upiRadioButton;
    private EditText cardNumberEditText, upiIdEditText;
    private Button payButton;
    private TextView paymentResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment );

        paymentOptionsRadioGroup = findViewById(R.id.paymentOptionsRadioGroup);
        creditCardRadioButton = findViewById(R.id.creditCardRadioButton);
        debitCardRadioButton = findViewById(R.id.debitCardRadioButton);
        upiRadioButton = findViewById(R.id.upiRadioButton);
        cardNumberEditText = findViewById(R.id.cardNumberEditText);
        upiIdEditText = findViewById(R.id.upiIdEditText);
        payButton = findViewById(R.id.payButton);
        paymentResultTextView = findViewById(R.id.paymentResultTextView);

        paymentOptionsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                cardNumberEditText.setVisibility(View.GONE);
                upiIdEditText.setVisibility(View.GONE);

                if (checkedId == creditCardRadioButton.getId()) {
                    cardNumberEditText.setVisibility(View.VISIBLE);
                } else if (checkedId == debitCardRadioButton.getId()) {
                    cardNumberEditText.setVisibility(View.VISIBLE);
                } else if (checkedId == upiRadioButton.getId()) {
                    upiIdEditText.setVisibility(View.VISIBLE);
                }
            }
        });

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String paymentResult = "";

                if (creditCardRadioButton.isChecked() || debitCardRadioButton.isChecked()) {
                    // Retrieve the card number and process the payment
                    String cardNumber = cardNumberEditText.getText().toString();
                    paymentResult = processCardPayment(cardNumber);
                } else if (upiRadioButton.isChecked()) {
                    // Retrieve the UPI ID and process the UPI payment
                    String upiId = upiIdEditText.getText().toString();
                    paymentResult = processUpiPayment(upiId);
                }

                paymentResultTextView.setText(paymentResult);
            }
        });
    }

    private String processCardPayment(String cardNumber) {
        // Implement your credit/debit card payment processing logic here
        if (cardNumber.isEmpty()) {
            return "Please enter a card number.";
        } else {
            // Simulate a successful payment for demonstration purposes
            return "Card Payment successful for card number: " + cardNumber;
        }
    }

    private String processUpiPayment(String upiId) {
        // Implement your UPI payment processing logic here
        if (upiId.isEmpty()) {
            return "Please enter a UPI ID.";
        } else {
            // Simulate a successful UPI payment for demonstration purposes
            return "UPI Payment successful for UPI ID: " + upiId;
        }
    }
}