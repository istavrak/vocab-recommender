package com.istavrak.vocabrecommender.model.vsearch;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;

/**
 * Following the vsearch vocabulary
 * http://vocab.sti2.at/vsearch
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Query implements Serializable {
    private static final long serialVersionUID = 2L;

    private String keyword;
    private String language;
    private List<ResultTerm> hasResultTerm;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<ResultTerm> getHasResultTerm() {
        return hasResultTerm;
    }

    public void setHasResultTerm(List<ResultTerm> hasResultTerm) {
        this.hasResultTerm = hasResultTerm;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(keyword);
        sb.append("[");
        for (ResultTerm term : hasResultTerm) {
            sb.append(term.termURI);
            sb.append(":");
            sb.append(term.hasRank);
            sb.append(",");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]");
        return sb.toString();
    }
}
