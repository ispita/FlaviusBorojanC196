package com.example.flaviusborojanc196;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Term.class, Course.class}, version = 2, exportSchema = false)
public abstract class WGUDatabase extends RoomDatabase {

    private static WGUDatabase instance;

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();

    public static synchronized WGUDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), WGUDatabase.class, "wgu_database").fallbackToDestructiveMigration().addCallback(roomCallback).build();

        }

        return instance;

    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {
        private TermDao termDao;
        private CourseDao courseDao;
        private PopulateDbAsyncTask(WGUDatabase db){
            termDao = db.termDao();
            courseDao = db.courseDao();
        }


        @Override
        protected Void doInBackground(Void... voids){
            termDao.insert(new Term("Title1", "Description1", "02/11/2020", "03/31/2020", "1"));
            termDao.insert(new Term("Title2", "Description2", "02/11/2020", "03/31/2020", "2"));
            termDao.insert(new Term("Title3", "Description3", "02/11/2020", "03/31/2020", "3"));
            termDao.insert(new Term("Title4", "Description4", "02/11/2020", "03/31/2020", "4"));
            courseDao.insert(new Course("Title1", "Description1", "02/11/2020", "03/31/2020","plan to take", "timmy","777-333-4444","email@email.com","optional min 1"));
            courseDao.insert(new Course("Title2", "Description2", "02/11/2020", "03/31/2020","plan to take", "timmy","777-333-4444","email@email.com","optional min 1"));
            courseDao.insert(new Course("Title3", "Description3", "02/11/2020", "03/31/2020","plan to take", "timmy","777-333-4444","email@email.com","optional min 1"));
            courseDao.insert(new Course("Title4", "Description4", "02/11/2020", "03/31/2020","plan to take", "timmy","777-333-4444","email@email.com","optional min 1"));
            return null;
        }
    }
}
