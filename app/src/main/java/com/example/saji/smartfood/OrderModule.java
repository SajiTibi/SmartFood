package com.example.saji.smartfood;

public class OrderModule {
    private int orderID;
    private int recipePurchaserID;
    private String purchaseTime;
    private int recipeID;
    private int state;

    public OrderModule(int orderID, int recipeID, int recipePurchaserID, String purchaseTime, int state) {
        this.orderID = orderID;
        this.recipeID = recipeID;
        this.recipePurchaserID = recipePurchaserID;
        this.purchaseTime = purchaseTime.substring(0, 16);
        this.state = state;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getRecipePurchaserID() {
        return recipePurchaserID;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public int getState() {
        return state;
    }
}
