package com.example.math_god;

public class user {
    private String name;
    private int score;

    public user(){};

    public user(String name){
        this.name = name;
    }

    public user(String name,int score){
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
