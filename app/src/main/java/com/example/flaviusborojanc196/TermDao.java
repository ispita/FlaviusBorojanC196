package com.example.flaviusborojanc196;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao

public interface TermDao {

    @Insert
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);


    @Query("DELETE FROM term_table")
    void deleteAllTerms();

    @Query("SELECT * FROM term_table ORDER BY termID ASC")
    LiveData<List<Term>> getAllTerms();

}
