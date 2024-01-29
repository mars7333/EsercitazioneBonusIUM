package com.example.librettouniversitario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.DialogInterface;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.*;


public class ResultActivity extends AppCompatActivity implements OnExamDeleteClickListener {
    public static final String PERSON_PATH2 = "com.example.myapplication.person";
    private RecyclerView recyclerView;
    private ExamAdapter adapter;
    protected Person person;
    protected ImageView error, add;
    protected ImageView delete;
    protected Intent result, intent, addExamIntent;
    protected ImageView esci;
    protected Serializable object;
    private TextView textViewMediaAritmetica, textViewMediaPonderata, textViewVotoDiLaurea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        add = findViewById(R.id.add);
        esci = findViewById(R.id.esci);
        // IMPORTANTE  --> gestire apparizione di un alert per la conferma

        // in questa classe viene gestita il libretto

        intent = getIntent(); // Recupero l'intento
        object = intent.getSerializableExtra(MainActivity.PERSON_PATH); // Recupero l'oggetto

        person = object instanceof Person ? (Person) object : new Person(); // Verifico l'oggetto

        recyclerView = findViewById(R.id.exam_list);
        adapter = new ExamAdapter(ExamList.getExams(), this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        add.setOnClickListener(v -> {
            addExamIntent = new Intent(ResultActivity.this, AddExamActivity.class);
            startActivity(addExamIntent);
        });

        esci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
        textViewMediaAritmetica = findViewById(R.id.textViewMediaAritmetica);
        textViewMediaPonderata = findViewById(R.id.textViewMediaPonderata);
        textViewVotoDiLaurea = findViewById(R.id.textViewVotoDiLaurea);

        visualizzaCalcoli();

    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Conferma")
                .setMessage("Sei sicuro di voler uscire dall'account e perdere i dati inseriti?")
                .setPositiveButton("Sì", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Azioni da eseguire per "Sì"
                        ExamList.clearExams(); // Assicurati che questo metodo esista e faccia ciò che deve fare
                        Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Chiude l'activity corrente
                    }
                })
                .setNegativeButton("No", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    private void visualizzaCalcoli() {
        double mediaAritmetica = person.calcolaMediaAritmetica();
        double mediaPonderata = person.calcolaMediaPonderata();
        double votoDiLaurea = person.calcolaVotoDiLaurea();

        textViewMediaAritmetica.setText(String.format(Locale.getDefault(), "%.2f", mediaAritmetica));
        textViewMediaPonderata.setText(String.format(Locale.getDefault(), "%.2f", mediaPonderata));
        textViewVotoDiLaurea.setText(String.format(Locale.getDefault(), "%.0f", votoDiLaurea)+"/110");

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Supponendo che person.getExams() sia stato aggiornato
        adapter.notifyDataSetChanged(); // Aggiorna l'adapter
        visualizzaCalcoli(); // Aggiorna i calcoli delle medie
    }

    public void onDeleteClick(int position) {
        ExamList.getExams().remove(position);
        // Ricalcola e aggiorna le visualizzazioni dei calcoli
        visualizzaCalcoli();

        adapter.notifyItemRemoved(position);
        adapter.notifyDataSetChanged();

        // Aggiorna l'oggetto Person con la lista aggiornata degli esami
        //person.setExams(GlobalExamRegistry.getExams()); // Assicurati che esista un metodo setExams in Person


    }


}
