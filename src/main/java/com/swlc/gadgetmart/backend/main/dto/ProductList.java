package com.swlc.gadgetmart.backend.main.dto;

import java.util.List;

public class ProductList {

   private List<ProductDto> productDtos;


    public ProductList() {
    }

    public ProductList(List<ProductDto> productDtos) {
        this.productDtos = productDtos;
    }

    public List<ProductDto> getProductDtos() {
        return productDtos;
    }

    public void setItems(List<ProductDto> products) {
        this.productDtos = products;
    }

    @Override
    public String toString() {
        return "ProductList{" +
                "products=" + productDtos +
                '}';
    }
}
