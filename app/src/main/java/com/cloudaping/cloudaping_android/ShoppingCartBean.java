package com.cloudaping.cloudaping_android;

import java.io.Serializable;

/**
 * Created by AYD on 2016/11/22.
 * <p>
 * 购物车
 */
public class ShoppingCartBean implements Serializable
    {

        private int id;
        private String customerID;
        private String imageUrl;
        private String shoppingName;

        private int dressSize;
        private String attribute;
        private String type;

        public String getType() {
            return type;
        }



        public void setType(String type) {
            this.type = type;
        }

        public String getOriginPrice() {
            return originPrice;
        }

        public void setOriginPrice(String originPrice) {
            this.originPrice = originPrice;
        }
        private String traderName;
        private String originPrice;
        private double price;

        public boolean isChoosed;
        public boolean isCheck = false;
        private int count;

        public String getTraderName() {
            return traderName;
        }

        public void setTraderName(String traderName) {
            this.traderName = traderName;
        }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public ShoppingCartBean() {
    }
        public ShoppingCartBean(int id,String imageUrl,String shoppingName,double price) {
            this.id = id;
            this.shoppingName = shoppingName;
            this.imageUrl=imageUrl;
            this.price = price;
        }
    public ShoppingCartBean(String customerID,int id, String shoppingName, String attribute, int dressSize,
                            double price, int count) {
        this.customerID=customerID;
        this.id = id;
        this.shoppingName = shoppingName;
        this.attribute = attribute;
        this.dressSize = dressSize;
        this.price = price;
        this.count = count;

    }

    public int getCount() {
        return count;
    }
        public String getCustomerID() {
            return customerID;
        }

        public void setCustomerID(String customerID) {
            this.customerID = customerID;
        }
    public void setCount(int count) {
        this.count = count;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getShoppingName() {
        return shoppingName;
    }

    public void setShoppingName(String shoppingName) {
        this.shoppingName = shoppingName;
    }


    public int getDressSize() {
        return dressSize;
    }

    public void setDressSize(int dressSize) {
        this.dressSize = dressSize;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


}
