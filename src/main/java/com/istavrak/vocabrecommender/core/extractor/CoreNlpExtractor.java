package com.istavrak.vocabrecommender.core.extractor;


import com.istavrak.vocabrecommender.model.TargetPage;

import java.util.*;

public class CoreNlpExtractor implements TokensExtractor {

    private final String NOUN_POS = "NN";

    private List<String> extractKeywords(String content) {
       return null;
    }

    @Override
    public List<String> getTokens(TargetPage page) {
        return extractKeywords(page.getPageDocument().body().text());
    }
}
