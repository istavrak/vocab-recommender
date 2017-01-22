package com.istavrak.vocabrecommender.lovranker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
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

    public List<Contributor> getCreators() {
        return creators;
    }

    public void setCreators(List<Contributor> creators) {
        this.creators = creators;
    }

    public Version getLatestVersion() {
        Version latestVersion = null;
        if (versions != null && !versions.isEmpty()) {
            for (Version version : versions) {
                if (latestVersion == null) {
                    latestVersion = version;
                } else {
                    if ( latestVersion.getIssuedDate()!= null && version.getIssuedDate() != null
                            && latestVersion.getIssuedDate().before(version.getIssuedDate())) {
                        latestVersion = version;
                    }
                }
            }
            return latestVersion;
        } else {
            return null;
        }
    }

    public List<String> getContributorIds() {
        List<String> ids = new ArrayList<>();

        if (getContributors() != null) {
            for (Contributor contributor : getContributors()) {
                ids.add(contributor.getId());
            }
        }

        if (getCreators() != null) {
            for (Contributor contributor : getCreators()) {
                ids.add(contributor.getId());
            }
        }
        return ids;
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
