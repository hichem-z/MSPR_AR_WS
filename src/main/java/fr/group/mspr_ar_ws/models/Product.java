package fr.group.mspr_ar_ws.models;

import java.math.BigDecimal;

public class Product {
    private long id;
    private long orderId;
    private String createdAt;

    private int stock;
    private String name;
    private Details details;

    public Product(long id, String createdAt, int stock, String name, Details details) {
        this.id = id;
        this.createdAt = createdAt;
        this.stock = stock;
        this.name = name;
        this.details = details;
    }

    public Product() {
    }

    public long getId() {
        return id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public static class Details{
        private BigDecimal price;
        private String Description;
        private String color;

        public Details(BigDecimal price, String description, String color) {
            this.price = price;
            Description = description;
            this.color = color;
        }

        public Details() {
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}
