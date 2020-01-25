package com.example.subiect_examen.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.subiect_examen.util.Examen;

import java.util.List;
@Dao
public interface ExamenDao {
    @Query("select * from examene")
    List<Examen>getAll();

    @Insert
    long insert (Examen examen);

    @Update
    int update(Examen examen);

    @Delete
    int delete(Examen examen);
}
