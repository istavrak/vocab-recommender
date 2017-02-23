package com.istavrak.vocabrecommender.core.recommender;

import com.istavrak.vocabrecommender.PropertiesLoader;
import com.istavrak.vocabrecommender.core.ranker.TermLODRanker;
import com.istavrak.vocabrecommender.core.ranker.TermVocabccRanker;
import com.istavrak.vocabrecommender.core.ranker.VocabularyLOVRanker;
import com.istavrak.vocabrecommender.core.searcher.LOVSearcher;
import com.istavrak.vocabrecommender.model.lov.Results;
import com.istavrak.vocabrecommender.model.lov.Term;
import com.istavrak.vocabrecommender.model.vsearch.Query;
import com.istavrak.vocabrecommender.model.vsearch.Rank;
import com.istavrak.vocabrecommender.model.vsearch.ResultTerm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LOVRecommender extends VocabularyRecommender {
    private static final String VOCABCC_PROP = "search.vocabcc.enabled";
    private static final String LODSTATS_PROP = "search.lodstats.enabled";
    private static final String ALPHA_PROP = "alphafactor";
    private static final String MAX_TERMS_PROP = "recommend.terms.max";
    private static final PropertiesLoader propertiesLoader = PropertiesLoader.INSTANCE;
    private static final boolean isVocabccEnabled = Boolean.parseBoolean(propertiesLoader.getProperty(VOCABCC_PROP));
    private static final boolean isLodstatsEnabled = Boolean.parseBoolean(propertiesLoader.getProperty(LODSTATS_PROP));
    private static final int maxTerms = Integer.parseInt(propertiesLoader.getProperty(MAX_TERMS_PROP));

    @Override
    public Query getResultsFor(String keyword) {

        Query query = new Query();
        query.setKeyword(keyword);

        LOVSearcher searcher = new LOVSearcher();
        Results lovResults = (Results) searcher.searchFor(keyword);

        List<ResultTerm> finalTerms = new ArrayList<>();

        if (!lovResults.getResults().isEmpty()) {
            for (Term result : lovResults.getResults()) {
                String termURI = result.getUri().get(0);
                String vocabPrefix = result.getPrefixedName().get(0).split(":")[0];

                // LOV vocabulary ranking
                Rank lovVocabRank = VocabularyLOVRanker.INSTANCE.getVocabularyRanking(vocabPrefix);

                // LODStats ranking
                Rank rankLOD = isLodstatsEnabled ? TermLODRanker.INSTANCE.getTermRanking(termURI) : null;

                // Vocabcc ranking
                Rank rankVocabcc = isVocabccEnabled ? TermVocabccRanker.INSTANCE.getTermRanking(termURI) : null;

                // Calculate final score
                ResultTerm queryResultTerm = new ResultTerm();
                queryResultTerm.termURI = termURI;
                queryResultTerm.hasRank = calculateTotalTermRanking(rankLOD, rankVocabcc, lovVocabRank,
                        Double.parseDouble(result.getScore()));
                finalTerms.add(queryResultTerm);
                if (finalTerms.size() == 10) {
                    break;
                }
            }
        }

        Collections.sort(finalTerms, ResultTermComparator);
        query.setHasResultTerm(new ArrayList<ResultTerm>());
        if (!finalTerms.isEmpty()) {
            if (finalTerms.size() > maxTerms) {
                query.getHasResultTerm().addAll(finalTerms.subList(0, maxTerms));
            } else {
                query.getHasResultTerm().addAll(finalTerms);
            }
            return query;
        } else {
            return null;
        }
    }

    private static Comparator<ResultTerm> ResultTermComparator = new Comparator<ResultTerm>() {

        public int compare(ResultTerm r1, ResultTerm r2) {

            Double r1Rank = r1.hasRank.rankValue;
            Double r2Rank = r2.hasRank.rankValue;

            return r2Rank.compareTo(r1Rank);
        }

    };

    private Rank calculateTotalTermRanking(Rank termLodRank, Rank termVocabccRank,
                                           Rank vocabLovRank, Double lovScoreAPI) {
        Double finalRank;
        Double termLodRankScore = termLodRank != null ? termLodRank.rankValue : 0;
        Double termVocabccRankScore = termVocabccRank != null ? termVocabccRank.rankValue : 1;
        Double vocabLovRankScore = vocabLovRank != null ? vocabLovRank.rankValue : 0;
        Double lovRelevanceScore = lovScoreAPI != null ? lovScoreAPI : 1;

        double a = Double.parseDouble(propertiesLoader.getProperty(ALPHA_PROP));
        finalRank = (termLodRankScore / termVocabccRankScore + a * vocabLovRankScore) * lovRelevanceScore;
        return new Rank(finalRank);
    }
}
