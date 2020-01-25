package com.example.subiect_examen.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.subiect_examen.database.dao.ExamenDao;
import com.example.subiect_examen.util.Examen;

@Database(entities = Examen.class,exportSchema = false,version = 1)
public abstract class DatabaseManager extends RoomDatabase {
    private static final String DB_NAME="dam_db";
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context){
        if(databaseManager==null){
            synchronized (DatabaseManager.class){
                if(databaseManager==null){
                    databaseManager= Room.databaseBuilder(context,DatabaseManager.class,DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                    return databaseManager;
                }
            }
        }
        return databaseManager;
    }

    public abstract ExamenDao getExamenDao();
}
