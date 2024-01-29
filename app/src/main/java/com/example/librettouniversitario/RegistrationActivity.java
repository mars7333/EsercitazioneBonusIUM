package com.example.librettouniversitario;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegistrationActivity extends TopBarActivity {
    public static final String PERSON_PATH = "com.example.myapplication.person";
    protected Person person;
    protected EditText name, surname, birthdate, password, repeat_password;

    private ImageView datePickerButton, showPassword;
    protected TextView error;
    protected Button insert;
    protected Intent result;
    protected Calendar calendar;
    protected SimpleDateFormat simpleDateFormat;
    protected Serializable object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // in questa classe viene gestita la registrazione

        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        birthdate = findViewById(R.id.birthdate);
        password = findViewById(R.id.password);
        repeat_password = findViewById(R.id.repeat_password);
        insert = findViewById(R.id.sign);
        error = findViewById(R.id.error2);
        showPassword = findViewById(R.id.show_password);
        person = new Person();

        // Inizializzazione del datePickerButton
        datePickerButton = findViewById(R.id.datePickerButton);
        datePickerButton.setOnClickListener(v -> showDatePickerDialog());


        insert.setOnClickListener(v -> {
            if (validateInput() && checkInput()) { // Se gli input sono validi
                // Creo l'intento di passare da MainActivity a ResultActivity
                result = new Intent(RegistrationActivity.this, MainActivity.class);
                updatePerson();
                Person.addPerson(person);
                result.putExtra(PERSON_PATH, person); // Passo dati all'intento
                startActivity(result); // Lancio l'intento
            }
        });

        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostra o nascondi la password quando l'ImageView viene cliccato
                togglePasswordVisibility(password);
                togglePasswordVisibility(repeat_password);
            }
        });

        indietro();
    }

    private void togglePasswordVisibility(EditText editText) {
        // Ottieni il tipo di trasformazione corrente
        boolean isPasswordVisible = editText.getTransformationMethod() instanceof PasswordTransformationMethod;

        // Inverti il tipo di trasformazione
        editText.setTransformationMethod(isPasswordVisible ? null : new PasswordTransformationMethod());
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Aggiorna il campo di testo con la data selezionata
                    String formattedDate = String.format("%d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    birthdate.setText(formattedDate);
                },
                year, month, day);

        // Impedisci la selezione di date future
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        datePickerDialog.show();
    }

    private boolean validateInput() {
        // Validazione della data
        if ((birthdate.getText() == null || birthdate.getText().toString().isEmpty())) {
            Toast.makeText(this, "Inserire la data di nascita", Toast.LENGTH_LONG).show();
            return false;
        }

        // Calcolo dell'età
        int age = calculateAge(birthdate.getText().toString());
        if (age < 0) {
            Toast.makeText(this, "Errore nel calcolo dell'età", Toast.LENGTH_LONG).show();
            return false;
        }

        String firstName = name.getText().toString().trim();
        String lastName = surname.getText().toString().trim();
        String passwordD = password.getText().toString();

        // Controlla se i campi sono vuoti
        if (firstName.isEmpty()) {
            Toast.makeText(this, "Il campo nome non può essere vuoto", Toast.LENGTH_SHORT).show();
            return false;
        } else if (lastName.isEmpty()) {
            Toast.makeText(this, "Il campo cognome non può essere vuoto", Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordD.isEmpty()) {
            Toast.makeText(this, "Il campo password non può essere vuoto", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private int calculateAge(String birthDateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Calendar birthDate = Calendar.getInstance();
            birthDate.setTime(sdf.parse(birthDateString));

            Calendar today = Calendar.getInstance();

            int age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

            // Se l'anniversario di quest'anno non è ancora avvenuto, sottrarre 1
            if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)){
                age--;
            }

            return age;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1; // Ritorna -1 in caso di errore
        }
    }


    protected void updatePerson() {
        person.setName(name.getText().toString());
        person.setSurname(surname.getText().toString());
        person.setPassword(password.getText().toString());
        person.setBirthdate(birthdate.getText().toString());

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
        String passwordStr2 =repeat_password.getText().toString();

        if (passwordStr == null ||  passwordStr2 == null || passwordStr.length() < 8 || passwordStr2.length() < 8) {
            errors++;
            password.setError("La password deve avere almeno 8 caratteri");
        } else if (!(passwordStr.matches(".*\\d.*") || (passwordStr2.matches(".*\\d.*")))) {
            errors++;
            password.setError("La password deve contenere almeno un numero");
        } else if (!(passwordStr.matches(".*[@*#$%^&+=].*") || passwordStr2.matches(".*[@*#$%^&+=].*"))) {
            errors++;
            password.setError("La password deve contenere almeno un simbolo tra: @*#$%^&+=");
        } else if (passwordStr2.isEmpty() || !(passwordStr.equals(passwordStr2))) {
            Toast.makeText(this, "Le password non corrispondono", Toast.LENGTH_SHORT).show();
            return false;
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

