package com.istavrak.vocabrecommender.lovranker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Version implements Serializable {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private String issued;
    private String name;
    private Integer propertyNumber;
    private Integer instanceNumber;
    private Integer datatypeNumber;
    private Integer classNumber;
    private List<String> relSpecializes;
    private List<String> relImports;
    private List<String> relGeneralizes;
    private List<String> relExtends;
    private List<String> relEquivalent;
    private List<String> relDisjunc;
    private List<String> relMetadata;

    public String getIssued() {
        return issued;
    }

    public Date getIssuedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            return sdf.parse(issued);
        } catch (ParseException e) {
            e.getStackTrace();
        }
        return null;
    }

    public void setIssued(String issued) {
        this.issued = issued;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPropertyNumber() {
        return propertyNumber;
    }

    public void setPropertyNumber(Integer propertyNumber) {
        this.propertyNumber = propertyNumber;
    }

    public Integer getInstanceNumber() {
        return instanceNumber;
    }

    public void setInstanceNumber(Integer instanceNumber) {
        this.instanceNumber = instanceNumber;
    }

    public Integer getDatatypeNumber() {
        return datatypeNumber;
    }

    public void setDatatypeNumber(Integer datatypeNumber) {
        this.datatypeNumber = datatypeNumber;
    }

    public Integer getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(Integer classNumber) {
        this.classNumber = classNumber;
    }

    public List<String> getRelSpecializes() {
        return relSpecializes;
    }

    public void setRelSpecializes(List<String> relSpecializes) {
        this.relSpecializes = relSpecializes;
    }

    public List<String> getRelImports() {
        return relImports;
    }

    public void setRelImports(List<String> relImports) {
        this.relImports = relImports;
    }

    public List<String> getRelGeneralizes() {
        return relGeneralizes;
    }

    public void setRelGeneralizes(List<String> relGeneralizes) {
        this.relGeneralizes = relGeneralizes;
    }

    public List<String> getRelExtends() {
        return relExtends;
    }

    public void setRelExtends(List<String> relExtends) {
        this.relExtends = relExtends;
    }

    public List<String> getRelEquivalent() {
        return relEquivalent;
    }

    public void setRelEquivalent(List<String> relEquivalent) {
        this.relEquivalent = relEquivalent;
    }

    public List<String> getRelDisjunc() {
        return relDisjunc;
    }

    public void setRelDisjunc(List<String> relDisjunc) {
        this.relDisjunc = relDisjunc;
    }

    public List<String> getRelMetadata() {
        return relMetadata;
    }

    public void setRelMetadata(List<String> relMetadata) {
        this.relMetadata = relMetadata;
    }

    public List<String> getOutgoingLinks() {
        List<String> outgoingLinks = new ArrayList<>();
        outgoingLinks.addAll(getRelSpecializes());
        outgoingLinks.addAll(getRelImports());
        outgoingLinks.addAll(getRelGeneralizes());
        outgoingLinks.addAll(getRelExtends());
        outgoingLinks.addAll(getRelEquivalent());
        outgoingLinks.addAll(getRelDisjunc());
        outgoingLinks.addAll(getRelMetadata());
        return outgoingLinks;
    }

    @Override
    public String toString() {
        return "Version{" +
                "issued='" + issued + '\'' +
                ", name='" + name + '\'' +
                ", propertyNumber=" + propertyNumber +
                ", instanceNumber=" + instanceNumber +
                ", datatypeNumber=" + datatypeNumber +
                ", classNumber=" + classNumber +
                ", outgoing links: " + (relSpecializes.size() + relImports.size() + relGeneralizes.size() + relExtends.size()
                + relEquivalent.size() + relDisjunc.size() + relMetadata.size()) +
                ", relSpecializes=" + relSpecializes +
                ", relImports=" + relImports +
                ", relGeneralizes=" + relGeneralizes +
                ", relExtends=" + relExtends +
                ", relEquivalent=" + relEquivalent +
                ", relDisjunc=" + relDisjunc +
                ", relMetadata=" + relMetadata +
                '}';
    }
}
