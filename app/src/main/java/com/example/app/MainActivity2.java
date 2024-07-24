package com.example.app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
public class MainActivity2 extends AppCompatActivity {
    private static final String CHANNEL_ID = "SIMPLE NOTIFICATION ID";
    private static final String CHANNEL_NAME = "SIMPLE_NOTIFICATION CH NAME";
    private static final String CHANNEL_DESC = "SIMPLE NOTIFICATION DESC";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2 );

        // Retrieve the product ID passed from the previous activity
        int productId = getIntent().getIntExtra("product_id", -1);

        // You can now fetch product details from the database based on the product ID
        // For this example, I'll assume you have a DatabaseHelper class to retrieve product details
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Product product = dbHelper.getProductById(productId);

        // Populate the TextViews with product details
        TextView productNameTextView = findViewById(R.id.productNameTextView);
        TextView productPriceTextView = findViewById(R.id.productPriceTextView);
        TextView productDescriptionTextView = findViewById(R.id.productDescription );
        TextView productBrandVTextView=findViewById(R.id.productBrandTextView );
        TextView productdiscountTextView=findViewById(R.id.productDiscountTextView);
        TextView productmanufacturerTextView=findViewById(R.id.productManufacturer);
        ImageView productImageView =findViewById(R.id.productImageView);



        if (product != null) {
            productNameTextView.setText(product.getName());
            productPriceTextView.setText("Price: $" + product.getPrice());
            productdiscountTextView .setText("discount :  "+ product.getDiscount());
            productDescriptionTextView.setText(product.getDescription());
            productBrandVTextView.setText(product.getBrand()) ;
            productmanufacturerTextView.setText(product.getManufacturer() ) ;

            Picasso.get().load(getResources().getIdentifier(product.getImage(), "drawable", getPackageName())).into(productImageView);
        }
        Button addtocart = findViewById(R.id.addtocart);
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add the product to the cart
                if (product != null) {
                    long cartItemId = dbHelper.addToCart(product.getId());

                    if (cartItemId != -1) {

                        Toast.makeText(MainActivity2.this, "Added to cart successfully", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(MainActivity2.this, "Failed to add to cart", Toast.LENGTH_SHORT).show();
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                    channel.setDescription(CHANNEL_DESC);
                    NotificationManager manager = getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel);
                }
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity2.this,CHANNEL_ID)
                        .setSmallIcon(R.drawable.notification_svgrepo_com )
                        .setContentTitle("Local Artisian Marketplace")
                        .setContentText("Added to cart succesfully "+product.getName() ).setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(MainActivity2.this);

                if (ActivityCompat.checkSelfPermission(MainActivity2.this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mNotificationMgr.notify(1, mBuilder.build());
            }
        });

    }
}
