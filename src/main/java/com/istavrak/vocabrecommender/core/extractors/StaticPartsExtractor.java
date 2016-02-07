package com.istavrak.vocabrecommender.core.extractors;

import com.istavrak.vocabrecommender.model.TargetPage;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.util.ArrayList;
import java.util.List;

public class StaticPartsExtractor implements TokensExtractor {

    public List<String> getTokens(TargetPage page) {
        List<String> tokens = new ArrayList<String>();
        Document doc = page.getPageDocument();
        Elements media = doc.select("[src]");

        for (Element src : media) {
            if (src.tagName().equals("img") && !tokens.contains("image")) {
                tokens.add("image");
            }
        }
        return tokens;
    }

}
