package com.istavrak.vocabrecommender.core;

import com.istavrak.vocabrecommender.model.vsearch.Query;
import java.util.List;

public interface VocabularyRecommender {

    Query getResultsFor(String keyword);

    List<Query> recommendVocabularyFor(List<String> keyword);
}
