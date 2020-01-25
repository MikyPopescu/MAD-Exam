package com.example.subiect_examen.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;


public class PieChartView extends View {
    private Map<String, Integer> source;
    private ArrayList<String> labels;
    Paint paint;
    Random random;

    public PieChartView(Context context, Map<String, Integer> source) {
        super(context);

        this.source = source;
        this.labels = new ArrayList<>(source.keySet());
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        random = new Random();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLUE);
        int sum = 0;
        for (double value : source.values()) {
            sum += value;
        }
        float A = 270;
        canvas.drawArc(200, 200, 800, 800, A, (source.get(labels.get(0)) * 360) / sum, true, paint);
        for (int i = 1; i < labels.size(); i++) {
            int color = Color.argb(100, 1 + random.nextInt(254),
                    1 + random.nextInt(254), 1 + random.nextInt(254));
            paint.setColor(color);
            A += (source.get(labels.get(i - 1)) * 360) / sum;
            canvas.drawArc(200, 200, 800, 800, A, (source.get(labels.get(i))) * 360 / sum, true, paint);
        }
    }
}
