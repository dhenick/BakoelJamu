package id.littlequery.bakoeljamu;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private int imageResId;
    private double price;
    private String category;
    private String description;
    private int quantity;

    public Product(int id, String name, int imageResId, double price, String category, String description, int quantity) {
        this.id = id;
        this.name = name;
        this.imageResId = imageResId;
        this.price = price;
        this.category = category;
        this.description = description;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
