package com.istavrak.vocabrecommender.model.lov;


import java.util.List;

public class Results {
    private List<Term> results;
    private String queryString;
    private String total_results;

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

    public String getTotal_results() {
        return total_results;
    }

    public void setTotal_results(String total_results) {
        this.total_results = total_results;
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
        sb.append(total_results);
        return sb.toString();
    }
}
