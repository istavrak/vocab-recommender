package com.istavrak.vocabrecommender.model.vocabcc;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Description {
    @XmlElement(name = "usedAsClass", namespace = "http://vocab.cc/v/schema/")
    public Integer usedAsClass;

    @XmlElement(name = "usedAsProperty", namespace = "http://vocab.cc/v/schema/")
    public Integer usedAsProperty;

    @XmlAttribute(name = "about", namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
    public String about;
}
