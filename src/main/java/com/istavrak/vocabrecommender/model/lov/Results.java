package com.istavrak.vocabrecommender.model.lov;

import com.google.gson.annotations.SerializedName;
import com.istavrak.vocabrecommender.model.SearchResults;

import java.util.List;

public class Results extends SearchResults {
    private List<Term> results;
    private String queryString;

    @SerializedName("total_results")
    private Integer totalResults;

    public List<Term> getResults() {
        return results;
    }

    public void setResults(List<Term> results) {
        this.results = results;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("terms: ");
        for (Term t : results) {
            sb.append('[');
            sb.append(t.toString());
            sb.append(']');
            sb.append(", ");
        }
        sb.append("queryString: ");
        sb.append(queryString);
        sb.append(", ");
        sb.append("totalResults: ");
        sb.append(totalResults);
        return sb.toString();
    }
}
