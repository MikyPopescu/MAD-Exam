package com.example.examprep2.chart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.examprep2.MainActivity;
import com.example.examprep2.R;
import com.example.examprep2.util.Jucator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarChartActivity extends AppCompatActivity {
    List<Jucator> jucatori = new ArrayList<>();
    LinearLayout layout;
    Intent intent;
    Map<String, Integer> source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);


        intent = getIntent();
        jucatori = intent.getParcelableArrayListExtra(MainActivity.CODE_SEND_CHART);
        for (Jucator j : jucatori) {
            Toast.makeText(getApplicationContext(), j.toString(), Toast.LENGTH_LONG).show();
        }

        source = getSource(jucatori);
        layout = findViewById(R.id.layoutBar);
        layout.addView(new BarChartView(getApplicationContext(), source));
    }


    private Map<String, Integer> getSource(List<Jucator> jucatori) {
        if (jucatori == null || jucatori.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, Integer> results = new HashMap<>();
        for (Jucator j : jucatori) {
            if (results.containsKey(j.getPozitie())) {
                results.put(j.getPozitie(), results.get(j.getPozitie()) + 1);
            } else {
                results.put(j.getPozitie(), 1);
            }
        }
        return results;

    }
}
