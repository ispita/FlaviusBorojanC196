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

public class AddEditTermActivity extends AppCompatActivity {
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
    private String editTermId;
    private Boolean editTerm = false;
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
        setContentView(R.layout.activity_add_term);

        editTextTitle = findViewById(R.id.edit_title);
        editTextDescription = findViewById(R.id.edit_description);
        editTextStartDate = findViewById(R.id.edit_term_start_date);
        editTextEndDate = findViewById(R.id.edit_term_end_date);
        addedCourses = findViewById(R.id.courses_added);
      //  addedCoursesId = findViewById(R.id.courses_added);
        //courseTitle = findViewById(R.id.text_view_course_title);


//        RecyclerView recyclerView = findViewById(R.id.course_add_recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setHasFixedSize(true);
//
//        final CourseAdapter adapter = new CourseAdapter();
//        recyclerView.setAdapter(adapter);
//
//        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
//        courseViewModel.getAllCourses().observe(this,new Observer<List<Course>>(){
//            @Override
//            public void onChanged(@Nullable List<Course> courses){
//                adapter.setCourses(courses);
//            }
//        });


        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Term");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            editTextStartDate.setText(intent.getStringExtra(EXTRA_START_DATE));
            editTextEndDate.setText(intent.getStringExtra(EXTRA_END_DATE));
//            addedCourses.setText(intent.getStringExtra(EXTRA_TERM_COURSES));
            editTermId = intent.getStringExtra(EXTRA_ID);
            editTerm = true;
//            String[] addedCoursesArray = (addedCourses.getText().toString().split(","));
//            Collections.addAll(courseArray,addedCoursesArray);
//            Toast.makeText(this, "EXTRA_ID  " + editTermId , Toast.LENGTH_SHORT).show();

        }
        else {
            setTitle("Add Term");
        }
//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                courseViewModel.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
//            }
//        }).attachToRecyclerView(recyclerView);
//
//        adapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Course course) {
//                    if (!courseArray.contains(course.getTitle())) {
//                        courseArray.add(course.getTitle());
//
//                        addedCourses.append("\n" +courseArray.get(courseArray.size() - 1) + "\n");
//                    } else {
//
//                        courseArray.remove(course.getTitle());
//                        addedCourses.setText("");
//                        for (int j = 0; j < courseArray.size(); j++) {
//                            addedCourses.append("\n" + courseArray.get(j) + "\n");
//                        }
//                    }
//
//            }
//        });

    }
    private void saveTerm(){
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
//        String termCourses = addedCourses.getText().toString();
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
       // data.putExtra(EXTRA_TERM_COURSES, termCourses);
        if (!editTerm) {
             id = getIntent().getIntExtra(EXTRA_ID, -1);
        }
        else if (editTerm) {
            id = Integer.parseInt(editTermId);
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
                saveTerm();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
