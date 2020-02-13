package com.example.flaviusborojanc196;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "term_table")

public class Term {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    private String start;

    private String end;

    private String termCourses;



    public Term(String title, String description, String start, String end, String termCourses) {
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.termCourses = termCourses;

    }


    public String getTermCourses() {
        return termCourses;
    }

    public void setTermCourses(String termCourses) {
        this.termCourses = termCourses;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}
