package com.istavrak.vocabrecommender.core.extractor;

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

        // The elements audio, video refer to HTML 5 as that is described by W3C:
        // https://www.w3.org/TR/html-markup/elements.html
        for (Element src : media) {
            if (src.tagName().equals("img") && !tokens.contains("image")) {
                tokens.add("image");
            } else if (src.tagName().equals("video") && !tokens.contains("video")) {
                tokens.add("video");
            } else if (src.tagName().equals("audio") && !tokens.contains("audio")) {
                tokens.add("audio");
            }
        }
        return tokens;
    }

}
