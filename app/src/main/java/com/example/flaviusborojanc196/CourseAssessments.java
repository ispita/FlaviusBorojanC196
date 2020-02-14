package com.example.flaviusborojanc196;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "courseassessments_table",
        foreignKeys ={ @ForeignKey(entity = Assessment.class,
              parentColumns = "aID",
              childColumns = "assessmentId",
              onDelete = CASCADE),
        @ForeignKey(entity = Course.class,
               parentColumns = "cID",
               childColumns = "courseId",
               onDelete = CASCADE)},
        indices = {@Index("assessmentId"),@Index("courseId")}
        )

public class CourseAssessments {

    @PrimaryKey(autoGenerate = true)
    private int caid;

    private int assessmentId;

    private int courseId;



    public CourseAssessments(int assessmentId, int courseId) {
        this.assessmentId = assessmentId;
        this.courseId = courseId;

    }

    public void setCaid(int caid) {
        this.caid = caid;
    }

    public int getCaid() {
        return caid;
    }


    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }


}
