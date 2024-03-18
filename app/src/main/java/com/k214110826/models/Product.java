package com.k214110826.models;

import androidx.annotation.NonNull;

public class Product {
    int productCode;
    String productName;
    double productPrice;
    //Constructor
    public Product(int productCode, String productName, double productPrice) {
        this.productCode = productCode;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;

    }

    @NonNull
    @Override
    public String toString() {
        return this.productCode + "-" + this.productName + "-" + this.productPrice;
    }
}
