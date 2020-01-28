package com.example.subiectcitat2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.subiectcitat2020.util.Citat;

public class AdaugaCitatActivity extends AppCompatActivity {
    public static final String ADAUGA_CITAT_KEY = "adaugaCitatKey";
    public static final String POZITIE_MODIFICARE = "pozitieModificare";
    private EditText etAutor;
    private EditText etText;
    private EditText etNrAprecieri;
    private Spinner spnCategorie;
    private Button btnSalvare;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_citat);

        initializari();

        intent=getIntent();

        //MODIFICARE
        if(intent.hasExtra(POZITIE_MODIFICARE)){
            Citat citat = intent.getParcelableExtra(POZITIE_MODIFICARE);
            modifica(citat);
        }
    }

    private void modifica(Citat citat) {
    etAutor.setText(citat.getText());
    etText.setText(citat.getText());
    etNrAprecieri.setText(""+citat.getNumarAprecieri());
    if(citat.getCategorie()!=null){
        modificaSpinner(citat);
    }
    }

    private void modificaSpinner(Citat citat) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spnCategorie.getAdapter();
        for(int i=0;i<adapter.getCount();i++){
            if(adapter.getItem(i).equals(citat.getCategorie())){
                spnCategorie.setSelection(i);
                break;
            }
        }
    }

    private void initializari() {
        etAutor = findViewById(R.id.et_autor);
        etText = findViewById(R.id.et_text);
        etNrAprecieri = findViewById(R.id.et_nr_aprecieri);
        spnCategorie = findViewById(R.id.spn_categorie);
        btnSalvare = findViewById(R.id.btn_salvare);

        //populare spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.adauga_valori,
                R.layout.support_simple_spinner_dropdown_item);
        spnCategorie.setAdapter(adapter);

        btnSalvare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eValid()) {
                    Citat citat= creezCitatDinInterfata();
                    Toast.makeText(getApplicationContext(),citat.toString(),Toast.LENGTH_LONG).show();

                    intent.putExtra(ADAUGA_CITAT_KEY,citat);
                    setResult(RESULT_OK,intent);
                    finish();

                }
            }
        });

    }

    private Citat creezCitatDinInterfata() {
        String autor= etAutor.getText().toString();
        String text=etText.getText().toString();
        Integer nrAprecieri = Integer.parseInt(etNrAprecieri.getText().toString());
        String categ=spnCategorie.getSelectedItem().toString();

        return new Citat(autor,text,nrAprecieri,categ);
    }

    private boolean eValid() {
        if (etAutor.getText() == null || etAutor.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Introduceti autor", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etText.getText() == null || etText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Introduceti text", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etNrAprecieri.getText() == null || etNrAprecieri.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Introduceti nr aprecieri", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
