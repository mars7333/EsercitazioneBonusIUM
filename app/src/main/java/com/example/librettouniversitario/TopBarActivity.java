package com.example.librettouniversitario;


import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

public class TopBarActivity extends AppCompatActivity {


    protected void indietro() {
        ImageView indietro = findViewById(R.id.backButton);

        indietro.setOnClickListener(view -> {
            // Chiudi l'Activity corrente
            finish();
        });
    }
}


