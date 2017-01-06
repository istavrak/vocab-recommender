package com.istavrak.vocabrecommender.model.lov;


import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;
import java.util.List;

public class Term {
    private List<String> prefixedName;
    private List<String> uri;
    private String type;
    private String score;

    @SerializedName("metrics.occurrencesInDatasets")
    private List<BigInteger> occurrencesInDatasets;

    @SerializedName("metrics.reusedByDatasets")
    private List<BigInteger> reusedByDatasets;

    public List<String> getPrefixedName() {
        return prefixedName;
    }

    public void setPrefixedName(List<String> prefixedName) {
        this.prefixedName = prefixedName;
    }

    public List<String> getUri() {
        return uri;
    }

    public void setUri(List<String> uri) {
        this.uri = uri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public List<BigInteger> getOccurrencesInDatasets() {
        return occurrencesInDatasets;
    }

    public void setOccurrencesInDatasets(List<BigInteger> occurrencesInDatasets) {
        this.occurrencesInDatasets = occurrencesInDatasets;
    }

    public List<BigInteger> getReusedByDatasets() {
        return reusedByDatasets;
    }

    public void setReusedByDatasets(List<BigInteger> reusedByDatasets) {
        this.reusedByDatasets = reusedByDatasets;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String s : prefixedName){
            sb.append("prefixedName: ");
            sb.append(s);
            sb.append(", ");
        }
        for (String s : uri){
            sb.append("uri: ");
            sb.append(s);
            sb.append(", ");
        }
        sb.append("type: ");
        sb.append(type);
        sb.append(", ");
        sb.append("score: ");
        sb.append(score);
        return sb.toString();
    }
}
