package com.swlc.gadgetmart.backend.main.dto;

public class ProductDto {

    private String id;
    protected String name;
    private String description;
    private String image;
    private double price;
    private double deliveryCost;
    private String brand;
    private String category;
    private int discount;
    private String shop;
    private String warranty;
    private boolean soldOut;

    public ProductDto() {
    }

    public ProductDto(String id, String name, String description, String image, double price, double deliveryCost, String brand, String category, int discount, String shop, String warranty, boolean soldOut) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.deliveryCost = deliveryCost;
        this.brand = brand;
        this.category = category;
        this.discount = discount;
        this.shop = shop;
        this.warranty = warranty;
        this.soldOut = soldOut;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public boolean isSoldOut() {
        return soldOut;
    }

    public void setSoldOut(boolean soldOut) {
        this.soldOut = soldOut;
    }
}
