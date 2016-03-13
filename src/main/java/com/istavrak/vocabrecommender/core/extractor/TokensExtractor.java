package com.istavrak.vocabrecommender.core.extractor;

import com.istavrak.vocabrecommender.model.TargetPage;

import java.util.List;

public interface TokensExtractor {
    List<String> getTokens(TargetPage page);
}