package com.example.flaviusborojanc196;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AssessmentRepository {
    private AssessmentDao assessmentDao;
    private LiveData<List<Assessment>> allAssessments;
    private LiveData<List<Assessment>> availableAssessments;
    private LiveData<List<Assessment>> currentAssessments;
    public static int courseId;
    public static int assessmentId;

    public AssessmentRepository(Application application){
        WGUDatabase database = WGUDatabase.getInstance(application);
        assessmentDao = database.assessmentDao();
        allAssessments = assessmentDao.getAllAssessments();
        availableAssessments = assessmentDao.getAvailableAssessments(courseId);
        currentAssessments = assessmentDao.getCurrentAssessments(courseId);


    }
    public void insert(Assessment assessment){
        new InsertAssessmentAsyncTask(assessmentDao).execute(assessment);

    }
    public void insertCourseAssessments(CourseAssessments courseAssessments){
        new InsertCourseAssessmentsAsyncTask(assessmentDao).execute(courseAssessments);

    }

    public void update(Assessment assessment){
        new UpdateAssessmentAsyncTask(assessmentDao).execute(assessment);
    }

    public void delete(Assessment assessment){
        new DeleteAssessmentAsyncTask(assessmentDao).execute(assessment);
    }

    public void deleteCourseAssessments(){
        new DeleteCourseAssessmentsAsyncTask(assessmentDao).execute();
    }

    public void deleteAllAssessments(){
        new DeleteAllAssessmentAsyncTask(assessmentDao).execute();
    }


    public LiveData<List<Assessment>> getAllAssessments() {
        return allAssessments;
    }

    public LiveData<List<Assessment>> getAvailableAssessments() {
        return availableAssessments;
    }

    public LiveData<List<Assessment>> getCurrentAssessments() {
        return currentAssessments;
    }

    private static class InsertAssessmentAsyncTask extends AsyncTask<Assessment, Void, Void>{
        private AssessmentDao assessmentDao;

        private InsertAssessmentAsyncTask(AssessmentDao assessmentDao){
            this.assessmentDao = assessmentDao;
        }

        @Override
        protected Void doInBackground(Assessment... assessments){
            assessmentDao.insert(assessments[0]);
            return null;
        }
    }

    private static class InsertCourseAssessmentsAsyncTask extends AsyncTask<CourseAssessments, Void, Void>{
        private AssessmentDao assessmentDao;

        private InsertCourseAssessmentsAsyncTask(AssessmentDao assessmentDao){
            this.assessmentDao = assessmentDao;
        }

        @Override
        protected Void doInBackground(CourseAssessments... courseAs){
            assessmentDao.insertCourseAssessments(courseAs[0]);
            return null;
        }
    }

    private static class DeleteAssessmentAsyncTask extends AsyncTask<Assessment, Void, Void>{
        private AssessmentDao assessmentDao;

        private DeleteAssessmentAsyncTask(AssessmentDao assessmentDao){
            this.assessmentDao = assessmentDao;
        }

        @Override
        protected Void doInBackground(Assessment... assessments){
            assessmentDao.delete(assessments[0]);
            return null;
        }
    }

    private static class DeleteCourseAssessmentsAsyncTask extends AsyncTask<Void, Void, Void>{
        private AssessmentDao assessmentDao;

        private DeleteCourseAssessmentsAsyncTask(AssessmentDao assessmentDao){
            this.assessmentDao = assessmentDao;
        }

        @Override
        protected Void doInBackground(Void... voids){
            assessmentDao.deleteCourseAssessments(assessmentId,courseId);
            return null;
        }
    }


    private static class DeleteAllAssessmentAsyncTask extends AsyncTask<Void, Void, Void>{
        private AssessmentDao assessmentDao;

        private DeleteAllAssessmentAsyncTask(AssessmentDao assessmentDao){
            this.assessmentDao = assessmentDao;
        }

        @Override
        protected Void doInBackground(Void... voids){
            assessmentDao.deleteAllAssessments();
            return null;
        }
    }

    private static class UpdateAssessmentAsyncTask extends AsyncTask<Assessment, Void, Void>{
        private AssessmentDao assessmentDao;

        private UpdateAssessmentAsyncTask(AssessmentDao assessmentDao){
            this.assessmentDao = assessmentDao;
        }

        @Override
        protected Void doInBackground(Assessment... assessments){
            assessmentDao.update(assessments[0]);
            return null;
        }
    }
}
