package com.example.flaviusborojanc196;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Course.class, version = 4, exportSchema = false)
public abstract class CourseDatabase extends RoomDatabase {

    private static CourseDatabase instance;

    public abstract CourseDao courseDao();

    public static synchronized CourseDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), CourseDatabase.class, "course_database").fallbackToDestructiveMigration().addCallback(roomCallback).build();

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
        private CourseDao courseDao;
        private PopulateDbAsyncTask(CourseDatabase db){
            courseDao = db.courseDao();
        }

        @Override
        protected Void doInBackground(Void... voids){
            courseDao.insert(new Course("Title1", "Description1", "02/11/2020", "03/31/2020"));
            courseDao.insert(new Course("Title2", "Description2", "02/11/2020", "03/31/2020"));
            courseDao.insert(new Course("Title3", "Description3", "02/11/2020", "03/31/2020"));
            courseDao.insert(new Course("Title4", "Description4", "02/11/2020", "03/31/2020"));
            return null;
        }
    }
}
