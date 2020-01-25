package com.example.subiect_examen.util;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "examene")
public class Examen implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    Long id;
    @ColumnInfo(name = "materie")
    String denumireMaterie;
    @ColumnInfo(name = "nrStudenti")
    Integer numarStudenti;
    @ColumnInfo(name = "sala")
    String sala;
    @ColumnInfo(name = "supraveghetor")
    String supraveghetor;

    public Examen(Long id, String denumireMaterie, Integer numarStudenti, String sala, String supraveghetor) {
        this.id = id;
        this.denumireMaterie = denumireMaterie;
        this.numarStudenti = numarStudenti;
        this.sala = sala;
        this.supraveghetor = supraveghetor;
    }

    protected Examen(Parcel in) {
       this.id=in.readLong();
       this.denumireMaterie=in.readString();
       this.numarStudenti=in.readInt();
       this.sala=in.readString();
       this.supraveghetor=in.readString();
    }

    public static final Creator<Examen> CREATOR = new Creator<Examen>() {
        @Override
        public Examen createFromParcel(Parcel in) {
            return new Examen(in);
        }

        @Override
        public Examen[] newArray(int size) {
            return new Examen[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDenumireMaterie() {
        return denumireMaterie;
    }

    public void setDenumireMaterie(String denumireMaterie) {
        this.denumireMaterie = denumireMaterie;
    }

    public Integer getNumarStudenti() {
        return numarStudenti;
    }

    public void setNumarStudenti(Integer numarStudenti) {
        this.numarStudenti = numarStudenti;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getSupraveghetor() {
        return supraveghetor;
    }

    public void setSupraveghetor(String supraveghetor) {
        this.supraveghetor = supraveghetor;
    }

    @Override
    public String toString() {
        return "Examen{" +
                "id=" + id +
                ", denumireMaterie='" + denumireMaterie + '\'' +
                ", numarStudenti=" + numarStudenti +
                ", sala='" + sala + '\'' +
                ", supraveghetor='" + supraveghetor + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(denumireMaterie);
        dest.writeInt(numarStudenti);
        dest.writeString(sala);
        dest.writeString(supraveghetor);
    }
}
