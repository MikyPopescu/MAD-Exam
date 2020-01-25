package com.example.subiect_examen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.subiect_examen.util.Examen;

public class AdaugaExamenActivity extends AppCompatActivity {
    public static final String ADAUGA_EXAMEN_KEY = "adaugaExamenKey";
    public static final String POZITIE_MODIFICARE = "pozitieModificare";

    private EditText etId;
    private EditText etMaterie;
    private EditText etNrStudenti;
    private EditText etSala;
    private EditText etSupraveghetor;
    private Button btnSalvare;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_examen);


        initializari();

        intent = getIntent();

        if(intent.hasExtra(POZITIE_MODIFICARE)){
            Examen examen=intent.getParcelableExtra(POZITIE_MODIFICARE);
            modifica(examen);
        }
    }

    private void modifica(Examen examen) {
        etId.setText(""+examen.getId());
        etMaterie.setText(examen.getDenumireMaterie());
        etNrStudenti.setText(""+examen.getNumarStudenti());
        etSala.setText(examen.getSala());
        etSupraveghetor.setText(examen.getSupraveghetor());
    }

    private void initializari() {
        etId = findViewById(R.id.et_id);
        etMaterie = findViewById(R.id.et_materie);
        etNrStudenti = findViewById(R.id.et_nr_studenti);
        etSala = findViewById(R.id.et_sala);
        etSupraveghetor = findViewById(R.id.et_supraveghetor);
        btnSalvare = findViewById(R.id.btn_salvare);

        btnSalvare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eValid()) {
                    Examen examen = creezExamenDinInterfata();
                    //Toast.makeText(AdaugaExamenActivity.this, examen.toString(), Toast.LENGTH_SHORT).show();
                    //transfer de param:
                    intent.putExtra(ADAUGA_EXAMEN_KEY,examen);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    private Examen creezExamenDinInterfata() {
        Long id = Long.parseLong(etId.getText().toString());
        String denumireMaterie = etMaterie.getText().toString();
        Integer numarStudenti = Integer.parseInt(etNrStudenti.getText().toString());
        String sala = etSala.getText().toString();
        String supraveghetor = etSupraveghetor.getText().toString();

        return new Examen(id, denumireMaterie, numarStudenti, sala, supraveghetor);
    }

    private boolean eValid() {
        if (etId.getText() == null || etId.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Introduceti id", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etMaterie.getText() == null || etMaterie.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Introduceti materie", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etNrStudenti.getText() == null || etNrStudenti.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Introduceti un nr de studenti", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etSala.getText() == null || etSala.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Introduceti sala", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etSupraveghetor.getText() == null || etSupraveghetor.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Introduceti supraveghetor", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
