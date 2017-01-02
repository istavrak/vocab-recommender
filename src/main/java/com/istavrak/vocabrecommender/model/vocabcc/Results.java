package com.istavrak.vocabrecommender.model.vocabcc;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RDF", namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
public class Results {
    @XmlElement(name = "Description", namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
    public List<Description> descriptions;

    public List<Description> getDescriptions() {
        if (descriptions == null) {
            descriptions = new ArrayList<>();
        }
        return descriptions;
    }
}

