package com.istavrak.vocabrecommender.core.ranker;

import com.istavrak.vocabrecommender.model.vsearch.Rank;

public interface VocabularyRanker {
    Rank getVocabularyRanking(String vocabURI);
}
