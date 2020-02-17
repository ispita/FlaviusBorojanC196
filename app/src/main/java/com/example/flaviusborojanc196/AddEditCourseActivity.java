package com.example.flaviusborojanc196;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AddEditCourseActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.flaviusborojanc196.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.flaviusborojanc196.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.flaviusborojanc196.EXTRA_DESCRIPTION";
    public static final String EXTRA_START_DATE =
            "com.example.flaviusborojanc196.EXTRA_START_DATE";
    public static final String EXTRA_END_DATE=
            "com.example.flaviusborojanc196.EXTRA_END_DATE";
    public static final String EXTRA_STATUS=
            "com.example.flaviusborojanc196.EXTRA_STATUS";
    public static final String EXTRA_MENTOR=
            "com.example.flaviusborojanc196.EXTRA_MENTOR";
    public static final String EXTRA_PHONE=
            "com.example.flaviusborojanc196.EXTRA_PHONE";
    public static final String EXTRA_EMAIL=
            "com.example.flaviusborojanc196.EXTRA_EMAIL";
    public static final String EXTRA_NOTE=
            "com.example.flaviusborojanc196.EXTRA_NOTE";
    public static final Integer EXTRA_COURSE_CARD_COLOR= 555555;

    private EditText editTextTitle;
    private EditText editTextDescription;
    private DatePicker editTextStartDate;
    private DatePicker editTextEndDate;
    private EditText editTextStatus;
    private EditText editTextMentor;
    private EditText editTextPhone;
    private EditText editTextEmail;
    private EditText editTextNote;
    private int startSep;
    private int startSep2;
    private int endSep;
    private int endSep2;
    private int startYear;
    private int  startMonth;
    private int startDay;
    private int endYear;
    private int endMonth;
    private int endDay;
    private List<String> emailTLD = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        editTextTitle = findViewById(R.id.edit_title);
        editTextDescription = findViewById(R.id.edit_description);
        editTextStartDate = findViewById(R.id.edit_course_start_date);
        editTextEndDate = findViewById(R.id.edit_course_end_date);
        editTextStatus = findViewById(R.id.edit_course_status);
        editTextMentor = findViewById(R.id.edit_course_mentor);
        editTextPhone = findViewById(R.id.edit_course_mentor_phone);
        editTextEmail = findViewById(R.id.edit_course_mentor_email);
        editTextNote = findViewById(R.id.edit_course_note);
        emailTLD.add("com");
        emailTLD.add("net");
        emailTLD.add("edu");
        emailTLD.add("org");

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Course");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            startSep = intent.getStringExtra(EXTRA_START_DATE).indexOf("/");
            startSep2 = intent.getStringExtra(EXTRA_START_DATE).indexOf("/", intent.getStringExtra(EXTRA_START_DATE).indexOf("/") + 1);
            startMonth = Integer.parseInt(intent.getStringExtra(EXTRA_START_DATE).substring(0, startSep));
            startDay = Integer.parseInt(intent.getStringExtra(EXTRA_START_DATE).substring(startSep + 1, startSep2));
            startYear = Integer.parseInt(intent.getStringExtra(EXTRA_START_DATE).substring(startSep2 + 1, startSep2 + 5));
//            Toast.makeText(this, startMonth + "/" + startDay + "/" + startYear, Toast.LENGTH_SHORT).show();
            endSep = intent.getStringExtra(EXTRA_END_DATE).indexOf("/");
            endSep2 = intent.getStringExtra(EXTRA_END_DATE).indexOf("/", intent.getStringExtra(EXTRA_END_DATE).indexOf("/") + 1);
            endMonth = Integer.parseInt(intent.getStringExtra(EXTRA_END_DATE).substring(0, endSep));
            endDay = Integer.parseInt(intent.getStringExtra(EXTRA_END_DATE).substring(endSep + 1, endSep2));
            endYear = Integer.parseInt(intent.getStringExtra(EXTRA_END_DATE).substring(endSep2 + 1, endSep2 + 5));
            editTextStartDate.updateDate(startYear,startMonth - 1,startDay);
            editTextEndDate.updateDate(endYear,endMonth - 1,endDay);
            editTextStatus.setText(intent.getStringExtra(EXTRA_STATUS));
            editTextMentor.setText(intent.getStringExtra(EXTRA_MENTOR));
            editTextPhone.setText(intent.getStringExtra(EXTRA_PHONE));
            editTextEmail.setText(intent.getStringExtra(EXTRA_EMAIL));
            editTextNote.setText(intent.getStringExtra(EXTRA_NOTE));


        }
        else {
            setTitle("Add Course");
        }
    }
    private void saveCourse() throws ParseException {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int startMonth = editTextStartDate.getMonth() + 1;
        int endMonth = editTextEndDate.getMonth() + 1;
        String start = startMonth + "/" + editTextStartDate.getDayOfMonth() + "/" + editTextStartDate.getYear();
        String end = endMonth + "/" + editTextEndDate.getDayOfMonth() + "/" + editTextEndDate.getYear();
        String status = editTextStatus.getText().toString();
        String mentor = editTextMentor.getText().toString();
        String phone = editTextPhone.getText().toString();
        String email = editTextEmail.getText().toString();
        String note = editTextNote.getText().toString();


        if(title.trim().isEmpty() || description.trim().isEmpty() || status.isEmpty() || mentor.isEmpty() || phone.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "Please fill out ALL required fields!", Toast.LENGTH_SHORT).show();
            return;
        }
        SimpleDateFormat format = new SimpleDateFormat("MM/d/yyyy");
        if (format.parse(end).before(format.parse(start)) || end.equals(start)) {
            Toast.makeText(this, "End date must be AFTER the start date!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Email is not valid. Please enter a valid email.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_START_DATE, start);
        data.putExtra(EXTRA_END_DATE, end);
        data.putExtra(EXTRA_STATUS, status);
        data.putExtra(EXTRA_MENTOR, mentor);
        data.putExtra(EXTRA_PHONE, phone);
        data.putExtra(EXTRA_EMAIL, email);
        data.putExtra(EXTRA_NOTE, note);



        int id = getIntent().getIntExtra(EXTRA_ID, -1);

        if (id != -1){
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_term_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_term:
                try {
                    saveCourse();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
