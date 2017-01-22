package com.istavrak.vocabrecommender.lovranker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VocabDetails implements Serializable {
    @SerializedName("contributorIds")
    @JsonProperty("contributorIds")
    private List<Contributor> contributors;

    @SerializedName("creatorIds")
    @JsonProperty("creatorIds")
    private List<Contributor> creators;

    private List<Dataset> datasets;
    private String issuedAt;

    @SerializedName("nsp")
    @JsonProperty("nsp")
    private String namespace;
    private String prefix;
    private List<Version> versions;

    public List<Contributor> getContributors() {
        return contributors;
    }

    public void setContributors(List<Contributor> contributors) {
        this.contributors = contributors;
    }

    public String getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(String issuedAt) {
        this.issuedAt = issuedAt;
    }

    public List<Dataset> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<Dataset> datasets) {
        this.datasets = datasets;
    }

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

    public List<Version> getVersions() {
        return versions;
    }

    public void setVersions(List<Version> versions) {
        this.versions = versions;
    }

    public Version getLatestVersion() {
        return versions.get(0);
    }

    @Override
    public String toString() {
        return "VocabDetails{" +
                "contributors=" + contributors +
                ", creators=" + creators +
                ", datasets=" + datasets +
                ", issuedAt='" + issuedAt + '\'' +
                ", namespace='" + namespace + '\'' +
                ", prefix='" + prefix + '\'' +
                ", versions=" + versions +
                '}';
    }
}
