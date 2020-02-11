package com.example.flaviusborojanc196;

import android.app.Application;

import java.util.List;

import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

public class TermRepository {
    private TermDao termDao;
    private LiveData<List<Term>> allTerms;

    public TermRepository(Application application){
        TermDatabase database = TermDatabase.getInstance(application);
        termDao = database.termDao();
        allTerms = termDao.getAllTerms();

    }
    public void insert(Term term){
        new InsertTermAsyncTask(termDao).execute(term);

    }

    public void update(Term term){
        new UpdateTermAsyncTask(termDao).execute(term);
    }

    public void delete(Term term){
        new DeleteTermAsyncTask(termDao).execute(term);
    }

    public void deleteAllTerms(){
        new DeleteAllTermAsyncTask(termDao).execute();
    }

    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }

    private static class InsertTermAsyncTask extends AsyncTask<Term, Void, Void>{
        private TermDao termDao;

        private InsertTermAsyncTask(TermDao termDao){
            this.termDao = termDao;
        }

        @Override
        protected Void doInBackground(Term... terms){
            termDao.insert(terms[0]);
            return null;
        }
    }

    private static class DeleteTermAsyncTask extends AsyncTask<Term, Void, Void>{
        private TermDao termDao;

        private DeleteTermAsyncTask(TermDao termDao){
            this.termDao = termDao;
        }

        @Override
        protected Void doInBackground(Term... terms){
            termDao.delete(terms[0]);
            return null;
        }
    }

    private static class DeleteAllTermAsyncTask extends AsyncTask<Void, Void, Void>{
        private TermDao termDao;

        private DeleteAllTermAsyncTask(TermDao termDao){
            this.termDao = termDao;
        }

        @Override
        protected Void doInBackground(Void... voids){
            termDao.deleteAllTerms();
            return null;
        }
    }

    private static class UpdateTermAsyncTask extends AsyncTask<Term, Void, Void>{
        private TermDao termDao;

        private UpdateTermAsyncTask(TermDao termDao){
            this.termDao = termDao;
        }

        @Override
        protected Void doInBackground(Term... terms){
            termDao.update(terms[0]);
            return null;
        }
    }
}
