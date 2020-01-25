package com.example.subiect_examen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.subiect_examen.chart.PieChartActivity;
import com.example.subiect_examen.database.service.ExamenService;
import com.example.subiect_examen.network.HttpManager;
import com.example.subiect_examen.util.Examen;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_ADAUGA_EXAMEN = 100;
    public static final String POZITIE_MODIFICARE = "pozitieModificare";
    public static final int REQUEST_CODE_MODIFICA_EXAMEN = 200;
    public static final String CODE_SEND_CHART = "trimit_grafic";
    private Intent intent;
    ListView lvExamene;
    List<Examen> examene = new ArrayList<>();
    int poz;
    Button btnSalvareBD;
    private static final String URL = "https://api.myjson.com/bins/16f54z";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializari();
    }

    private void initializari() {
        lvExamene = findViewById(R.id.lv_examene);

        lvExamene.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                poz = position;
                intent = new Intent(getApplicationContext(), AdaugaExamenActivity.class);
                intent.putExtra(POZITIE_MODIFICARE, examene.get(position));
                startActivityForResult(intent, REQUEST_CODE_MODIFICA_EXAMEN);
            }
        });


        lvExamene.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                construiesteAlertDialog(position);
                return true;
            }
        });

//        btnSalvareBD.findViewById(R.id.btn_bd);
//        //nu mai zic nimic de materia asta...
//        btnSalvareBD.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //salvare in fisier
//                FileOutputStream fileOutputStream = null;
//                try {
//                    fileOutputStream = openFileOutput("fisier.txt", MODE_APPEND);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
//                try {
//                    outputStreamWriter.write("aici ar trebui sa fie insertul".toString());
//                    outputStreamWriter.write("\n");
//                    outputStreamWriter.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }


    //select
    private void getExameneFromDB() {
        new ExamenService.GetAll(getApplicationContext()) {
            @Override
            protected void onPostExecute(List<Examen> results) {
                if (results != null) {
                    examene.clear();
                    examene.addAll(results);
                }
            }
        }.execute();
    }

    //insert
    private void insertExamenIntoDB(Examen examen) {
        new ExamenService.Insert(getApplicationContext()) {
            @Override
            protected void onPostExecute(Examen result) {
                if (result != null) {
                    examene.add(result);
                }
            }
        }.execute(examen);
    }

    private void construiesteAlertDialog(final int position) {
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Stergere!!!")
                .setMessage("Doriti sa stergeti examenul " + position + " ?")
                .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        examene.remove(position);
                        adaugaInLv(examene);

                    }
                })
                .setNegativeButton("NU", new DialogInterface.OnClickListener() {
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
        getMenuInflater().inflate(R.menu.meniu_nou, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.id_adauga_examen) {
            intent = new Intent(getApplicationContext(), AdaugaExamenActivity.class);
            // startActivity(intent);
            startActivityForResult(intent, REQUEST_CODE_ADAUGA_EXAMEN);
        }
        if (item.getItemId() == R.id.id_sincronizare_retea) {
            new HttpManager() {
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }
            }.execute(URL);
        }
        if (item.getItemId() == R.id.id_grafic) {
            intent = new Intent(getApplicationContext(), PieChartActivity.class);
            intent.putParcelableArrayListExtra(CODE_SEND_CHART, (ArrayList<? extends Parcelable>) examene);
            startActivity(intent);

        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADAUGA_EXAMEN && resultCode == RESULT_OK && data != null) {
            Examen examen = data.getParcelableExtra(AdaugaExamenActivity.ADAUGA_EXAMEN_KEY);
            if (examen != null) {
                // Toast.makeText(this, examen.toString(), Toast.LENGTH_SHORT).show();
                examene.add(examen);
                adaugaInLv(examene);
            }
        } else if (requestCode == REQUEST_CODE_MODIFICA_EXAMEN && resultCode == RESULT_OK && data != null) {
            Examen examen = data.getParcelableExtra(AdaugaExamenActivity.ADAUGA_EXAMEN_KEY);
            if (examen != null) {

                modificaExamen(examen);
                adaugaInLv(examene);
            }
        }
    }

    private void modificaExamen(Examen examen) {
        examene.get(poz).setId(examen.getId());
        examene.get(poz).setDenumireMaterie(examen.getDenumireMaterie());
        examene.get(poz).setNumarStudenti(examen.getNumarStudenti());
        examene.get(poz).setSala(examen.getSala());
        examene.get(poz).setSupraveghetor(examen.getSupraveghetor());
    }

    private void adaugaInLv(List<Examen> examene) {
        ArrayAdapter<Examen> adapter = new ArrayAdapter<>
                (getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        examene);
        lvExamene.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
