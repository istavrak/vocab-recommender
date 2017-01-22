package com.istavrak.vocabrecommender.lovranker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigInteger;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Dataset implements Serializable {
    private String uri;
    private String label;
    private BigInteger occurrences;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public BigInteger getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(BigInteger occurrences) {
        this.occurrences = occurrences;
    }

    @Override
    public String toString() {
        return "Dataset{" +
                "uri='" + uri + '\'' +
                ", label='" + label + '\'' +
                ", occurrences=" + occurrences +
                '}';
    }
}
