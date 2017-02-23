package com.istavrak.vocabrecommender.core.recommender;

import com.istavrak.vocabrecommender.model.vsearch.Query;
import com.istavrak.vocabrecommender.model.vsearch.Rank;
import com.istavrak.vocabrecommender.model.vsearch.ResultTerm;

import java.util.ArrayList;

/**
 * Used to generate the recommendation about common parts of webpages
 * that are domain and content agnostic, e.g. media types.
 */
public class StaticRecommender extends VocabularyRecommender {

    @Override
    public Query getResultsFor(String keyword) {
        Query query = new Query();
        query.setKeyword(keyword);
        query.setHasResultTerm(new ArrayList<ResultTerm>());

        ResultTerm term = new ResultTerm();
        query.getHasResultTerm().add(term);
        switch (keyword) {
            case "image":
                term.termURI = "https://schema.org/ImageObject";
                term.hasRank = new Rank(1.0);
                break;
            case "video":
                term.termURI = "https://schema.org/VideoObject";
                term.hasRank = new Rank(1.0);
                break;
            case "audio":
                term.termURI = "https://schema.org/AudioObject";
                term.hasRank = new Rank(1.0);
                break;
            default:
                return null;
        }
        return query;
    }
}
