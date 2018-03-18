package com.cloudaping.cloudaping_android;

/**
 * Created by reggie on 13/03/18.
 */

public class MyData {

    private int id;
    private String description,image_link,productType,originPrice,quantity,category,traderID,updateDate,sell,price;
public MyData(){

}
    public MyData(int id, String description,String image_link,String productType,String originPrice,String quantity,String category,String traderID,String updateDate,String sell,String price) {
        this.id = id;
        this.description = description;
        this.image_link = image_link;
        this.price=price;
        this.productType = productType;
        this.originPrice = originPrice;
        this.quantity = quantity;
        this.category=category;
        this.traderID = traderID;
        this.updateDate = updateDate;
        this.sell = sell;
        this.price=price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrice() {
        return "£"+price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_link() {
        return "http://cloudaping.com/assets/images/"+image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getOriginPrice() {
        return "£"+originPrice;
    }

    public void setOriginPrice(String originPrice) {
        this.originPrice = originPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTraderID() {
        return traderID;
    }

    public void setTraderID(String traderID) {
        this.traderID = traderID;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }
}