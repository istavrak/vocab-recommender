package com.istavrak.vocabrecommender.core.recommenders;

import com.istavrak.vocabrecommender.model.vsearch.Query;
import com.istavrak.vocabrecommender.model.vsearch.ResultTerm;

/**
 * Used to generate the recommendation about common parts of webpages
 * that are domain and context agnostic, e.g. images.
 */
public class StaticRecommender extends VocabularyRecommender {

    @Override
    public Query getResultsFor(String keyword) {
        Query query = new Query();
        query.keyword = keyword;
        query.hasResultTerm = new ResultTerm();

        switch (keyword) {
            case "image":
                query.hasResultTerm.termURI = "http://schema.org/image";
                break;
        }
        return query;
    }
}
