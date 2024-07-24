package com.example.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "UserDatabase";
    private static final String TABLE_CART = "cart";
    private static final String KEY_CART_ID = "cart_id";
    private static final String KEY_CART_PRODUCT_ID = "product_id";


    private static final String TABLE_PRODUCTS = "products";
    private static final String KEY_PRODUCT_ID = "id";
    private static final String KEY_PRODUCT_NAME = "name";
    private static final String KEY_PRODUCT_BRAND = "brand";
    private static final String KEY_PRODUCT_IMAGE = "image";
    private static final String KEY_PRODUCT_PRICE = "price";
    private static final String KEY_PRODUCT_DISCOUNT = "discount";
    private static final String KEY_PRODUCT_MANUFACTURER = "manufacturer";
    private static final String KEY_PRODUCT_DESCRIPTION = "description";


    private static final String TABLE_USERS = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String TABLE_SIGNUP_DETAILS = "signup_details";
    private static final String KEY_SIGNUP_ID = "id";
    private static final String KEY_SIGNUP_USERNAME = "username";
    private static final String KEY_SIGNUP_EMAIL = "email";
    private static final String KEY_SIGNUP_PHONE = "phone";
    private static final String KEY_SIGNUP_ADDRESS = "address";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_USERNAME + " TEXT,"
                + KEY_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_SIGNUP_DETAILS_TABLE = "CREATE TABLE " + TABLE_SIGNUP_DETAILS + "("
                + KEY_SIGNUP_ID + " INTEGER PRIMARY KEY,"
                + KEY_SIGNUP_USERNAME + " TEXT,"
                + KEY_SIGNUP_EMAIL + " TEXT,"
                + KEY_SIGNUP_PHONE + " TEXT,"
                + KEY_SIGNUP_ADDRESS + " TEXT,"
                + KEY_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_SIGNUP_DETAILS_TABLE);


        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + KEY_PRODUCT_ID + " INTEGER PRIMARY KEY,"
                + KEY_PRODUCT_NAME + " TEXT,"
                + KEY_PRODUCT_BRAND + " TEXT,"
                + KEY_PRODUCT_IMAGE + " TEXT,"
                + KEY_PRODUCT_PRICE + " REAL,"
                + KEY_PRODUCT_DISCOUNT + " REAL,"
                + KEY_PRODUCT_MANUFACTURER + " TEXT,"
                + KEY_PRODUCT_DESCRIPTION + " TEXT" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);

        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + "("
                + KEY_CART_ID + " INTEGER PRIMARY KEY,"
                + KEY_CART_PRODUCT_ID + " INTEGER" + ")";
        db.execSQL(CREATE_CART_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SIGNUP_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }
    public long addToCart(long productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CART_PRODUCT_ID, productId);
        long newRowId = db.insert(TABLE_CART, null, values);
        db.close();
        return newRowId;
    }


    public long addProduct(String name, String brand, String image, double price, double discount, String manufacturer, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_NAME, name);
        values.put(KEY_PRODUCT_BRAND, brand);
        values.put(KEY_PRODUCT_IMAGE, image);
        values.put(KEY_PRODUCT_PRICE, price);
        values.put(KEY_PRODUCT_DISCOUNT, discount);
        values.put(KEY_PRODUCT_MANUFACTURER, manufacturer);
        values.put(KEY_PRODUCT_DESCRIPTION, description);
        long newRowId = db.insert(TABLE_PRODUCTS, null, values);
        db.close();
        return newRowId;
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                KEY_PRODUCT_ID,
                KEY_PRODUCT_NAME,
                KEY_PRODUCT_BRAND,
                KEY_PRODUCT_IMAGE,
                KEY_PRODUCT_PRICE,
                KEY_PRODUCT_DISCOUNT,
                KEY_PRODUCT_MANUFACTURER,
                KEY_PRODUCT_DESCRIPTION
        };
        Cursor cursor = db.query(TABLE_PRODUCTS, columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(KEY_PRODUCT_ID);
                int nameIndex = cursor.getColumnIndex(KEY_PRODUCT_NAME);
                int brandIndex = cursor.getColumnIndex(KEY_PRODUCT_BRAND);
                int imageIndex = cursor.getColumnIndex(KEY_PRODUCT_IMAGE);
                int priceIndex = cursor.getColumnIndex(KEY_PRODUCT_PRICE);
                int discountIndex = cursor.getColumnIndex(KEY_PRODUCT_DISCOUNT);
                int manufacturerIndex = cursor.getColumnIndex(KEY_PRODUCT_MANUFACTURER);
                int descriptionIndex = cursor.getColumnIndex(KEY_PRODUCT_DESCRIPTION);

                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                String brand = cursor.getString(brandIndex);
                String image = cursor.getString(imageIndex);
                double price = cursor.getDouble(priceIndex);
                double discount = cursor.getDouble(discountIndex);
                String manufacturer = cursor.getString(manufacturerIndex);
                String description = cursor.getString(descriptionIndex);

                Product product = new Product(id,name, brand, image, price, discount, manufacturer, description);

                productList.add(product);
            } while (cursor.moveToNext());

            cursor.close();
        }


        db.close();
        return productList;
    }
    public List<Product> getAllCartProducts() {
        List<Product> cartProducts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] cartColumns = {
                KEY_CART_ID,
                KEY_CART_PRODUCT_ID
        };

        Cursor cartCursor = db.query(TABLE_CART, cartColumns, null, null, null, null, null);

        if (cartCursor != null && cartCursor.moveToFirst()) {
            do {
                int productIdIndex = cartCursor.getColumnIndex(KEY_CART_PRODUCT_ID);

                int productId = cartCursor.getInt(productIdIndex);

                // Use getProductById method to get product details
                Product product = getProductById(productId);

                if (product != null) {
                    cartProducts.add(product);
                }
            } while (cartCursor.moveToNext());

            cartCursor.close();
        }

        db.close();
        return cartProducts;
    }


    public Product getProductById(long productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Product product = null;

        String[] columns = {
                KEY_PRODUCT_ID,
                KEY_PRODUCT_NAME,
                KEY_PRODUCT_BRAND,
                KEY_PRODUCT_IMAGE,
                KEY_PRODUCT_PRICE,
                KEY_PRODUCT_DISCOUNT,
                KEY_PRODUCT_MANUFACTURER,
                KEY_PRODUCT_DESCRIPTION
        };
        String selection = KEY_PRODUCT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(productId)};

        Cursor cursor = db.query(TABLE_PRODUCTS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(KEY_PRODUCT_NAME);
            int brandIndex = cursor.getColumnIndex(KEY_PRODUCT_BRAND);
            int imageIndex = cursor.getColumnIndex(KEY_PRODUCT_IMAGE);
            int priceIndex = cursor.getColumnIndex(KEY_PRODUCT_PRICE);
            int discountIndex = cursor.getColumnIndex(KEY_PRODUCT_DISCOUNT);
            int manufacturerIndex = cursor.getColumnIndex(KEY_PRODUCT_MANUFACTURER);
            int descriptionIndex = cursor.getColumnIndex(KEY_PRODUCT_DESCRIPTION);
            int idIndex= cursor.getColumnIndex(KEY_PRODUCT_ID );
            int id= cursor.getInt(idIndex );
            String name = cursor.getString(nameIndex);
            String brand = cursor.getString(brandIndex);
            String image = cursor.getString(imageIndex);
            double price = cursor.getDouble(priceIndex);
            double discount = cursor.getDouble(discountIndex);
            String manufacturer = cursor.getString(manufacturerIndex);
            String description = cursor.getString(descriptionIndex);

            product = new Product(id ,name, brand, image, price, discount, manufacturer, description);

            cursor.close();
        }

        db.close();

        return product;
    }




    public void addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, username);
        values.put(KEY_PASSWORD, password);
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public UserLoginResult checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_SIGNUP_EMAIL};
        String selection = KEY_SIGNUP_USERNAME + " = ?" + " AND " + KEY_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_SIGNUP_DETAILS, columns, selection, selectionArgs, null, null, null);
        String email = null;
        boolean isSuccess = false;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int emailColumnIndex = cursor.getColumnIndex(KEY_SIGNUP_EMAIL);
                if (emailColumnIndex != -1) {
                    email = cursor.getString(emailColumnIndex);
                    isSuccess = true;
                }
                cursor.close();
            }
        }

        db.close();

        return new UserLoginResult(isSuccess, email);
    }

    public String getPasswordForUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_PASSWORD};
        String selection = KEY_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        String password = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int passwordColumnIndex = cursor.getColumnIndex(KEY_PASSWORD);
                if (passwordColumnIndex != -1) {
                    password = cursor.getString(passwordColumnIndex);
                }
                cursor.close();
            }
        }

        db.close();
        return password;
    }

    public void addSignupDetails(String username, String email, String phone, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SIGNUP_USERNAME, username);
        values.put(KEY_SIGNUP_EMAIL, email);
        values.put(KEY_SIGNUP_PHONE, phone);
        values.put(KEY_SIGNUP_ADDRESS, address);
        values.put(KEY_PASSWORD, getPasswordForUsername(username)); // Retrieve the password from users table
        db.insert(TABLE_SIGNUP_DETAILS, null, values);
        db.close();
    }

    public String getEmailForUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_SIGNUP_EMAIL};
        String selection = KEY_SIGNUP_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_SIGNUP_DETAILS, columns, selection, selectionArgs, null, null, null);
        String email = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int emailColumnIndex = cursor.getColumnIndex(KEY_SIGNUP_EMAIL);
                if (emailColumnIndex != -1) {
                    email = cursor.getString(emailColumnIndex);
                }
                cursor.close();
            }
        }

        db.close();
        return email;
    }

    public String getPhoneNumber(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_SIGNUP_PHONE};
        String selection = KEY_SIGNUP_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_SIGNUP_DETAILS, columns, selection, selectionArgs, null, null, null);
        String phoneNumber = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int phoneNumberIndex = cursor.getColumnIndex(KEY_SIGNUP_PHONE);
                if (phoneNumberIndex != -1) {
                    phoneNumber = cursor.getString(phoneNumberIndex);
                }
                cursor.close();
            }
        }

        db.close();
        return phoneNumber;
    }

    public String getAddress(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_SIGNUP_ADDRESS};
        String selection = KEY_SIGNUP_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_SIGNUP_DETAILS, columns, selection, selectionArgs, null, null, null);
        String address = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int addressIndex = cursor.getColumnIndex(KEY_SIGNUP_ADDRESS);
                if (addressIndex != -1) {
                    address = cursor.getString(addressIndex);
                }
                cursor.close();
            }
        }

        db.close();
        return address;
    }

    public void updatePhoneNumber(String username, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SIGNUP_PHONE, phone);
        String whereClause = KEY_SIGNUP_USERNAME + " = ?";
        String[] whereArgs = {username};
        db.update(TABLE_SIGNUP_DETAILS, values, whereClause, whereArgs);
        db.close();
    }

    public void updateAddress(String username, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SIGNUP_ADDRESS, address);
        String whereClause = KEY_SIGNUP_USERNAME + " = ?";
        String[] whereArgs = {username};
        db.update(TABLE_SIGNUP_DETAILS, values, whereClause, whereArgs);
        db.close();
    }
    // Inside DatabaseHelper class
    public void removeAllProducts() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, null, null);
        db.close();
    }
    public void removeFromCart(int productId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define the WHERE clause to find the product in the cart by its ID
        String whereClause = KEY_CART_PRODUCT_ID + " = ?";
        String[] whereArgs = {String.valueOf(productId)};

        // Perform the delete operation
        db.delete(TABLE_CART, whereClause, whereArgs);

        db.close();
    }

}