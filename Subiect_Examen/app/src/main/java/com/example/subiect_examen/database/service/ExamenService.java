package com.example.subiect_examen.database.service;

import android.content.Context;
import android.os.AsyncTask;

import com.example.subiect_examen.database.DatabaseManager;
import com.example.subiect_examen.database.dao.ExamenDao;
import com.example.subiect_examen.util.Examen;

import java.util.List;

public class ExamenService {

    private static ExamenDao examenDao;


    public static class GetAll extends AsyncTask<Void,Void, List<Examen>>{
        public GetAll(Context context){
            examenDao= DatabaseManager
                    .getInstance(context)
                    .getExamenDao();
        }
        @Override
        protected List<Examen> doInBackground(Void... voids) {
            return examenDao.getAll();
        }
    }

    public static class Insert extends AsyncTask<Examen,Void,Examen>{
        public Insert(Context context){
            examenDao = DatabaseManager
                    .getInstance(context)
                    .getExamenDao();
        }
        @Override
        protected Examen doInBackground(Examen... examens) {
           if(examens==null || examens.length!=1){
               return null;
           }
           Examen examen = examens[0];
           long id=examenDao.insert(examen);
           if(id!=-1){
               examen.setId(id);
               return examen;
           }
           return null;
        }
    }

    public static class Update extends AsyncTask<Examen,Void,Integer>{
        public Update(Context context){
            examenDao=DatabaseManager
                    .getInstance(context)
                    .getExamenDao();
        }

        @Override
        protected Integer doInBackground(Examen... examens) {
          if(examens==null || examens.length!=1){
              return -1;
          }
          return examenDao.update(examens[0]);
        }
    }

    public static class Delete extends AsyncTask<Examen,Void,Integer>{
        public Delete(Context context){
            examenDao= DatabaseManager
                        .getInstance(context)
                        .getExamenDao();
        }

        @Override
        protected Integer doInBackground(Examen... examens) {
           if(examens==null || examens.length!=-1){
               return -1;
           }
           return examenDao.delete(examens[0]);
        }
    }
}
