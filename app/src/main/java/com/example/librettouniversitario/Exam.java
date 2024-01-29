package com.example.librettouniversitario;

import java.io.Serializable;
import java.util.Calendar;
import java.util.ArrayList;

public class Exam implements Serializable {
    private String name;
    private int grade;
    private int cfu;



    // Constructor
    public Exam(String name, int grade, int cfu) {
        this.name = name;
        this.grade = grade;
        this.cfu = cfu;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getGrade() {
        return grade;
    }

    public int getCfu() {
        return cfu;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setCfu(int cfu) {
        this.cfu = cfu;
    }
}
