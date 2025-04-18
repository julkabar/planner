package com.example.planner_practice;

public class Task {
    private int id;
    private String name;
    private int difficulty;
    private int time;
    private String date;
    private int priority;
    private String type;
    private int status;

    Task(){}

    Task(int id, String name, int difficulty, int time, String date, int priority, String type, int status) {
        this.id = id;
        this.name = name;
        this.difficulty = difficulty;
        this.time = time;
        this.date = date;
        this.priority = priority;
        this.type = type;
        this.status = status;
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

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo(){
        return name + "\n\t[ скл. " + difficulty + ", пр. " + priority + "]\n\t— " + date + " (" + time + " хв.)";
    }
}
