package com.example.examprep2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.examprep2.util.Jucator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdaugaJucatorActivity extends AppCompatActivity {
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String ADAUGA_JUCATOR_KEY = "adaugaJucatorKey";
    EditText etNume;
    EditText etNumar;
    EditText etData;
    Spinner spnPozitii;
    RadioGroup rgManaFav;
    Button btnSalvare;
    Intent intent;
//
public static final String POZITIE_MODIFICARE = "pozitie_modificare";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_jucator);

        initializari();
        intent = getIntent();

        //
        if (intent.hasExtra(POZITIE_MODIFICARE)) {
            Jucator jucator = intent.getParcelableExtra(POZITIE_MODIFICARE);
            modifica(jucator);
        }

    }
//
    private void modifica(Jucator jucator) {
        etNume.setText(jucator.getNume());
        etNumar.setText(""+jucator.getNumar());
        if(jucator.getDataNastere()!=null){
            etData.setText(new SimpleDateFormat(DATE_FORMAT,Locale.US)
                            .format(jucator.getDataNastere()));
        }
        if(jucator.getPozitie()!=null){
            modificaSpinner(jucator);
        }
        if(jucator.getManaFavorita()!=null){
            modificaRadioGroup(jucator);
        }

    }

    private void modificaRadioGroup(Jucator jucator) {
        if(jucator.getManaFavorita().equals("Stanga")){
            rgManaFav.check(R.id.rb_stanga);
        }
        else{
            rgManaFav.check(R.id.rb_dreapta);
        }
    }

    private void modificaSpinner(Jucator jucator) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spnPozitii.getAdapter();
        for(int i=0;i<adapter.getCount();i++){
            if(adapter.getItem(i).equals(jucator.getPozitie())){
                spnPozitii.setSelection(i);
                break;
            }
        }
    }

    private void initializari() {
        etNume = findViewById(R.id.et_nume);
        etNumar = findViewById(R.id.et_numar);
        etData = findViewById(R.id.et_data);
        spnPozitii = findViewById(R.id.spn_pozitii);
        rgManaFav = findViewById(R.id.rg_mana_fav);
        btnSalvare = findViewById(R.id.btn_salveaza);

        //populare spn
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.adauga_valori,
                R.layout.support_simple_spinner_dropdown_item);
        spnPozitii.setAdapter(adapter);

        //onclick
        btnSalvare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eValid()) {
                    Jucator j = creezJucatorDinInterfata();
                    Toast.makeText(AdaugaJucatorActivity.this, j.toString(), Toast.LENGTH_SHORT).show();
                    //transfer param
                    intent.putExtra(ADAUGA_JUCATOR_KEY, j);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private Jucator creezJucatorDinInterfata() {
        String nume = etNume.getText().toString();
        Integer numar = Integer.parseInt(etNumar.getText().toString());
        Date dataN = null;
        try {
            dataN = new SimpleDateFormat(DATE_FORMAT, Locale.US)
                    .parse(etData.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String poz = spnPozitii.getSelectedItem().toString();
        RadioButton rbMana = findViewById(rgManaFav.getCheckedRadioButtonId());
        String mana = rbMana.getText().toString();

        return new Jucator(nume, numar, dataN, poz, mana);
    }

    private boolean eValid() {
        if (etNume.getText() == null || etNume.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Introduceti nume", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etNumar.getText() == null || etNumar.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Introduceti numar", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etData.getText() == null || etData.getText().toString().trim().isEmpty() || !validareData(etData.getText().toString())) {
            Toast.makeText(this, "Introduceti data dd/mm/yyyy", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validareData(String strData) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        try {
            sdf.parse(strData);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
