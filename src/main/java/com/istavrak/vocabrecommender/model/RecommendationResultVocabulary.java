package com.istavrak.vocabrecommender.model;

import com.istavrak.vocabrecommender.model.vsearch.Query;

import java.io.Serializable;
import java.util.List;

public class RecommendationResultVocabulary implements RecommendationResponse, Serializable {
    private static final long serialVersionUID = 1L;

    public List<Query> doQuery;

    public RecommendationResultVocabulary() {
    }

    public RecommendationResultVocabulary(List<Query> doQuery) {
        this.doQuery = doQuery;
    }

    @Override
    public boolean getSuccess() {
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Query query : doQuery) {
            sb.append(query.keyword);
            sb.append(":");
            sb.append(query.hasResultTerm);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
