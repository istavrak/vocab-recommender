package com.istavrak.vocabrecommender.model;

import com.istavrak.vocabrecommender.model.vsearch.Query;

import java.io.Serializable;
import java.util.List;

public class RecommendationResultVocabulary implements RecommendationResponse, Serializable {
    private static final long serialVersionUID = 1L;

    public List<Query> doQuery;

    @Override
    public boolean getSuccess() {
        return true;
    }
}
