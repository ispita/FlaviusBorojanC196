package com.example.flaviusborojanc196;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class TermViewModel extends AndroidViewModel {

    private TermRepository repository;
    private LiveData<List<Term>> allTerms;


    public TermViewModel(@NonNull Application application) {
        super(application);
        repository = new TermRepository(application);
        allTerms = repository.getAllTerms();
    }

    public void insert(Term term) {
        repository.insert(term);
    }

    public void update(Term term) {
        repository.update(term);
    }

    public void updateTermCourses(TermCourses termCourses) {
        repository.updateTermCourses(termCourses);
    }

    public void insertTermCourses(TermCourses termCourses) {
        repository.insertTermCourses(termCourses);
    }

    public void delete(Term term) {
        repository.delete(term);
    }


    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }
}
