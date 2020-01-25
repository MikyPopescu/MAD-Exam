package com.example.subiect_examen.chart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.subiect_examen.MainActivity;
import com.example.subiect_examen.R;
import com.example.subiect_examen.util.Examen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieChartActivity extends AppCompatActivity {
    List<Examen> examene = new ArrayList<>();
    LinearLayout layout;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);


        intent = getIntent();
        examene = intent.getParcelableArrayListExtra(MainActivity.CODE_SEND_CHART);
        for (Examen e : examene) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        Map<String, Integer> source = getSource(examene);
        layout = findViewById(R.id.layoutPie);
        layout.addView(new PieChartView(getApplicationContext(), source));
    }

    private Map<String, Integer> getSource(List<Examen> examene) {
        if (examene == null || examene.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, Integer> results = new HashMap<>();
        for (Examen e : examene) {
            if (results.containsKey(e.getDenumireMaterie())) {
                results.put(e.getDenumireMaterie(), results.get(e.getDenumireMaterie()) + 1);
            } else {
                results.put(e.getDenumireMaterie(), 1);
            }
        }
        return results;
    }
}
