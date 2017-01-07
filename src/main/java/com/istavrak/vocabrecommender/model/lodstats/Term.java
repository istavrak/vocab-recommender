package com.istavrak.vocabrecommender.model.lodstats;


import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;

public class Term {
    private BigInteger datasets;
    private String uri;

    @SerializedName("overall_sum")
    private BigInteger overallSum;

    public BigInteger getDatasets() {
        return datasets;
    }

    public void setDatasets(BigInteger datasets) {
        this.datasets = datasets;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public BigInteger getOverallSum() {
        return overallSum;
    }

    public void setOverallSum(BigInteger overallSum) {
        this.overallSum = overallSum;
    }
}
