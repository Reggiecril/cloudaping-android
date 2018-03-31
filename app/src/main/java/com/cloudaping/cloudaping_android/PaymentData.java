package com.cloudaping.cloudaping_android;

/**
 * Created by reggie on 31/03/18.
 */


public class PaymentData {

    private int id;
    private String holder;
    private String cardNumber;
    private String cardLast;
    private String expirationMonth;
    private String expirationYear;
    private String secure;
    private String state;
    public PaymentData(){

    }
    public PaymentData(int id, String holder,String cardNumber,String cardLast,String expirationMonth,String expirationYear,String secure,String state) {
        this.id = id;
        this.holder = holder;
        this.cardNumber = cardNumber;
        this.cardLast=cardLast;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.secure = secure;
        this.state=state;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardLast() {
        return cardLast;
    }

    public void setCardLast(String cardLast) {
        this.cardLast = cardLast;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }

    public String getSecure() {
        return secure;
    }

    public void setSecure(String secure) {
        this.secure = secure;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }




}