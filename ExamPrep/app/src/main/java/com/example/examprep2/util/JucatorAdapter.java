package com.example.examprep2.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.examprep2.AdaugaJucatorActivity;
import com.example.examprep2.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JucatorAdapter extends ArrayAdapter<Jucator> {
    private Context context;
    private int resource;
    private List<Jucator> jucatori;
    private LayoutInflater layoutInflater;

    public JucatorAdapter(@NonNull Context context, int resource, List<Jucator> jucatori, LayoutInflater layoutInflater) {
        super(context, resource);

        this.context = context;
        this.resource = resource;
        this.jucatori = jucatori;
        this.layoutInflater = layoutInflater;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // return super.getView(position, convertView, parent);
        View view = layoutInflater.inflate(resource, parent, false);
        Jucator jucator = jucatori.get(position);
        if (jucator != null) {
            adaugaNume(view, jucator.getNume());
            adaugaNumar(view, jucator.getNumar());
            adaugaZiNastere(view, jucator.getDataNastere());
            adaugaPozitie(view, jucator.getPozitie());
            adaugaManaFavorita(view, jucator.getManaFavorita());
        }
        return view;
    }

    private void adaugaManaFavorita(View view, String manaFavorita) {
        TextView tvManaFav = view.findViewById(R.id.lv_players_row_tv_fav_hand);
        if (manaFavorita != null && !manaFavorita.trim().isEmpty()) {
            tvManaFav.setText(manaFavorita);
        } else {
            tvManaFav.setText("-");
        }
    }

    private void adaugaPozitie(View view, String pozitie) {
        TextView tvPozitie = view.findViewById(R.id.lv_players_row_tv_position);
        if (pozitie != null && !pozitie.trim().isEmpty()) {
            tvPozitie.setText(pozitie);
        } else {
            tvPozitie.setText("-");
        }
    }

    private void adaugaZiNastere(View view, Date dataNastere) {
        TextView tvZiNastere = view.findViewById(R.id.lv_players_row_tv_birthday);
        if (dataNastere != null) {
            tvZiNastere.setText(new SimpleDateFormat(AdaugaJucatorActivity.DATE_FORMAT, Locale.US)
                    .format(dataNastere));
        } else {
            tvZiNastere.setText("-");
        }
    }

    private void adaugaNumar(View view, Integer numar) {
        TextView tvNumar = view.findViewById(R.id.lv_players_row_tv_number);
        if (numar != null) {
            tvNumar.setText(String.valueOf(numar));
        } else {
            tvNumar.setText("-");
        }
    }

    private void adaugaNume(View view, String nume) {
        TextView tvNume = view.findViewById(R.id.lv_players_row_tv_name);
        if (nume != null && !nume.trim().isEmpty()) {
            tvNume.setText(nume);
        } else {
            tvNume.setText("-");
        }
    }
}
