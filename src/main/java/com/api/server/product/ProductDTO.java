package com.api.server.product;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class ProductDTO {
    private long productId;
    private String name;
    private double price;
    private String description;
    @Enumerated(EnumType.STRING)
    private CategoryType category;
    private int numberInStock;


    private String supplier;
    private ImageModel imageModel;

    public ProductDTO(){}

    public ProductDTO(long productId, String name, double price, String description, CategoryType category, int numberInStock, String supplier, ImageModel imageModel) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.numberInStock = numberInStock;
        this.supplier = supplier;
        this.imageModel = imageModel;
    }


    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryType getCategory() {
        return category;
    }

    public void setCategory(CategoryType category) {
        this.category = category;
    }

    public int getNumberInStock() {
        return numberInStock;
    }

    public void setNumberInStock(int numberInStock) {
        this.numberInStock = numberInStock;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public ImageModel getImageModel() {
        return imageModel;
    }

    public void setImageModel(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", numberInStock=" + numberInStock +
                ", supplierName='" + supplier + '\'' +
                '}';
    }
}
