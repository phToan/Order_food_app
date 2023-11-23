package com.example.projectapp.ObjectClass;

import java.io.Serializable;

public class Evaluation implements Serializable {
    private String name;
    private String evaluation;
    private int rate;

    public Evaluation(String name, String evaluation) {
        this.name = name;
        this.evaluation = evaluation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
