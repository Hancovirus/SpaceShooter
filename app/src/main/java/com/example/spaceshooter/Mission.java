package com.example.spaceshooter;

import androidx.annotation.NonNull;

public class Mission {
    private int id;
    private String name;
    private String description;
    private boolean isComplete;

    public Mission(int id, String name, String description, boolean isComplete) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isComplete = isComplete;
    }

    @NonNull
    @Override
    public String toString() {
        return "[" + id + " " + isComplete + " " + "]\n";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
