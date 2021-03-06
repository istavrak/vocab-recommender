package com.istavrak.vocabrecommender.lovranker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Vocab implements Serializable {
    @JsonProperty("nsp")
    private String namespace;
    private String prefix;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return "Vocab{" +
                "namespace='" + namespace + '\'' +
                ", prefix='" + prefix + '\'' +
                '}';
    }
}
