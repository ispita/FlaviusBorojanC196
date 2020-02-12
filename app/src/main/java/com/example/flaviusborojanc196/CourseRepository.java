package com.example.flaviusborojanc196;

import android.app.Application;

import java.util.List;

import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

public class CourseRepository {
    private CourseDao courseDao;
    private LiveData<List<Course>> allCourses;

    public CourseRepository(Application application){
        CourseDatabase database = CourseDatabase.getInstance(application);
        courseDao = database.courseDao();
        allCourses = courseDao.getAllCourses();

    }
    public void insert(Course course){
        new InsertCourseAsyncTask(courseDao).execute(course);

    }

    public void update(Course course){
        new UpdateCourseAsyncTask(courseDao).execute(course);
    }

    public void delete(Course course){
        new DeleteCourseAsyncTask(courseDao).execute(course);
    }

    public void deleteAllCourses(){
        new DeleteAllCourseAsyncTask(courseDao).execute();
    }

    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    private static class InsertCourseAsyncTask extends AsyncTask<Course, Void, Void>{
        private CourseDao courseDao;

        private InsertCourseAsyncTask(CourseDao courseDao){
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses){
            courseDao.insert(courses[0]);
            return null;
        }
    }

    private static class DeleteCourseAsyncTask extends AsyncTask<Course, Void, Void>{
        private CourseDao courseDao;

        private DeleteCourseAsyncTask(CourseDao courseDao){
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses){
            courseDao.delete(courses[0]);
            return null;
        }
    }

    private static class DeleteAllCourseAsyncTask extends AsyncTask<Void, Void, Void>{
        private CourseDao courseDao;

        private DeleteAllCourseAsyncTask(CourseDao courseDao){
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Void... voids){
            courseDao.deleteAllCourses();
            return null;
        }
    }

    private static class UpdateCourseAsyncTask extends AsyncTask<Course, Void, Void>{
        private CourseDao courseDao;

        private UpdateCourseAsyncTask(CourseDao courseDao){
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses){
            courseDao.update(courses[0]);
            return null;
        }
    }
}
