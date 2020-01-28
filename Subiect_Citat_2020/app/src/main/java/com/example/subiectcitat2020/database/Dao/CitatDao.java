package com.example.subiectcitat2020.database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.subiectcitat2020.util.Citat;

import java.util.List;

@Dao
public interface CitatDao {
    @Query("select * from citate")
    List<Citat> getAll();

    @Insert
    long insert (Citat citat);

    @Update
    int update(Citat citat);

    @Delete
    int delete(Citat citat);
}
