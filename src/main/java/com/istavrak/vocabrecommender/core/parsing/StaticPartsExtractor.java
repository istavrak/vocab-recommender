package com.istavrak.vocabrecommender.core.parsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StaticPartsExtractor {

    public List<String> getCommonParts(String url) throws IOException {
        List<String> tokens = new ArrayList<String>();
        Document doc = Jsoup.connect(url).get();
        Elements media = doc.select("[src]");

        for (Element src : media) {
            if (src.tagName().equals("img") && !tokens.contains("image")) {
                tokens.add("image");
            }
        }
        return tokens;
    }

}
