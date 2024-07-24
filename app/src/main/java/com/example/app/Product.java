package com.example.app;


public class Product {
    private String name;
    private String brand;
    private String image;
    private double price;
    private double discount;
    private String manufacturer;
    private String description;
    private int id;

    public Product(int id, String name, String brand, String image, double price, double discount, String manufacturer, String description) {
        this.id=id;
        this.name = name;
        this.brand = brand;
        this.image = image;
        this.price = price;
        this.discount = discount;
        this.manufacturer = manufacturer;
        this.description = description;
    }
    public int getId(){return id;}
    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getImage() {
        return image;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getDescription() {
        return description;
    }
}
