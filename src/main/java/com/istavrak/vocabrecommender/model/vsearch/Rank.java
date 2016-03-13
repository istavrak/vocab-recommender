package com.istavrak.vocabrecommender.model.vsearch;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

/**
 * Following the vsearch vocabulary
 * http://vocab.sti2.at/vsearch
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rank implements Serializable {
    private static final long serialVersionUID = 1L;

    public Double rankValue;

    public Rank(Double rankValue) {
        this.rankValue = rankValue;
    }

    @Override
    public String toString() {
        return String.valueOf(rankValue);
    }
}
