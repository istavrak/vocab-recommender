package com.istavrak.vocabrecommender.core.recommender;

import com.istavrak.vocabrecommender.model.vsearch.Query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class VocabularyRecommender {

    abstract Query getResultsFor(String keyword);

    public List<Query> recommendVocabularyFor(List<String> keywords) {
        List<String> notUsedKeywords = new ArrayList<>();
        List<Query> queries = new ArrayList<>();
        for (String keyword : keywords) {
            Query query = getResultsFor(keyword);
            if (query != null) {
                queries.add(query);
            } else {
                notUsedKeywords.add(keyword);
            }
        }
        // Keeping only the keywords that have not been used.
        keywords.retainAll(notUsedKeywords);
        return queries;
    }
}
