package com.example.subiectcitat2020.database.service;

import android.content.Context;
import android.os.AsyncTask;

import com.example.subiectcitat2020.database.Dao.CitatDao;
import com.example.subiectcitat2020.database.DatabaseManager;
import com.example.subiectcitat2020.util.Citat;

import java.util.List;

public class CitatService {
    private static CitatDao citatDao;

    public static class GetAll extends AsyncTask<Void, Void, List<Citat>> {
        public GetAll(Context context) {
            citatDao = DatabaseManager.getInstance(context).getCitatDao();
        }

        @Override
        protected List<Citat> doInBackground(Void... voids) {
            return citatDao.getAll();
        }
    }

    public static class Insert extends AsyncTask<Citat, Void, Citat> {
        public Insert(Context context) {
            citatDao = DatabaseManager.getInstance(context).getCitatDao();
        }

        @Override
        protected Citat doInBackground(Citat... citats) {
            if (citats == null || citats.length != 1) {
                return null;
            }
            Citat citat = citats[0];
            long id = citatDao.insert(citat);
            if (id != -1) {
                citat.setId(id);
                return citat;
            }
            return null;
        }
    }

    public static class Update extends AsyncTask<Citat, Void, Integer> {
        public Update(Context context) {
            citatDao = DatabaseManager.getInstance(context).getCitatDao();
        }

        @Override
        protected Integer doInBackground(Citat... citats) {
            if (citats == null || citats.length != 1) {
                return -1;
            }
            return citatDao.update(citats[0]);
        }
    }

    public static class Delete extends AsyncTask<Citat, Void, Integer> {
        public Delete(Context context) {
            citatDao = DatabaseManager.getInstance(context).getCitatDao();
        }

        @Override
        protected Integer doInBackground(Citat... citats) {
           if(citats==null || citats.length!=1){
               return -1;
           }
           return citatDao.delete(citats[0]);
        }
    }
}
