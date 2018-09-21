package com.example.saji.smartfood;

public class RegularRecipe extends RecipeModel {
    private double recipeLongitude;
    private double recipeLatitude;

    public RegularRecipe(String recipeName, UserModel recipeCooker, String recipeDescription,
                         double recipePrice, double recipeLongitude, double recipeLatitude) {
        super(recipeName, recipeCooker, recipeDescription, recipePrice);
        this.recipeLongitude = recipeLongitude;
        this.recipeLatitude = recipeLatitude;
    }

    public double getRecipeLongitude() {
        return recipeLongitude;
    }

    public double getRecipeLatitude() {
        return recipeLatitude;
    }
}
