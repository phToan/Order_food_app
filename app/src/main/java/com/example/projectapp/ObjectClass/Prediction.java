package com.example.projectapp.ObjectClass;

import java.io.Serializable;

public class Prediction implements Serializable {
    private String description;
    private String place_id;
    private String reference;
//    private StructuredFormatting structuredFormatting;
//
//    public StructuredFormatting getStructuredFormatting() {
//        return structuredFormatting;
//    }
//
//    public void setStructuredFormatting(StructuredFormatting structuredFormatting) {
//        this.structuredFormatting = structuredFormatting;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}

