package com.example.projectapp.ui.Address;

import com.example.projectapp.ObjectClass.Prediction;

import java.util.List;

public class PredictionResponse {
    private List<Prediction> predictions;
    private int executed_time;
    private int executed_time_all;
    private String status;
    // Các trường dữ liệu khác, getter và setter

    public List<Prediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<Prediction> predictions) {
        this.predictions = predictions;
    }

    public int getExecuted_time() {
        return executed_time;
    }

    public void setExecuted_time(int executed_time) {
        this.executed_time = executed_time;
    }

    public int getExecuted_time_all() {
        return executed_time_all;
    }

    public void setExecuted_time_all(int executed_time_all) {
        this.executed_time_all = executed_time_all;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

