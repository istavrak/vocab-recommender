package com.istavrak.vocabrecommender.core;

import com.istavrak.vocabrecommender.model.vsearch.Query;
import java.util.List;

/**
 * Used to generate the recommendation about common parts of webpages
 * that are domain and context agnostic.
 * E.g. various media types, images, videos, etc.
 */
public class StaticRecommender implements VocabularyRecommender {
    @Override
    public Query getResultsFor(String keyword) {
        return null;
    }

    @Override
    public List<Query> recommendVocabularyFor(List<String> keyword) {
        return null;
    }
}
