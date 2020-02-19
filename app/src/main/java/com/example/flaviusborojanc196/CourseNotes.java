package com.example.flaviusborojanc196;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "coursenotes_table",
        foreignKeys ={ @ForeignKey(entity = Note.class,
              parentColumns = "nID",
              childColumns = "noteId",
              onDelete = CASCADE),
        @ForeignKey(entity = Course.class,
               parentColumns = "cID",
               childColumns = "courseId",
               onDelete = CASCADE)},
        indices = {@Index("noteId"),@Index("courseId")}
        )

public class CourseNotes {

    @PrimaryKey(autoGenerate = true)
    private int cnid;

    private int noteId;

    private int courseId;



    public CourseNotes(int noteId, int courseId) {
        this.noteId = noteId;
        this.courseId = courseId;

    }

    public int getCnid() {
        return cnid;
    }

    public void setCnid(int cnid) {
        this.cnid = cnid;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }


}
