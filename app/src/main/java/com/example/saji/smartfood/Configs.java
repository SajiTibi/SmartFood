package com.example.saji.smartfood;

public class Configs {
    public static final int USER_COOKER_ID = 0;
    public static final int USER_FOODIE_ID = 1;
    public static final String LOGIN_REQUEST_URL = "https://silicotic-vent.000webhostapp" +
            ".com/Login.php";
    public static final String REGISTER_REQUEST_URL = "https://silicotic-vent.000webhostapp" +
            ".com/Register.php";
    public static final String RECIPES_RETRIEVAL_URL = "https://silicotic-vent.000webhostapp" +
            ".com/RecipeRetriever.php";
    public static final String RECIPES_ADD_URL = "https://silicotic-vent.000webhostapp" +
            ".com/RecipesAdd.php";
    // DB related
    public static final String USER_EMAIL = "email_address";
    public static final String USER_PASSWORD ="user_password";
    public static final String USER_TYPE = "user_type";
    public static final String USER_ID = "uid";
    public static final String RECIPE_NAME = "recipe_name";
    public static final String RECIPE_COOKER_ID = "recipe_cooker_id";
    // TODO change this to recipe_cooker_name in db,here and php
    public static final String RECIPE_COOKER = "recipe_cooker";
    public static final String RECIPE_DESCRIPTION = "recipe_description";
    public static final String RECIPE_PRICE = "recipe_price";
    public static final String RECIPE_ID = "recipe_id";
    public enum UserType{
        FOODIE( "Foodie",1),
        COOKER ("Cooker",0);
        private final String name;
        private final int type;
        UserType ( String name,int type){
            this.name=name;
            this.type=type;
        }
        public String getName(){
            return name;
        }
    }
}
