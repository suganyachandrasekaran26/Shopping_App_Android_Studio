package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    private List<Product> products;
    private LinearLayout productListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);
        String username = getIntent().getStringExtra("username");
        String userEmail = getIntent().getStringExtra("userEmail");
        Button acc=findViewById(R.id.accountButton ) ;
        acc.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
              Intent intent=new Intent(MainActivity3.this, account.class ) ;
                intent.putExtra("username", username);
                intent.putExtra("email", userEmail);
              startActivity(intent) ;
                 }
        });
        Button cart=findViewById(R.id.cartButton) ;
        cart.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity3.this, cart.class ) ;
                startActivity(intent) ;
            }

        });
        dbHelper.removeAllProducts();

        // Insert a product into the database
        long newRowId = dbHelper.addProduct(
                "Women's Handicrafted Vegan Leather Palolem Wave Braided Sandal",
                "Brand : Zouk",
                "image2",
                999,
                -63,
                "Manufacturer:SeaTurtle Pvt Ltd",
                "Product details:Sandals are a versatile and trendy go to for all your attire.Dainty and prettily designed with finely handicrafted braids to pair up with just anything everything"
        );
        long newRowId1 = dbHelper.addProduct(
                "Shanvi handcraft Women's Rajasthani Jaipuri Bohemain Art Tote Bag(Multicolor,Large)",
                "Brand : Generic",
                "img",
                599,
                69,
                "Manufacturer:Shanvi handicraft",
                "100% Comfortable Cotton Material Embroided with Resham Dori which is specially designed by leeRoy * Exterior compartments:1 Interior Compartments :1 * Theme:Fully embroided work");

        long newRowId2 = dbHelper.addProduct(
                " Macrame Keychain Boho Bag Charm with Tassels Bohemian Handmade Accessories for Car Key, Purse, Phone, Wallet ",
                "Brand : Generic",
                "pic1",
                810,
                42,
                "Manufacturer: Alex interior dkor ",
                "Closure: Lobster Clasp Size: Total Length is 14 cm, Width is 5 cm. Material: made cotton cord, ,featured with quality round clasp , macrame keychains is made by hand.Suitable occasions: exquisite ornaments are suitable for purses, keyrings, backpacks, coin wallet, phone case");


        long newRowId3 = dbHelper.addProduct(
                "Macrame Earring, Handmade Earring, Gift Earring ",
                "Brand : Generic",
                "pic",
                299,
                40,
                "Manufacturer: Alex interior dkor ",
                " Our unique thread macramé earrings make you a fashionable stylish look perfect for any time or any occasion.We can only imagine how beautiful, gorgeous, exceptional and extraordinary your looks are when you wear our unique thread macramé earrings. OUR QUALITY – our earrings are made with the best quality thread");



        products = dbHelper.getAllProducts();

        productListLayout = findViewById(R.id.productListLayout);
        EditText  searchEditText = findViewById(R.id.searchEditText);

        // Set up a TextWatcher to listen for changes in the search input
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not needed for this example
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Filter products based on the search query
                filterProducts(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Not needed for this example
            }
        });

        displayProducts(products);
    }

    private void displayProducts(List<Product> products) {
        productListLayout.removeAllViews();

        for (Product product : products) {
            // Create a new View to display the product details
            View productView = getLayoutInflater().inflate(R.layout.product_item, null);
            TextView productNameTextView = productView.findViewById(R.id.productNameTextView);
            TextView productPriceTextView = productView.findViewById(R.id.productPriceTextView);
            TextView productDiscountTextView = productView.findViewById(R.id.productDiscountTextView);
            ImageView productImageView = productView.findViewById(R.id.productImageView);
            Button viewDetailsButton = productView.findViewById(R.id.viewdetails);

            productNameTextView.setText(product.getName());
            productPriceTextView.setText("$" + product.getPrice());
            productDiscountTextView.setText("$" + product.getDiscount());

            int resourceId = getResources().getIdentifier(product.getImage(), "drawable", getPackageName());

            if (resourceId != 0) {
                // Resource ID is valid, so you can load the image
                Picasso.get().load(resourceId).into(productImageView);
            } else {
                // Handle the case where the resource doesn't exist or is invalid
                // For example:
                // productImageView.setImageResource(R.drawable.default_image);
                // Or display an error message
                // Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show();
            }

            viewDetailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity3.this, MainActivity2 .class);
                    intent.putExtra("product_id", product.getId());
                    startActivity(intent);
                }
            });

            productListLayout.addView(productView);
        }
    }

    private void filterProducts(String query) {
        List<Product> filteredProducts = new ArrayList<>();

        String[] searchTerms = query.toLowerCase().split("\\s+");

        for (Product product : products) {
            String productNameLower = product.getName().toLowerCase();
            boolean matchesAllTerms = true;

            for (String term : searchTerms) {
                if (!productNameLower.contains(term)) {
                    matchesAllTerms = false;
                    break;
                }
            }

            if (matchesAllTerms) {
                filteredProducts.add(product);
            }
        }

        displayProducts(filteredProducts);
    }
}

