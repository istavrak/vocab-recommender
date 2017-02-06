package com.istavrak.vocabrecommender.core.recommender;

import com.istavrak.vocabrecommender.core.ranker.TermLODRanker;
import com.istavrak.vocabrecommender.core.ranker.TermVocabccRanker;
import com.istavrak.vocabrecommender.core.ranker.VocabularyLOVRanker;
import com.istavrak.vocabrecommender.core.searcher.LOVSearcher;
import com.istavrak.vocabrecommender.model.lov.Results;
import com.istavrak.vocabrecommender.model.vsearch.Query;
import com.istavrak.vocabrecommender.model.vsearch.Rank;
import com.istavrak.vocabrecommender.model.vsearch.ResultTerm;

public class LOVRecommender extends VocabularyRecommender {
    @Override
    public Query getResultsFor(String keyword) {
        Query query = new Query();
        query.keyword = keyword;
        query.hasResultTerm = new ResultTerm();

        LOVSearcher searcher = new LOVSearcher();
        Results lovResults = (Results) searcher.searchFor(keyword);

        if (!lovResults.getResults().isEmpty()) {
            // TODO iterate through the LOV results
            String termURI = lovResults.getResults().get(0).getUri().get(0);
            query.hasResultTerm.termURI = termURI;
            String vocabPrefix = lovResults.getResults().get(0).getPrefixedName().get(0).split(":")[0];

            // LOV vocabulary ranking
            Rank lovVocabRank = VocabularyLOVRanker.INSTANCE.getVocabularyRanking(vocabPrefix);

            // LODStats ranking
            Rank rankLOD = TermLODRanker.INSTANCE.getTermRanking(termURI);

            // Vocabcc ranking
            Rank rankVocabcc = TermVocabccRanker.INSTANCE.getTermRanking(termURI);

            // Calculate final score
            query.hasResultTerm.hasRank = calculateTotalTermRanking(rankLOD, rankVocabcc, lovVocabRank,
                    Double.parseDouble(lovResults.getResults().get(0).getScore()));
        }
        return query;
    }

    private Rank calculateTotalTermRanking(Rank termLodRank, Rank termVocabccRank,
                                           Rank vocabLovRank, Double lovScoreAPI) {
        Double finalRank;
        Double termLodRankScore = termLodRank != null ? termLodRank.rankValue : 0;
        Double termVocabccRankScore = termVocabccRank != null ? termVocabccRank.rankValue : 1;
        Double vocabLovRankScore = vocabLovRank != null ? vocabLovRank.rankValue : 0;
        Double lovRelevanceScore = lovScoreAPI != null ? lovScoreAPI : 1;

        finalRank = (termLodRankScore / termVocabccRankScore + vocabLovRankScore) * lovRelevanceScore;
        return new Rank(finalRank);
    }
}
