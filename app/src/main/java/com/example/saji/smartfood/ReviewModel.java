package com.example.saji.smartfood;

public class ReviewModel {
    private final UserModel reviewer;
    private final UserModel reviewed;
    private final String reviewBody;
    private final String reviewDate;
    private int reviewID;

    public ReviewModel(int reviewID, String reviewBody, String reviewDate, UserModel reviewer, UserModel reviewed) {
        this.reviewBody = reviewBody;
        this.reviewDate = reviewDate.substring(0, 16);
        this.reviewID = reviewID;
        this.reviewer = reviewer;
        this.reviewed = reviewed;
    }

    public int getReviewID() {
        return reviewID;
    }

    public String getReviewBody() {
        return reviewBody;
    }

    public UserModel getReviewer() {
        return reviewer;
    }

    public UserModel getReviewed() {
        return reviewed;
    }

    public String getReviewDate() {
        return reviewDate;
    }

}
