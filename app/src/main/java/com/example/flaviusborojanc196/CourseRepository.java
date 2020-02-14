package com.example.flaviusborojanc196;

import android.app.Application;

import java.util.List;

import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

public class CourseRepository {
    private CourseDao courseDao;
    private LiveData<List<Course>> allCourses;
    private LiveData<List<Course>> availableCourses;
    private LiveData<List<Course>> currentCourses;
    public static int termId;

    public CourseRepository(Application application){
        WGUDatabase database = WGUDatabase.getInstance(application);
        courseDao = database.courseDao();
        allCourses = courseDao.getAllCourses();
        availableCourses = courseDao.getAvailableCourses(termId);
        currentCourses = courseDao.getCurrentCourses(termId);


    }
    public void insert(Course course){
        new InsertCourseAsyncTask(courseDao).execute(course);

    }
    public void insertTermCourses(TermCourses termCourse){
        new InsertTermCoursesAsyncTask(courseDao).execute(termCourse);

    }

    public void update(Course course){
        new UpdateCourseAsyncTask(courseDao).execute(course);
    }

    public void delete(Course course){
        new DeleteCourseAsyncTask(courseDao).execute(course);
    }

    public void deleteTermCourses(TermCourses termCourse){
        new DeleteTermCoursesAsyncTask(courseDao).execute(termCourse);
    }

    public void deleteAllCourses(){
        new DeleteAllCourseAsyncTask(courseDao).execute();
    }


    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    public LiveData<List<Course>> getAvailableCourses() {
        return availableCourses;
    }

    public LiveData<List<Course>> getCurrentCourses() {
        return currentCourses;
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

    private static class InsertTermCoursesAsyncTask extends AsyncTask<TermCourses, Void, Void>{
        private CourseDao courseDao;

        private InsertTermCoursesAsyncTask(CourseDao courseDao){
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(TermCourses... termCs){
            courseDao.insertTermCourses(termCs[0]);
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

    private static class DeleteTermCoursesAsyncTask extends AsyncTask<TermCourses, Void, Void>{
        private CourseDao courseDao;

        private DeleteTermCoursesAsyncTask(CourseDao courseDao){
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(TermCourses... termCs){
            courseDao.deleteTermCourses(termCs[0]);
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
