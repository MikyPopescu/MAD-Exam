package com.example.subiectcitat2020.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.subiectcitat2020.database.Dao.CitatDao;
import com.example.subiectcitat2020.util.Citat;

@Database(entities = {Citat.class}, exportSchema = false, version = 1)
public abstract class DatabaseManager extends RoomDatabase {
    private static final String DB_NAME = "db_dam";
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager == null) {
            synchronized (DatabaseManager.class) {
                if (databaseManager == null) {
                    databaseManager= Room.databaseBuilder(context,DatabaseManager.class,DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                    return databaseManager;
                }
            }
        }
        return  databaseManager;
    }
    public abstract CitatDao getCitatDao();
}
