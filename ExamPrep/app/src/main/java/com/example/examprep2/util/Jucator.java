package com.example.examprep2.util;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.examprep2.AdaugaJucatorActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "jucatori")
public class Jucator implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    long id;
    @ColumnInfo(name = "nume")
    String nume;
    @ColumnInfo(name = "numar")
    Integer numar;
    @ColumnInfo(name = "dataNastere")
    Date dataNastere;
    @ColumnInfo(name = "pozitie")
    String pozitie;
    @ColumnInfo(name = "manaFavorita")
    String manaFavorita;

    public Jucator(long id, String nume, Integer numar, Date dataNastere, String pozitie, String manaFavorita) {
        this.id = id;
        this.nume = nume;
        this.numar = numar;
        this.dataNastere = dataNastere;
        this.pozitie = pozitie;
        this.manaFavorita = manaFavorita;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Ignore
    public Jucator(String nume, Integer numar, Date dataNastere, String pozitie, String manaFavorita) {
        this.nume = nume;
        this.numar = numar;
        this.dataNastere = dataNastere;
        this.pozitie = pozitie;
        this.manaFavorita = manaFavorita;
    }

    protected Jucator(Parcel in) {
        this.nume = in.readString();
        this.numar = in.readInt();
        try {
            this.dataNastere = new SimpleDateFormat(AdaugaJucatorActivity.DATE_FORMAT, Locale.US)
                    .parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.pozitie = in.readString();
        this.manaFavorita = in.readString();
    }

    public static final Creator<Jucator> CREATOR = new Creator<Jucator>() {
        @Override
        public Jucator createFromParcel(Parcel in) {
            return new Jucator(in);
        }

        @Override
        public Jucator[] newArray(int size) {
            return new Jucator[size];
        }
    };

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getNumar() {
        return numar;
    }

    public void setNumar(Integer numar) {
        this.numar = numar;
    }

    public Date getDataNastere() {
        return dataNastere;
    }

    public void setDataNastere(Date dataNastere) {
        this.dataNastere = dataNastere;
    }

    public String getPozitie() {
        return pozitie;
    }

    public void setPozitie(String pozitie) {
        this.pozitie = pozitie;
    }

    public String getManaFavorita() {
        return manaFavorita;
    }

    public void setManaFavorita(String manaFavorita) {
        this.manaFavorita = manaFavorita;
    }

    @Override
    public String toString() {
        return "Jucator{" +
                "nume='" + nume + '\'' +
                ", numar=" + numar +
                ", dataNastere=" + dataNastere +
                ", pozitie='" + pozitie + '\'' +
                ", manaFavorita='" + manaFavorita + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nume);
        dest.writeInt(numar);
////////////////////////////////////////////////////////////
        String dataStr = this.dataNastere != null ?
                new SimpleDateFormat(AdaugaJucatorActivity.DATE_FORMAT, Locale.US)
                        .format(this.dataNastere) : null;
        dest.writeString(dataStr);
        dest.writeString(pozitie);
        dest.writeString(manaFavorita);

    }
}
