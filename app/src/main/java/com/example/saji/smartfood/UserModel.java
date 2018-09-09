package com.example.saji.smartfood;

public class UserModel  {
    int userID;
    String emailAddress;
    Configs.UserType userType;

    public UserModel(int userID, String emailAddress,int userTypeID){
        this.userID=userID;
        this.emailAddress= emailAddress;
        userType = userTypeID==Configs.USER_FOODIE_ID?Configs.UserType.FOODIE:Configs.UserType
                .COOKER;
    }

    public int getUserID() {
        return userID;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
    public Configs.UserType getUserType(){
        return userType;
    }


}
