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


    private String end;




    public Assessment(String title, String description, String end) {
        this.title = title;
        this.description = description;
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


    public String getEnd() {
        return end;
    }
}
