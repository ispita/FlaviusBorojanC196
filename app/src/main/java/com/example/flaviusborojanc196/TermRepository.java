package com.example.flaviusborojanc196;

import android.app.Application;

import java.util.List;

import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

public class TermRepository {
    private TermDao termDao;
    private LiveData<List<Term>> allTerms;

    public TermRepository(Application application){
        WGUDatabase database = WGUDatabase.getInstance(application);
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

    public void insertTermCourses(TermCourses termCourses){ new InsertTermCoursesAsyncTask(termDao).execute(termCourses);}

    public void updateTermCourses(TermCourses termCourses){
        new UpdateTermCoursesAsyncTask(termDao).execute(termCourses);
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

    private static class InsertTermCoursesAsyncTask extends AsyncTask<TermCourses, Void, Void>{
        private TermDao termDao;

        private InsertTermCoursesAsyncTask(TermDao termDao){
            this.termDao = termDao;
        }

        @Override
        protected Void doInBackground(TermCourses... termCs){
            termDao.insertTermCourses(termCs[0]);
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

    private static class UpdateTermCoursesAsyncTask extends AsyncTask<TermCourses, Void, Void>{
        private TermDao termDao;

        private UpdateTermCoursesAsyncTask(TermDao termDao){
            this.termDao = termDao;
        }

        @Override
        protected Void doInBackground(TermCourses... termCs){
            termDao.updateTermCourses(termCs[0]);
            return null;
        }
    }
}
