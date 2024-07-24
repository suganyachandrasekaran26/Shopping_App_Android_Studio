package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class cart extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        dbHelper = new DatabaseHelper(this);
        // Reference to the container layout where cart items will be added
        LinearLayout cartContainer = findViewById(R.id.productcartListLayout);

        // Get all products in the cart
        List<Product> cartProducts = dbHelper.getAllCartProducts();
        if (cartProducts.isEmpty()) {
            // Display a message when there are no products in the cart
            TextView noProductsTextView = new TextView(this);
            noProductsTextView.setText("No products in the cart");
            noProductsTextView.setTextSize(18);
            noProductsTextView.setGravity(Gravity.CENTER);
            cartContainer.addView(noProductsTextView);
        } else {
            // Inflate and add cart item views dynamically
            LayoutInflater inflater = LayoutInflater.from(this);
            for (Product cartProduct : cartProducts) {
                View cartItemView = inflater.inflate(R.layout.cartcontainer, null);

                ImageView cartItemImageView = cartItemView.findViewById(R.id.productImageView);
                TextView cartItemNameTextView = cartItemView.findViewById(R.id.productNameTextView);
                TextView cartItemPriceTextView = cartItemView.findViewById(R.id.productPriceTextView);
                TextView cartItemDiscountTextView = cartItemView.findViewById(R.id.productDiscountTextView);
                Button removeFromCartButton = cartItemView.findViewById(R.id.removeFromCartButton);

                // Populate views with cart product details
                cartItemImageView.setImageResource(getResources().getIdentifier(cartProduct.getImage(), "drawable", getPackageName()));
                cartItemNameTextView.setText(cartProduct.getName());
                cartItemPriceTextView.setText("Price: $" + cartProduct.getPrice());
                cartItemDiscountTextView.setText("Discount: " + cartProduct.getDiscount() + "%");

                // Set a click listener for the remove button
                removeFromCartButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Implement the logic to remove the product from the cart
                        dbHelper.removeFromCart(cartProduct.getId());
                        // Remove the cart item view from the container
                        cartContainer.removeView(cartItemView);
                    }
                });

                // Add the cart item view to the container
                cartContainer.addView(cartItemView);
            }
        }

    }
}