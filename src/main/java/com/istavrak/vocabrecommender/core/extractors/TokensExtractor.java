package com.istavrak.vocabrecommender.core.extractors;

import com.istavrak.vocabrecommender.model.TargetPage;

import java.util.List;

public interface TokensExtractor {
    public List<String> getTokens(TargetPage page);
}