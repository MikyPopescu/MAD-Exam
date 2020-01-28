package com.example.examprep2.chart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.PrimaryKey;

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

public class PieChartActivity extends AppCompatActivity {
    List<Jucator> jucators;
    Map<String, Integer> source;
    LinearLayout layout;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        intent = getIntent();
        jucators = intent.getParcelableArrayListExtra(MainActivity.CODE_SEND_CHART);
        source = getSource(jucators);
        layout = findViewById(R.id.layoutBar);
        layout.addView(new PieChartView(getApplicationContext(), source));
    }

    private Map<String, Integer> getSource(List<Jucator> jucators) {
        if (jucators == null || jucators.isEmpty()) {
            return new HashMap<>();
        }

        Map<String, Integer> results = new HashMap<>();

        for (Jucator j : jucators) {
            if (results.containsKey(j.getNume())) {
                results.put(j.getNume(),
                        results.get(j.getNume()) + 1);
            } else {
                results.put(j.getNume(), 1);
            }
        }
        return results;
    }

}
