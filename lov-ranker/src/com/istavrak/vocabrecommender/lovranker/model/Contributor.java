package com.istavrak.vocabrecommender.lovranker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Contributor implements Serializable {
    @SerializedName("_id")
    @JsonProperty("_id")
    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Contributor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
