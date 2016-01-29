package com.istavrak.vocabrecommender.model;

public class RecommendationFailure implements RecommendationResponse{

    public String message;

    public RecommendationFailure(String message) {
        this.message = message;
    }

    @Override
    public boolean getSuccess() {
        return false;
    }
}
