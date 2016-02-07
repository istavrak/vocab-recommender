package com.istavrak.vocabrecommender.model;

import org.jsoup.nodes.Document;

public class TargetPage {

    private String pageURL;
    private Document pageDocument;

    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    public Document getPageDocument() {
        return pageDocument;
    }

    public void setPageDocument(Document pageDocument) {
        this.pageDocument = pageDocument;
    }
}
