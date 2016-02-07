package com.istavrak.vocabrecommender.model.vsearch;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultTerm implements Serializable {
    private static final long serialVersionUID = 1L;

    public String termURI;
    public Rank hasRank;
}
