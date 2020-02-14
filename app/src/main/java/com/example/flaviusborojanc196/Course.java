package com.example.flaviusborojanc196;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "course_table")

public class Course {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="cID")
    private int id;

    private String title;

    private String description;

    private String start;

    private String end;

    private String status;

    private String mentor;

    private String phone;

    private String email;

    private String note;



    public Course(String title, String description, String start, String end, String status, String mentor, String phone, String email, String note) {
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.status = status;
        this.mentor = mentor;
        this.phone = phone;
        this.email = email;
        this.note = note;


    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMentor() {
        return mentor;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
