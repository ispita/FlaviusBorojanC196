package com.example.flaviusborojanc196;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")

public class Note {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="nID")
    private int id;

    private String title;

    private String description;

    private int courseId;


//    @Ignore
//    public Note(String title, String description) {
//        this.title = title;
//        this.description = description;
//    }

    public Note(int courseId, String title, String description){
        this.courseId = courseId;
        this.title = title;
        this.description = description;
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

    public int getCourseId() {
        return courseId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
