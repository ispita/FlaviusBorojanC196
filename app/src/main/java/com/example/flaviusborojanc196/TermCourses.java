package com.example.flaviusborojanc196;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "termcourse_table",
        foreignKeys ={ @ForeignKey(entity = Term.class,
              parentColumns = "tID",
              childColumns = "termId",
              onDelete = CASCADE),
        @ForeignKey(entity = Course.class,
               parentColumns = "cID",
               childColumns = "courseId",
               onDelete = CASCADE)},
        indices = {@Index("termId"),@Index("courseId")}
        )

public class TermCourses {

    @PrimaryKey(autoGenerate = true)
    private int tcid;

    private int termId;

    private int courseId;



    public TermCourses(int termId, int courseId) {
        this.termId = termId;
        this.courseId = courseId;

    }

    public int getTcid() {
        return tcid;
    }

    public void setTcid(int tcid) {
        this.tcid = tcid;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }


}
