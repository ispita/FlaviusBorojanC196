package com.example.flaviusborojanc196;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class AssessmentViewModel extends AndroidViewModel {

    private AssessmentRepository repository;
    private LiveData<List<Assessment>> allAssessments;
    private LiveData<List<Assessment>> availableAssessments;
    private LiveData<List<Assessment>> currentAssessments;


    public AssessmentViewModel(@NonNull Application application) {
        super(application);
        repository = new AssessmentRepository(application);
        allAssessments = repository.getAllAssessments();
        availableAssessments = repository.getAvailableAssessments();
        currentAssessments = repository.getCurrentAssessments();
    }

    public void insert(Assessment assessment){
        repository.insert(assessment);
    }

    public void update(Assessment assessment){
        repository.update(assessment);
    }

    public void delete(Assessment assessment){
        repository.delete(assessment);
    }

    public void insertCourseAssessments(CourseAssessments courseAssessments) {
        repository.insertCourseAssessments(courseAssessments);
    }

    public void deleteCourseAssessments(CourseAssessments courseAssessments) {
        repository.deleteCourseAssessments(courseAssessments);
    }



    public LiveData<List<Assessment>> getAllAssessments(){
       return allAssessments;
    }

    public LiveData<List<Assessment>> getAvailableAssessments() {return availableAssessments;}

    public LiveData<List<Assessment>> getCurrentAssessments(){
        return currentAssessments;
    }


}
