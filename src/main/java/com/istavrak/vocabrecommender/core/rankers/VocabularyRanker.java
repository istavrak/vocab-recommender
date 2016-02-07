package com.istavrak.vocabrecommender.core.rankers;

import com.istavrak.vocabrecommender.model.vsearch.Rank;

public interface VocabularyRanker {
    Rank getVocabularyRanking(String vocabURI);
}
