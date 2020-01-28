package com.example.subiectcitat2020.util;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "citate")
public class Citat implements Parcelable {
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "id")
    long id;
    @ColumnInfo(name = "autor")
    String autor;
    @ColumnInfo(name = "text")
    String text;
    @ColumnInfo(name = "nrAprecieri")
    Integer numarAprecieri;
    @ColumnInfo(name = "categorie")
    String categorie;

    public Citat(long id, String autor, String text, Integer numarAprecieri, String categorie) {
        this.id = id;
        this.autor = autor;
        this.text = text;
        this.numarAprecieri = numarAprecieri;
        this.categorie = categorie;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Ignore
    public Citat(String autor, String text, Integer numarAprecieri, String categorie) {
        this.autor = autor;
        this.text = text;
        this.numarAprecieri = numarAprecieri;
        this.categorie = categorie;
    }

    protected Citat(Parcel in) {
        this.autor=in.readString();
        this.text=in.readString();
        this.numarAprecieri=in.readInt();
        this.categorie=in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(autor);
      dest.writeString(text);
      dest.writeInt(numarAprecieri);
      dest.writeString(categorie);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Citat> CREATOR = new Creator<Citat>() {
        @Override
        public Citat createFromParcel(Parcel in) {
            return new Citat(in);
        }

        @Override
        public Citat[] newArray(int size) {
            return new Citat[size];
        }
    };

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getNumarAprecieri() {
        return numarAprecieri;
    }

    public void setNumarAprecieri(Integer numarAprecieri) {
        this.numarAprecieri = numarAprecieri;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "Citat{" +
                "autor='" + autor + '\'' +
                ", text='" + text + '\'' +
                ", numarAprecieri=" + numarAprecieri +
                ", categorie='" + categorie + '\'' +
                '}';
    }
}
