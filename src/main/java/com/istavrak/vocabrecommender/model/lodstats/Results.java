package com.istavrak.vocabrecommender.model.lodstats;

import java.util.List;

public class Results {
    private List<Term> terms;

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    public Results(List<Term> terms) {
        this.terms = terms;
    }
}
