package com.istavrak.vocabrecommender.core.recommender;

import com.istavrak.vocabrecommender.model.vsearch.Query;
import com.istavrak.vocabrecommender.model.vsearch.Rank;
import com.istavrak.vocabrecommender.model.vsearch.ResultTerm;

/**
 * Used to generate the recommendation about common parts of webpages
 * that are domain and content agnostic, e.g. media types.
 */
public class StaticRecommender extends VocabularyRecommender {

    @Override
    public Query getResultsFor(String keyword) {
        Query query = new Query();
        query.keyword = keyword;
        query.hasResultTerm = new ResultTerm();

        switch (keyword) {
            case "image":
                query.hasResultTerm.termURI = "https://schema.org/ImageObject";
                query.hasResultTerm.hasRank = new Rank(1.0);
                break;
            case "video":
                query.hasResultTerm.termURI = "https://schema.org/VideoObject";
                query.hasResultTerm.hasRank = new Rank(1.0);
                break;
            case "audio":
                query.hasResultTerm.termURI = "https://schema.org/AudioObject";
                query.hasResultTerm.hasRank = new Rank(1.0);
                break;
            default:
                return null;
        }
        return query;
    }
}
