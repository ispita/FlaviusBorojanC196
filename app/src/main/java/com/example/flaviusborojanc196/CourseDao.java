package com.example.flaviusborojanc196;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao

public interface CourseDao {

    @Insert
    void insert(Course course);

    @Insert
    void insertTermCourses(TermCourses termCourses);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Delete
    void deleteTermCourses(TermCourses termCourses);

    @Query("DELETE FROM course_table")
    void deleteAllCourses();

    @Query("SELECT * FROM course_table ORDER BY cID ASC")
    LiveData<List<Course>> getAllCourses();

    @Query("SELECT * FROM course_table where cID not in (select courseId from termcourse_table where termId = :termId)")
    LiveData<List<Course>> getAvailableCourses(int termId);

    @Query("SELECT * FROM course_table where cID in (select courseId from termcourse_table where termId = :termId)")
    LiveData<List<Course>> getCurrentCourses(int termId);

}
