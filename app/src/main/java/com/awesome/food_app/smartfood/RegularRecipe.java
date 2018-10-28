package com.awesome.food_app.smartfood;

public class RegularRecipe extends RecipeModel {
    private double recipeLongitude;
    private double recipeLatitude;
    private int recipeID;
    private int recipeCookerID;

    public RegularRecipe(int recipeID, int recipeCookerID, String recipeName, UserModel
            recipeCooker, String recipeDescription,
                         double recipePrice, double recipeLongitude, double recipeLatitude) {
        super(recipeID, recipeName, recipeCooker, recipeDescription, recipePrice);
        this.recipeLongitude = recipeLongitude;
        this.recipeLatitude = recipeLatitude;
        this.recipeCookerID = recipeCookerID;
        this.recipeID = recipeID;
    }

    public double getRecipeLongitude() {
        return recipeLongitude;
    }

    public double getRecipeLatitude() {
        return recipeLatitude;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public int getRecipeCookerID() {
        return recipeCookerID;
    }
}
