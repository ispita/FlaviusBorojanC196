package com.example.flaviusborojanc196;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class CourseViewModel extends AndroidViewModel {

    private CourseRepository repository;
    private LiveData<List<Course>> allCourses;
    private LiveData<List<Course>> availableCourses;
    private LiveData<List<Course>> currentCourses;


    public CourseViewModel(@NonNull Application application) {
        super(application);
        repository = new CourseRepository(application);
        allCourses = repository.getAllCourses();
        availableCourses = repository.getAvailableCourses();
        currentCourses = repository.getCurrentCourses();
    }

    public void insert(Course course){
        repository.insert(course);
    }

    public void update(Course course){
        repository.update(course);
    }

    public void delete(Course course){
        repository.delete(course);
    }

    public void insertTermCourses(TermCourses termCourses) {
        repository.insertTermCourses(termCourses);
    }

    public void deleteTermCourses(TermCourses termCourses) {
        repository.deleteTermCourses(termCourses);
    }



    public LiveData<List<Course>> getAllCourses(){
       return allCourses;
    }

    public LiveData<List<Course>> getAvailableCourses() {return availableCourses;}

    public LiveData<List<Course>> getCurrentCourses(){
        return currentCourses;
    }


}
