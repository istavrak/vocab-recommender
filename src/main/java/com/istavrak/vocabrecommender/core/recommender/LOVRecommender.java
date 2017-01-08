package com.istavrak.vocabrecommender.core.recommender;

import com.istavrak.vocabrecommender.core.searcher.LOVSearcher;
import com.istavrak.vocabrecommender.model.lov.Results;
import com.istavrak.vocabrecommender.model.vsearch.Query;
import com.istavrak.vocabrecommender.model.vsearch.ResultTerm;

public class LOVRecommender extends VocabularyRecommender {
    @Override
    public Query getResultsFor(String keyword) {
        Query query = new Query();
        query.keyword = keyword;
        query.hasResultTerm = new ResultTerm();

        LOVSearcher searcher = new LOVSearcher();
        Results lovResults = (Results) searcher.searchFor(keyword);

        // TODO implement the logic to use the rankers here
        if (!lovResults.getResults().isEmpty()) {
            query.hasResultTerm.termURI = lovResults.getResults().get(0).getUri().get(0);
        }
        return query;
    }
}
