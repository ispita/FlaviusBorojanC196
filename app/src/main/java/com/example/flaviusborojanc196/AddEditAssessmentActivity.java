package com.example.flaviusborojanc196;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddEditAssessmentActivity extends AppCompatActivity {
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
//    public static final String EXTRA_ASSESSMENT_COURSES=
//            "com.example.flaviusborojanc196.EXTRA_ASSESSMENT_COURSES";

    private EditText editTextTitle;
    private RadioGroup editTextDescription;
    private RadioButton textDescription;
    private DatePicker editTextStartDate;
    private DatePicker editTextEndDate;
    private String editAssessmentId;
    private Boolean editAssessment = false;
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
    private TextView addedCourses;

    private List<String> courseArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);

        editTextTitle = findViewById(R.id.edit_title);
        editTextDescription = findViewById(R.id.edit_description);
        editTextStartDate = findViewById(R.id.edit_assessment_start_date);
        editTextEndDate = findViewById(R.id.edit_assessment_end_date);
        addedCourses = findViewById(R.id.courses_added);


        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Assessment");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
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
            editAssessmentId = intent.getStringExtra(EXTRA_ID);
            editAssessment = true;
//            String[] addedCoursesArray = (addedCourses.getText().toString().split(","));
//            Collections.addAll(courseArray,addedCoursesArray);
//            Toast.makeText(this, "EXTRA_ID  " + editAssessmentId , Toast.LENGTH_SHORT).show();

        }
        else {
            setTitle("Add Assessment");
        }


    }

    private void saveAssessment(){
        int radioGroup = editTextDescription.getCheckedRadioButtonId();
        textDescription = findViewById(radioGroup);
        String title = editTextTitle.getText().toString();
        String description = textDescription.getText().toString();
        int startMonth = editTextStartDate.getMonth() + 1;
        int endMonth = editTextEndDate.getMonth() + 1;
        String start = startMonth + "/" + editTextStartDate.getDayOfMonth() + "/" + editTextStartDate.getYear();
        String end = endMonth + "/" + editTextEndDate.getDayOfMonth() + "/" + editTextEndDate.getYear();

        if(title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "Please Insert a Description and a Title!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_START_DATE, start);
        data.putExtra(EXTRA_END_DATE, end);
        int id = -1;
        // data.putExtra(EXTRA_ASSESSMENT_COURSES, assessmentCourses);
        if (!editAssessment) {
            id = getIntent().getIntExtra(EXTRA_ID, -1);
        }
        else if (editAssessment) {
            id = Integer.parseInt(editAssessmentId);
        }
        Toast.makeText(this, "this is the ID " + id, Toast.LENGTH_SHORT).show();
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
                saveAssessment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
