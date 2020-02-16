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
import android.widget.DatePicker;
import android.widget.EditText;
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
//    public static final String EXTRA_TERM_COURSES=
//            "com.example.flaviusborojanc196.EXTRA_TERM_COURSES";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextStartDate;
    private EditText editTextEndDate;
    private String editAssessmentId;
    private Boolean editAssessment = false;
    //    private TextView coursesLabel;
//    private CourseViewModel courseViewModel;
//    private CourseViewModel addedCourseViewModel;
    // private TextView courseTitle;
    //private Integer courseCardColor;
    private TextView addedCourses;
    // private TextView addedCoursesId;
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
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            editTextStartDate.setText(intent.getStringExtra(EXTRA_START_DATE));
            editTextEndDate.setText(intent.getStringExtra(EXTRA_END_DATE));
//            addedCourses.setText(intent.getStringExtra(EXTRA_TERM_COURSES));
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
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String start = editTextStartDate.getText().toString();
        String end = editTextEndDate.getText().toString();
//        addedCourses.setText("");
//        for(int j = 0; j < courseArray.size(); j++) {
//            if (j == courseArray.size() - 1){
//                addedCourses.append(courseArray.get(j));
//            }
//            else {
//                addedCourses.append(courseArray.get(j) + ",");
//            }
//        }
//        String assessmentCourses = addedCourses.getText().toString();
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
        // data.putExtra(EXTRA_TERM_COURSES, assessmentCourses);
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
