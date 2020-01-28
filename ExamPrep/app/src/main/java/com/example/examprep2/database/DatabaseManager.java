package com.example.examprep2.database;


import android.content.Context;
import android.provider.ContactsContract;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.examprep2.database.dao.JucatorDao;
import com.example.examprep2.util.DateConvertor;
import com.example.examprep2.util.Jucator;

@Database(entities = {Jucator.class}, exportSchema = false, version = 1)
@TypeConverters({DateConvertor.class})
public abstract class DatabaseManager extends RoomDatabase {
    private static final String DB_NAME = "dam_db";
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
    if(databaseManager==null){
        synchronized (DatabaseManager.class){
            if(databaseManager==null){
                databaseManager = Room.databaseBuilder(context, DatabaseManager.class,DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
                return databaseManager;
            }
        }
    }
    return databaseManager;
    }
    public abstract JucatorDao getJucatorDao();
}
