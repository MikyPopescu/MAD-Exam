package com.example.examprep2;

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
import android.widget.ListView;
import android.widget.Toast;

import com.example.examprep2.chart.BarChartActivity;
import com.example.examprep2.chart.PieChartActivity;
import com.example.examprep2.network.HttpManager;
import com.example.examprep2.network.HttpResponse;
import com.example.examprep2.util.Jucator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_ADAUGA_JUCATOR = 100;
    public static final String LISTA_DE_JUCATORI = "listaDeJucatori";
    public static final String POZITIE_MODIFICARE = "pozitie_modificare";
    public static final int REQUEST_CODE_MODIFICA_JUCATOR = 200;
    //
    public final static String CODE_SEND_CHART = "cod_add_player_chart";


    Intent intent;
    List<Jucator> jucatori = new ArrayList<>();
    ListView lvJucatori;
    int poz;
    private static final String URL = "https://api.myjson.com/bins/16f54z";


    private HttpResponse httpResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // mesaj();
        initializari();
    }

    private void mesaj() {
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Nu imi place DAM")
                .setMessage("Miky" + "\n"
                        + (new SimpleDateFormat("dd/mm/yyyy", Locale.US))
                        .format(new Date(System.currentTimeMillis())))
                .setPositiveButton("INCHIDE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create();
        dialog.show();
    }

    private void initializari() {
        lvJucatori = findViewById(R.id.lv_jucatori_main_activity);


        lvJucatori.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                poz = position;
                intent = new Intent(getApplicationContext(), AdaugaJucatorActivity.class);
                intent.putExtra(POZITIE_MODIFICARE, jucatori.get(position));
                startActivityForResult(intent, REQUEST_CODE_MODIFICA_JUCATOR);
            }
        });

        lvJucatori.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                jucatori.remove(position);
                adaugaInLv(jucatori);
                return true;
            }
        });
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
        if (item.getItemId() == R.id.id_transfer) {
            // Toast.makeText(this, "Transfer param", Toast.LENGTH_SHORT).show();
            intent = new Intent(getApplicationContext(), AdaugaJucatorActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADAUGA_JUCATOR);
        }
        if (item.getItemId() == R.id.id_lista) {
            intent = new Intent(getApplicationContext(), ListaJucatoriActivity.class);
            intent.putParcelableArrayListExtra(LISTA_DE_JUCATORI, (ArrayList<? extends Parcelable>) jucatori);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.id_retea) {
            new HttpManager() {
                @Override
                protected void onPostExecute(String s) { //s-jsonu
                    super.onPostExecute(s);
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
//                    httpResponse = JSONParser.parseJson(s);
//                    if(httpResponse!=null){
//                        Toast.makeText(getApplicationContext(),httpResponse.toString(),Toast.LENGTH_LONG).show();
//                    }
//                    else{
//                        Toast.makeText(getApplicationContext(),"Nu merge",Toast.LENGTH_LONG).show();
//                    }
                }
            }.execute(URL);
        }

        if (item.getItemId() == R.id.id_grafic) {
            intent = new Intent(getApplicationContext(), BarChartActivity.class);
            intent.putParcelableArrayListExtra(CODE_SEND_CHART, (ArrayList<? extends Parcelable>) jucatori);
            startActivity(intent);

        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADAUGA_JUCATOR && resultCode == RESULT_OK && data != null) {
            Jucator j = data.getParcelableExtra(AdaugaJucatorActivity.ADAUGA_JUCATOR_KEY);
            if (j != null) {

                jucatori.add(j);
                adaugaInLv(jucatori);
            }

        } else if (requestCode == REQUEST_CODE_MODIFICA_JUCATOR && resultCode == RESULT_OK && data != null) {
            Jucator j = data.getParcelableExtra(AdaugaJucatorActivity.ADAUGA_JUCATOR_KEY);
            if (j != null) {
                modificaJucator(j);
                adaugaInLv(jucatori);
            }

        }
    }


    private void modificaJucator(Jucator j) {
        jucatori.get(poz).setNume(j.getNume());
        jucatori.get(poz).setNumar(j.getNumar());
        jucatori.get(poz).setDataNastere(j.getDataNastere());
        jucatori.get(poz).setManaFavorita(j.getManaFavorita());


    }

    private void adaugaInLv(List<Jucator> jucatori) {
        ArrayAdapter<Jucator> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, jucatori);
        lvJucatori.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}
