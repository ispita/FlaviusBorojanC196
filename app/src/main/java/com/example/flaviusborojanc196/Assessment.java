package com.example.flaviusborojanc196;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessment_table")

public class Assessment {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="aID")
    private int id;

    private String title;

    private String description;

    private String goal;

    private String end;




    public Assessment(String title, String description, String end, String goal) {
        this.title = title;
        this.description = description;
        this.end = end;
        this.goal = goal;


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

    public String getGoal(){ return goal;}

    public String getEnd() {
        return end;
    }
}
