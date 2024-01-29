package com.example.librettouniversitario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddExamActivity extends TopBarActivity {


    protected TextView error, indietro;
    protected Intent result, intent;
    protected Serializable object;
    protected Person person;
    protected EditText editTextSubject, editTextGrade, editTextCfu;
    protected Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam);

        // Ottieni l'oggetto Person passato

        intent = getIntent(); // Recupero l'intento
        object = intent.getSerializableExtra("add"); // Recupero l'oggetto

        person = object instanceof Person ? (Person) object : new Person(); // Verifico l'oggetto

        editTextSubject = findViewById(R.id.exam_name);
        editTextGrade = findViewById(R.id.voto);
        editTextCfu = findViewById(R.id.cfu);
        addButton = findViewById(R.id.add_exam);

        addButton.setOnClickListener(v -> {
            if (validateFields()) {
                addExam();
            }
        });

        indietro();

    }

    private void addExam() {
        String subject = editTextSubject.getText().toString();
        int grade = Integer.parseInt(editTextGrade.getText().toString());
        int cfu = Integer.parseInt(editTextCfu.getText().toString());

        Exam exam = new Exam(subject, grade, cfu);
        ExamList.addExam(exam);

        Toast.makeText(this, "Esame aggiunto con successo", Toast.LENGTH_SHORT).show();

        Intent returnIntent = new Intent( AddExamActivity.this ,ResultActivity.class);
        returnIntent.putExtra(MainActivity.PERSON_PATH, person);
        startActivity(returnIntent);
    }

    private boolean validateFields() {
        String subject = editTextSubject.getText().toString();
        String gradeStr = editTextGrade.getText().toString();
        String cfuStr = editTextCfu.getText().toString();

        if (subject.isEmpty() || gradeStr.isEmpty() || cfuStr.isEmpty()) {
            Toast.makeText(this, "Tutti i campi sono obbligatori", Toast.LENGTH_SHORT).show();
            return false;
        }

        int grade = Integer.parseInt(gradeStr);
        if (grade < 18 || grade > 30) {
            Toast.makeText(this, "Il voto deve essere compreso tra 18 e 30", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }





}