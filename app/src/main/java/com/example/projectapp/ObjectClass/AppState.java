package com.example.projectapp.ObjectClass;

public class AppState {
    private static AppState instance;
    private boolean shouldFinishActivity = false;

    private AppState() {}

    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public boolean shouldFinishActivity() {
        return shouldFinishActivity;
    }

    public void setShouldFinishActivity(boolean shouldFinishActivity2) {
        this.shouldFinishActivity = shouldFinishActivity2;
    }
}

