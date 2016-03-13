package com.istavrak.vocabrecommender.core.recommender;

import com.istavrak.vocabrecommender.model.vsearch.Query;

import java.util.ArrayList;
import java.util.List;

public abstract class VocabularyRecommender {

    abstract Query getResultsFor(String keyword);

    public List<Query> recommendVocabularyFor(List<String> keywords) {
        List<Query> queries = new ArrayList<Query>();
        for (String keyword : keywords) {
            Query query = getResultsFor(keyword);
            queries.add(query);
        }
        return queries;
    }
}
