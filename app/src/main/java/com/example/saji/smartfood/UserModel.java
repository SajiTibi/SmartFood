package com.example.saji.smartfood;

public class UserModel {
    int userID;
    String emailAddress;
    Configs.UserType userType;
    String fcmToken;
    double userLongitude;
    double userLatitude;

    public UserModel(int userID, String emailAddress, int userTypeID, String fcmToken, double
            userLongitude, double userLatitude) {
        this.userID = userID;
        this.emailAddress = emailAddress;
        this.fcmToken = fcmToken;
        this.userLongitude= userLongitude;
        this.userLatitude = userLatitude;
                userType = userTypeID == Configs.USER_FOODIE_ID ? Configs.UserType.FOODIE : Configs.UserType
                        .COOKER;
    }

    public int getUserID() {
        return userID;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Configs.UserType getUserType() {
        return userType;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public double getUserLatitude() {
        return userLatitude;
    }

    public double getUserLongitude() {
        return userLongitude;
    }
}
