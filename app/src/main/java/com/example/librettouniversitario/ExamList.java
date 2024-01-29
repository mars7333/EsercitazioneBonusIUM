package com.example.librettouniversitario;

import java.io.Serializable;
import java.util.ArrayList;

public class ExamList implements Serializable {

    //arrayList statico per contenere gli esami
    private static ArrayList<Exam> exams = new ArrayList<>();

    //metodo per aggiungere un esame all'elenco
    public static void addExam(Exam exam) {
        exams.add(exam);
    }

    //metodo per ottenere l'elenco degli esami
    public static ArrayList<Exam> getExams() {
        return exams;
    }

    //metodo per cancellare gli esami
    public static void clearExams() {
        exams.clear();
    }

    // Altri metodi utili come la rimozione di un esame, la ricerca, ecc., possono essere aggiunti qui
}
