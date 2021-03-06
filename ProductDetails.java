package com.example.techapp;

public class ProductDetails {

    private String product_name,product_description,product_price;

    private byte[] product_image;

    public ProductDetails(String product_name,byte[] product_image,String product_description,String product_price) {
        this.product_name = product_name;
        this.product_description=product_description;
        this.product_image=product_image;
        this.product_price=product_price;
    }
    public void setProd_name(String product_name){
        this.product_name = product_name;
    }

    public String getProduct_name(){
        return product_name;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }
    public String getProduct_description() {
        return product_description;
    }
    public void setProduct_image(byte[] product_image) {
        this.product_image = product_image;
    }

    public byte[] getProduct_image() {
        return product_image;
    }


    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_price() {
        return product_price;
    }
}
