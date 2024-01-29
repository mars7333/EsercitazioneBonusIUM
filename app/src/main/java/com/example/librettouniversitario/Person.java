package com.example.librettouniversitario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Person implements Serializable {
    private String name, surname, password;
    private Calendar birthdate;

    private static List<Person> personList = new ArrayList<>();


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirthdate(String string) {
        this.birthdate = birthdate;
    }

    public static void addPerson(Person person) {
        personList.add(person);
    }

    public double calcolaMediaAritmetica() {
        if (ExamList.getExams().isEmpty()) {
            return 0;
        }
        int sommaVoti = 0;
        for (Exam exam : ExamList.getExams()) {
            sommaVoti += exam.getGrade();
        }
        return (double) sommaVoti / ExamList.getExams().size();
    }

    public double calcolaMediaPonderata() {
        if (ExamList.getExams().isEmpty()) {
            return 0;
        }
        int sommaPonderata = 0;
        int totaleCfu = 0;
        for (Exam exam : ExamList.getExams()) {
            sommaPonderata += exam.getGrade() * exam.getCfu();
            totaleCfu += exam.getCfu();
        }
        return totaleCfu > 0 ? (double) sommaPonderata / totaleCfu : 0;
    }

    public double calcolaVotoDiLaurea() {
        double mediaPonderata = calcolaMediaPonderata();
        return (int)mediaPonderata * 110 / 30;
    }
}

