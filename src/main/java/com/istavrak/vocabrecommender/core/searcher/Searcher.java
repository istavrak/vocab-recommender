package com.istavrak.vocabrecommender.core.searcher;

import com.istavrak.vocabrecommender.model.SearchResults;

abstract class Searcher {

    public SearchResults searchFor(String keyword) {
        return invokeViaHttp(generateApiUrl(keyword));
    }

    abstract SearchResults invokeViaHttp(String url);

    private String generateApiUrl(String keyword) {
        return getApiSearchUrl() + keyword;
    }

    abstract String getApiSearchUrl();
}
