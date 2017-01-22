package com.istavrak.vocabrecommender.lovranker.model;

import java.util.List;

public class VocabAggregation {
    private String prefix;
    private int incoming;
    private int outgoing;
    private List<String> authors;

    public VocabAggregation(String prefix, int incoming, int outgoing, List<String> authors) {
        this.prefix = prefix;
        this.incoming = incoming;
        this.outgoing = outgoing;
        this.authors = authors;
    }

    public VocabAggregation() {
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getIncoming() {
        return incoming;
    }

    public void setIncoming(int incoming) {
        this.incoming = incoming;
    }

    public int getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(int outgoing) {
        this.outgoing = outgoing;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(";");
        sb.append(incoming);
        sb.append(";");
        sb.append(outgoing);
        sb.append(";");
        for (String author : authors) {
            sb.append(author);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
