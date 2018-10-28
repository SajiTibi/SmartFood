package com.awesome.food_app.smartfood;

public class MyRecipe extends RecipeModel {
    public MyRecipe(int recipeID, String recipeName, UserModel recipeCooker, String
            recipeDescription,
                    double recipePrice) {
        super(recipeID, recipeName, recipeCooker, recipeDescription, recipePrice);

    }
}
