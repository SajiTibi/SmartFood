package com.example.saji.smartfood;

public abstract  class RecipeModel {
    private final String recipeName;
    private final UserModel recipeCooker;
    private final String recipeDescription;
    private final double recipePrice;

    public RecipeModel(String recipeName, UserModel recipeCooker, String recipeDescription, double
            recipePrice){

        this.recipeName = recipeName;
        this.recipeCooker = recipeCooker;
        this.recipeDescription = recipeDescription;
        this.recipePrice = recipePrice;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public UserModel getRecipeCooker() {
        return recipeCooker;
    }

    public double getRecipePrice() {
        return recipePrice;
    }
}
