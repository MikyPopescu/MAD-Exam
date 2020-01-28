package com.example.subiectcitat2020;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.subiectcitat2020.database.service.CitatService;
import com.example.subiectcitat2020.network.HttpManager;
import com.example.subiectcitat2020.util.Citat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_ADAUGA_CITAT = 100;
    public static final String POZITIE_MODIFICARE = "pozitieModificare";
    private static final String URL= "http://pdm.ase.ro/examen/citate.json";
    public static final int REQUEST_CODE_MODIFICA_CITAT = 200;
    private Intent intent;
    ListView lvCitate;
    List<Citat> citate = new ArrayList<>();
    int poz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mesaj();
        initializari();
        //getCitateFromDatabase();
    }

    private void getCitateFromDatabase() {
        new CitatService.GetAll(getApplicationContext()) {
            @Override
            protected void onPostExecute(List<Citat> results) {
                // super.onPostExecute(citats);
                if (results != null) {
                    citate.clear();
                    citate.addAll(results);
                    citate.notify();
                }
            }
        }.execute();
    }

    private void insertCitatIntoDb(Citat citat) {
        new CitatService.Insert(getApplicationContext()) {
            @Override
            protected void onPostExecute(Citat result) {
                if (result != null) {
                    citate.add(result);
                    citate.notify();
                }
            }
        }.execute(citat);
    }

    private void updateCitatFromDb(final Citat citat) {
        citat.setId(citate.get(poz).getId());
        new CitatService.Update(getApplicationContext()) {
            @Override
            protected void onPostExecute(Integer result) {
                if (result == 1) {
                    modificaCitat(citat);
                    citate.notify();
                }
            }
        }.execute(citat);
    }

    private void deleteCitatFromDb(final int index) {
        new CitatService.Delete(getApplicationContext()) {
            @Override
            protected void onPostExecute(Integer result) {
                if (result == 1) {
                    citate.remove(index);
                    citate.notify();
                }
            }
        }.execute(citate.get(index));
    }

    private void initializari() {
        lvCitate = findViewById(R.id.id_lv_ma);

        lvCitate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                construiesteAlertDialog(position);
            }
        });

        lvCitate.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //insertCitatIntoDb(citate.get(position));

                return true;
            }
        });
    }

    private void construiesteAlertDialog(final int position) {
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Stergere/modificare element")
                .setMessage("Doriti sa stergeti sau sa modificati un element din LV?")
                .setPositiveButton("MODIFIC", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        poz = position;
                        intent = new Intent(getApplicationContext(), AdaugaCitatActivity.class);
                        intent.putExtra(POZITIE_MODIFICARE, citate.get(position));
                        startActivityForResult(intent, REQUEST_CODE_MODIFICA_CITAT);
                    }
                })
                .setNegativeButton("STERG", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        citate.remove(position);
                        adaugaInListView(citate);
                    }
                })
                .setNeutralButton("INAPOI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create();
        dialog.show();

    }

    private void mesaj() {
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Salut")
                .setMessage("Miky" + "\n" +
                        (new SimpleDateFormat("dd/MM/yyyy", Locale.US))
                                .format(new Date(System.currentTimeMillis())))
                .setPositiveButton("INCHIDE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.meniul_meu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.id_adauga_citat) {
            intent = new Intent(getApplicationContext(), AdaugaCitatActivity.class);
            //startActivity(intent);
            startActivityForResult(intent, REQUEST_CODE_ADAUGA_CITAT);
        }
        if (item.getItemId() == R.id.id_sincronizare_retea) {
           new HttpManager(){
               @Override
               protected void onPostExecute(String s) {
                  super.onPostExecute(s);
                   Toast.makeText(getApplicationContext(),
                           s,
                           Toast.LENGTH_LONG).show();
               }
           }.execute(URL);
          //  Toast.makeText(this, "ciao", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADAUGA_CITAT && resultCode == RESULT_OK && data != null) {
            Citat citat = data.getParcelableExtra(AdaugaCitatActivity.ADAUGA_CITAT_KEY);
            if (citat != null) {
                // Toast.makeText(this, citat.toString(), Toast.LENGTH_SHORT).show();
                citate.add(citat);
                adaugaInListView(citate);
            }
        } else if (requestCode == REQUEST_CODE_MODIFICA_CITAT && resultCode == RESULT_OK && data != null) {
            Citat citat = data.getParcelableExtra(AdaugaCitatActivity.ADAUGA_CITAT_KEY);
            if (citat != null) {
                modificaCitat(citat);
                // updateCitatFromDb(citat);
                adaugaInListView(citate);
            }
        }
    }

    private void modificaCitat(Citat citat) {
        citate.get(poz).setAutor(citat.getAutor());
        citate.get(poz).setText(citat.getText());
        citate.get(poz).setNumarAprecieri(citat.getNumarAprecieri());
        citate.get(poz).setCategorie(citat.getCategorie());
    }

    private void adaugaInListView(List<Citat> citate) {
        ArrayAdapter<Citat> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, citate);
        lvCitate.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
