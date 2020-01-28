package com.example.examprep2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.examprep2.database.service.JucatorService;
import com.example.examprep2.util.Jucator;
import com.example.examprep2.util.JucatorAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListaJucatoriActivity extends AppCompatActivity {
    ListView lvJucatori;
    List<Jucator> jucatori = new ArrayList<>();
    Intent intent;
    public static final String LISTA_DE_JUCATORI = "listaDeJucatori";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_jucatori);

        initializari();
        intent = getIntent();
        jucatori = intent.getParcelableArrayListExtra(LISTA_DE_JUCATORI);
        adaugaInLv(jucatori);
       // notifyInternal();
//        insertPlayerIntoDatabase(jucatori.get(0));
//        getPlayersFromDatabase();
//        updatePlayerIntoDatabse(jucatori.get(0));
//        deletePlayerFromDatabase(0);
    }

    private void deletePlayerFromDatabase(final int index) {
        new JucatorService.Delete(getApplicationContext()) {
            @Override
            protected void onPostExecute(Integer result) {
                if (result == 1) {
                    jucatori.remove(index);
                    jucatori.notify();
                }
            }
        }.execute(jucatori.get(index));
    }

    private void updatePlayerIntoDatabse(final Jucator jucator) {
        jucator.setId(jucatori.get(0).getId());
        new JucatorService.Update(getApplicationContext()) {
            @Override
            protected void onPostExecute(Integer result) {
                if (result == 1) {
                    //  modificaJucator(jucator);
                    jucatori.notify();
                }
            }
        }.execute(jucator);
    }

    private void insertPlayerIntoDatabase(Jucator jucator) {
        new JucatorService.Insert(getApplicationContext()) {
            @Override
            protected void onPostExecute(Jucator result) {
                if (result != null) {
                    jucatori.add(result);
                    jucatori.notify();
                }
            }
        }.execute(jucator);
    }

    private void getPlayersFromDatabase() {
        new JucatorService.GetAll(getApplicationContext()) {
            @Override
            protected void onPostExecute(List<Jucator> results) {
                super.onPostExecute(results);
                if (results != null) {
                    jucatori.clear();
                    jucatori.addAll(results);
                    jucatori.notify();
                }
            }
        }.execute();
    }

    private void initializari() {
        lvJucatori = findViewById(R.id.lv_jucatori_lista_jucatori);
    }

    private void adaugaInLv(List<Jucator> jucatori) {
        //adapter normal
        ArrayAdapter<Jucator> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, jucatori);
        lvJucatori.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //adapter personalizat -- "merge ca in seminar" MINCIUNI
//        JucatorAdapter adapter = new JucatorAdapter(getApplicationContext(),
//                R.layout.lv_row_view,
//                jucatori,
//                getLayoutInflater());
//        lvJucatori.setAdapter(adapter);
//        notifyInternal();

    }

    //update adapter
    public void notifyInternal() {
        JucatorAdapter jucatorAdapter =
                (JucatorAdapter) lvJucatori.getAdapter();
        jucatorAdapter.notifyDataSetChanged();
    }

}
