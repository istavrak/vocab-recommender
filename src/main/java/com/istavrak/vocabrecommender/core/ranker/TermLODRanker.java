package com.istavrak.vocabrecommender.core.ranker;


import com.istavrak.vocabrecommender.core.searcher.LODStatsSearcher;
import com.istavrak.vocabrecommender.model.lodstats.Results;
import com.istavrak.vocabrecommender.model.lodstats.Term;
import com.istavrak.vocabrecommender.model.vsearch.Rank;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;

public class TermLODRanker implements VocabularyTermRanker {
    public final static TermLODRanker INSTANCE = new TermLODRanker();

    protected TermLODRanker() {
        // Exists only to defeat instantiation.
    }

    @Override
    public Rank getTermRanking(String termURI) {
        LODStatsSearcher searcher = new LODStatsSearcher();
        Results results = null;
        try {
            results = (Results) searcher.searchFor(URLEncoder.encode(termURI, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (results != null && !results.getTerms().isEmpty()) {
            for (Term term : results.getTerms()) {
                if (term.getUri().equals(termURI)) {
                    return new Rank(calculateScore(term.getDatasets()));
                }
            }
        }
        return null;
    }

    private Double calculateScore(BigInteger occurrences) {
        return (Math.log(occurrences.doubleValue())/Math.log(2)+1);
    }
}
