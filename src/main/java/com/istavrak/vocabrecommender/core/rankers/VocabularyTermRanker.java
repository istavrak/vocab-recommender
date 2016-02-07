package com.istavrak.vocabrecommender.core.rankers;

import com.istavrak.vocabrecommender.model.vsearch.Rank;

public interface VocabularyTermRanker {
    Rank getTermRanking(String termURI);
}
