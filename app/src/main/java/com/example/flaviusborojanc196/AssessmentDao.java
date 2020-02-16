package com.example.flaviusborojanc196;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao

public interface AssessmentDao {

    @Insert
    void insert(Assessment assessment);

    @Insert
    void insertCourseAssessments(CourseAssessments courseAssessments);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);


    @Query ("DELETE FROM courseassessments_table where assessmentId= :assessmentId and courseId = :courseId")
    void  deleteCourseAssessments(int assessmentId, int courseId);

    @Query("DELETE FROM assessment_table")
    void deleteAllAssessments();

    @Query("SELECT * FROM assessment_table ORDER BY aID ASC")
    LiveData<List<Assessment>> getAllAssessments();

    @Query("SELECT * FROM assessment_table where aID not in (select assessmentId from courseassessments_table where courseId= :courseId)")
    LiveData<List<Assessment>> getAvailableAssessments(int courseId);

    @Query("SELECT * FROM assessment_table where aID in (select assessmentId from courseassessments_table where courseId = :courseId)")
    LiveData<List<Assessment>> getCurrentAssessments(int courseId);

}
