package com.istavrak.vocabrecommender.core.ranker;


import com.istavrak.vocabrecommender.core.searcher.VocabCCSearcher;
import com.istavrak.vocabrecommender.model.vocabcc.Description;
import com.istavrak.vocabrecommender.model.vocabcc.Results;
import com.istavrak.vocabrecommender.model.vsearch.Rank;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class TermVocabccRanker implements VocabularyTermRanker {

    public final static TermVocabccRanker INSTANCE = new TermVocabccRanker();

    protected TermVocabccRanker() {
        // Exists only to defeat instantiation.
    }

    @Override
    public Rank getTermRanking(String termURI) {
        VocabCCSearcher searcher = new VocabCCSearcher();
        Results results = null;
        try {
            String[] tokens = termURI.split("#");
            if (tokens.length > 1) {
                results = (Results) searcher.searchFor(URLEncoder.encode(tokens[1], "UTF-8"));
            } else {
                tokens = termURI.replace("http://", "").split("/");
                results = (Results) searcher.searchFor(URLEncoder.encode(tokens[tokens.length - 1], "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (results != null && !results.getDescriptions().isEmpty()) {
            for (Description term : results.getDescriptions()) {
                if (term.about.equals(termURI)) {
                    return new Rank(calculateScore(term.usedAsClass != null ? term.usedAsClass : term.usedAsProperty));
                }
            }
        }
        return null;
    }

    private Double calculateScore(Integer occurrences) {
        return Math.sqrt(occurrences.doubleValue());
    }

}
