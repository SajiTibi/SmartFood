package com.awesome.food_app.smartfood;

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
    public static final String ALL_RECIPES_RETRIEVAL_URL = "https://silicotic-vent.000webhostapp" +
            ".com/AllRecipeRetriever.php";
    public static final String REGISTER_TOKEN_URL = "https://silicotic-vent.000webhostapp" +
            ".com/RegisterToken.php";
    public static final String TOKEN_RETRIEVAL_URL = "https://silicotic-vent.000webhostapp" +
            ".com/FCMRetriever.php";
    public static final String ALL_USERS_RETRIEVAL_URL = "https://silicotic-vent.000webhostapp" +
            ".com/AllUsersRetriever.php";
    public static final String PLACE_ORDER_URL = "https://silicotic-vent.000webhostapp" +
            ".com/PlaceAnOder.php";
    public static final String ORDERS_RETRIEVAL_URL = "https://silicotic-vent.000webhostapp" +
            ".com/CookerOrdersRetriever.php";
    public static final String UPDATE_LOCATION_URL = "https://silicotic-vent.000webhostapp.com/UpdateLocation.php";
    public static final String UPDATE_RECIPE_URL = "https://silicotic-vent.000webhostapp" +
            ".com/UpdateRecipe.php";
    public static final String DELETE_RECIPE_URL = "https://silicotic-vent.000webhostapp.com/DeleteRecipe.php";
    public static final String UPDATE_ORDER_STATUS_URL = "https://silicotic-vent.000webhostapp" +
            ".com/UpdateOrderStatus.php";
    public static final String CART_RETRIEVER_URL = "https://silicotic-vent.000webhostapp" +
            ".com/MyCartRetriever.php";
    public static final String REVIEW_ADD_URL = "https://silicotic-vent.000webhostapp" +
            ".com/ReviewAdd.php";
    public static final String REVIEW_RETRIEVER_URL = "https://silicotic-vent.000webhostapp" +
            ".com/ReviewsRetriever.php";
    public static final String DELETE_ORDER_URL = "https://silicotic-vent.000webhostapp" +
            ".com/DeleteOrder.php";
    // DB related
    public static final String USER_EMAIL = "email_address";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_TYPE = "user_type";
    public static final String USER_ID = "uid";
    public static final String FIREBASE_TOKEN = "fcm_token";

    public static final String RECIPE_NAME = "recipe_name";
    public static final String RECIPE_COOKER_ID = "recipe_cooker_id";
    // TODO change this to recipe_cooker_name in db,here and php
    public static final String RECIPE_COOKER = "recipe_cooker";
    public static final String RECIPE_DESCRIPTION = "recipe_description";
    public static final String RECIPE_PRICE = "recipe_price";
    public static final String RECIPE_ID = "recipe_id";
    public static final String RECIPE_LONGITUDE = "recipe_longitude";
    public static final String USER_LONGITUDE = "user_longitude";

    public static final String RECIPE_LATITUDE = "recipe_latitude";
    public static final String USER_LATITUDE = "user_latitude";
    public static final String REVIEWED_ID = "reviewed_id";
    public static final String REVIEWER_ID = "reviewer_id";
    public static final String REVIEW_ID = "review_id";
    public static final String REVIEW_NAME = "review_name";
    public static final String REVIEW_DATE = "review_date";

    public static final String REVIEW_DESCRIPTION = "review_description";

    public static final String RECIPE_PURCHASER_ID = "recipe_purchaser_id";
    public static final String RECIPE_TIME_ORDER_PLACED = "time_order_placed";
    public static final String ORDER_ID = "order_id";
    public static final String ORDER_STATUS = "order_status";
    public static final String ORDER_STATUS_PENDING = "0";
    public static final String ORDER_STATUS_ACCEPTED = "1";
    public static final String ORDER_STATUS_REJECTED = "2";
    public static final String ORDER_STATUS_FINISHED = "3";


    public final static String SEPARATOR = "__,__";

    public enum UserType {
        FOODIE("Foodie", 1),
        COOKER("Cooker", 0);
        private final String name;
        private final int type;

        UserType(String name, int type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }
    }
}
