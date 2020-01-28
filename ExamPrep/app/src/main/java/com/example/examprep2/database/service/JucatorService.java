package com.example.examprep2.database.service;

import android.content.Context;
import android.os.AsyncTask;

import com.example.examprep2.database.DatabaseManager;
import com.example.examprep2.database.dao.JucatorDao;
import com.example.examprep2.util.Jucator;

import java.util.List;

public class JucatorService {
    private static JucatorDao jucatorDao;

    public static class GetAll extends AsyncTask<Void,Void, List<Jucator>>{
       public GetAll(Context context){
           jucatorDao = DatabaseManager
                        .getInstance(context)
                        .getJucatorDao();
       }
        @Override
        protected List<Jucator> doInBackground(Void... voids) {
            return jucatorDao.getAll();
        }
    }

    public static class Insert extends AsyncTask<Jucator,Void,Jucator>{
        public Insert(Context context){
            jucatorDao = DatabaseManager
                    .getInstance(context)
                    .getJucatorDao();
        }
        @Override
        protected Jucator doInBackground(Jucator... jucators) {
          if(jucators == null || jucators.length!=1){
              return null;
          }

              Jucator jucator = jucators[0];
              long id =  jucatorDao.insert(jucator);
              if(id!=-1){
                  jucator.setId(id);
                  return jucator;
              }
              return null;
        }
    }

    public static class Update extends AsyncTask<Jucator,Void,Integer>{
        public Update(Context context){
            jucatorDao = DatabaseManager.getInstance(context).getJucatorDao();
        }
        @Override
        protected Integer doInBackground(Jucator... jucators) {
            if(jucators==null || jucators.length!=1){
                return -1;
            }
            return jucatorDao.update(jucators[0]);
        }
    }

    public static class Delete extends AsyncTask<Jucator,Void,Integer>{
        public Delete(Context context){
            jucatorDao=DatabaseManager.getInstance(context).getJucatorDao();
        }
        @Override
        protected Integer doInBackground(Jucator... jucators) {
            if(jucators==null || jucators.length!=1){
                return -1;
            }
            return jucatorDao.delete(jucators[0]);
        }
    }
}
