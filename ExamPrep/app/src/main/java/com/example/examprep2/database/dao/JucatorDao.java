package com.example.examprep2.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.examprep2.util.Jucator;

import java.util.List;

@Dao
public interface JucatorDao {
@Query("select * from jucatori")
List<Jucator> getAll();
@Insert
long insert(Jucator jucator);
@Update
int update(Jucator jucator);
@Delete
int delete(Jucator jucator);
}
