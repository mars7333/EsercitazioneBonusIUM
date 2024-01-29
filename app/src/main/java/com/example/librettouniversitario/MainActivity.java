package com.example.librettouniversitario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.PasswordTransformationMethod;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String PERSON_PATH = "com.example.librettouniversitario.person";

    protected EditText name, surname, password;
    protected TextView error;
    protected Button login;
    protected Button registration;
    protected Person person;
    protected Intent result;
    protected ImageView showPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        registration = findViewById(R.id.registration);
        error = findViewById(R.id.error);
        showPassword = findViewById(R.id.show_password);

        String registrationText = "Registrati";

        SpannableString content = new SpannableString(registrationText);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        registration.setText(content);

        registration.setOnClickListener(v -> {
            result = new Intent(MainActivity.this, RegistrationActivity.class);
            startActivity(result); // Lancio l'intento
        });


        person = new Person();


        login.setOnClickListener(v -> {
            if (checkInput()) { // Se gli input sono validi
                // Creo l'intento di passare da MainActivity a ResultActivity
                result = new Intent(MainActivity.this, ResultActivity.class);
                updatePerson();
                result.putExtra(PERSON_PATH, person); // Passo dati all'intento
                startActivity(result); // Lancio l'intento
            }
        });

        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostra o nascondi la password quando l'ImageView viene cliccato
                togglePasswordVisibility(password);
            }
        });

    }

    private void togglePasswordVisibility(EditText editText) {
        // Ottieni il tipo di trasformazione corrente
        boolean isPasswordVisible = editText.getTransformationMethod() instanceof PasswordTransformationMethod;

        // Inverti il tipo di trasformazione
        editText.setTransformationMethod(isPasswordVisible ? null : new PasswordTransformationMethod());
    }


    // Funzione che mostra il DatePickerFragment tramite il DialogFragment
    protected void showDialog() {
        DialogFragment dialogFragment = DatePickerFragment.newInstance();
        dialogFragment.show(getSupportFragmentManager(), "dialog");
    }


    protected void updatePerson() {
        person.setName(name.getText().toString());
        person.setSurname(surname.getText().toString());

    }

    protected boolean checkInput() {
        int errors = 0;

        if (name.getText() == null || name.getText().length() == 0) {
            errors++;
            name.setError("Inserire un nome valido");
        } else if (!name.getText().toString().matches("[a-zA-Z]+")) {
            // Verifica se il testo contiene solo lettere, senza numeri o simboli
            errors++;
            name.setError("Il nome non può contenere numeri o simboli");
        } else
            name.setError(null);

        if (surname.getText() == null || surname.getText().length() == 0) {
            errors++;
            surname.setError("Inserire un cognome valido");
        } else if (!surname.getText().toString().matches("[a-zA-Z]+")) {
            // Verifica se il testo contiene solo lettere, senza numeri o simboli
            errors++;
            surname.setError("Il nome non può contenere numeri o simboli");
        } else
            surname.setError(null);


        String passwordStr = password.getText().toString();

        if (passwordStr == null || passwordStr.length() < 8) {
            errors++;
            password.setError("La password deve avere almeno 8 caratteri");
        } else if (!passwordStr.matches(".*\\d.*")) {
            errors++;
            password.setError("La password deve contenere almeno un numero");
        } else if (!passwordStr.matches(".*[@*#$%^&+=].*")) {
            errors++;
            password.setError("La password deve contenere almeno un simbolo: @*#$%^&+=");
        } else {
            password.setError(null);
        }


        if (errors > 0 ) {
            error.setVisibility(View.VISIBLE); // Mostro la scritta di errore generale
            error.setText(R.string.error);
        } else
            error.setVisibility(View.GONE); // Nascondo la scritta di errore generale

        return errors == 0; // True se non vi sono errori, altrimenti false
    }
}