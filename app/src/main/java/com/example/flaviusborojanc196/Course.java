package com.example.flaviusborojanc196;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_table")

public class Course {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    private String start;

    private String end;

    public Course(String title, String description, String start, String end) {
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
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
