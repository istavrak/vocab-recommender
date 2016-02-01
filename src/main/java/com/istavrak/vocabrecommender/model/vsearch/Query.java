package com.istavrak.vocabrecommender.model.vsearch;

import java.io.Serializable;

public class Query implements Serializable {
    private static final long serialVersionUID = 1L;

    public String keyword;
    public String language;
    public ResultTerm hasResultTerm;

}
