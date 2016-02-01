package com.istavrak.vocabrecommender.core;

import com.istavrak.vocabrecommender.model.vsearch.Query;
import java.util.List;

public class LOVRecommender implements VocabularyRecommender {
    @Override
    public Query getResultsFor(String keyword) {
        return null;
    }

    @Override
    public List<Query> recommendVocabularyFor(List<String> keyword) {
        return null;
    }
}
