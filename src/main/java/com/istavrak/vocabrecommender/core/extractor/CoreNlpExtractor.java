package com.istavrak.vocabrecommender.core.extractor;


import com.istavrak.vocabrecommender.model.TargetPage;
import edu.stanford.nlp.simple.*;

import java.util.*;

public class CoreNlpExtractor implements TokensExtractor {

    private final String NOUN_POS = "NN";

    private List<String> extractKeywords(String content) {
        Document doc = new Document(content);
        Set<String> tokens = new HashSet<>();
        for (Sentence sent : doc.sentences()) {
            List<String> words = sent.words();
            for (int i = 0; i < words.size(); i++) {
                if (words.get(i).length() > 2 && NOUN_POS.equals(sent.posTag(i))) {
                    tokens.add(words.get(i).toLowerCase());
                }
            }
        }
        return !tokens.isEmpty() ? new ArrayList<>(tokens) : Collections.<String>emptyList();
    }

    @Override
    public List<String> getTokens(TargetPage page) {
        return extractKeywords(page.getPageDocument().body().text());
    }
}
