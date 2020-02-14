package com.example.flaviusborojanc196;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ViewCourseDetailedActivity extends AppCompatActivity {
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



    private TextView viewTextTitle;
    private TextView viewTextDescription;
    private TextView viewTextStartDate;
    private TextView viewTextEndDate;
    private TextView viewTextCourseId;
    private RecyclerView viewTextCourseAssessments;
    private CourseViewModel courseViewModel;
    private AssessmentViewModel assessmentViewMode1;
    private List<Course> coursesList;

    public static final int ADD_TERM_REQUEST = 1;
    public static final int EDIT_TERM_REQUEST = 2;
    public static final int VIEW_TERM_DETAILED_REQUEST = 3;
    public static final int ADD_COURSE_REQUEST = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course_detailed);

        viewTextTitle = findViewById(R.id.view_title);
        viewTextDescription = findViewById(R.id.view_description);
        viewTextStartDate = findViewById(R.id.view_course_start_date);
        viewTextEndDate = findViewById(R.id.view_course_end_date);
        viewTextCourseAssessments = findViewById(R.id.view_courses);
        viewTextCourseId = findViewById(R.id.view_course_id);


        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Course Detailed View");
            viewTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            viewTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            viewTextStartDate.setText(intent.getStringExtra(EXTRA_START_DATE));
            viewTextEndDate.setText(intent.getStringExtra(EXTRA_END_DATE));
            //   viewTextCourseAssessments.setText(intent.getStringExtra(EXTRA_TERM_COURSES));
            viewTextCourseId.setText(intent.getStringExtra(EXTRA_ID));
            Toast.makeText(this, viewTextCourseId.getText().toString(), Toast.LENGTH_SHORT).show();

        }

        FloatingActionButton buttonEditCourse = findViewById(R.id.button_edit_course);
        buttonEditCourse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewCourseDetailedActivity.this, AddEditCourseActivity.class);
                intent.putExtra(AddEditCourseActivity.EXTRA_TITLE, viewTextTitle.getText().toString());
                intent.putExtra(AddEditCourseActivity.EXTRA_DESCRIPTION, viewTextDescription.getText().toString());
                intent.putExtra(AddEditCourseActivity.EXTRA_ID, viewTextCourseId.getText().toString());
                Toast.makeText(ViewCourseDetailedActivity.this, viewTextCourseId.getText().toString(), Toast.LENGTH_SHORT).show();
                intent.putExtra(AddEditCourseActivity.EXTRA_START_DATE, viewTextStartDate.getText().toString());
                intent.putExtra(AddEditCourseActivity.EXTRA_END_DATE, viewTextEndDate.getText().toString());
             //   intent.putExtra(AddEditCourseActivity.EXTRA_TERM_COURSES, viewTextCourseAssessments.getText().toString());
                startActivityForResult(intent,EDIT_TERM_REQUEST);
            }
        });

        FloatingActionButton buttonCourseCourseEdit = findViewById(R.id.button_add_course_assessment);
        buttonCourseCourseEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewCourseDetailedActivity.this, AddEditCourseAssessmentActivity.class);
                intent.putExtra(AddEditCourseActivity.EXTRA_ID, viewTextCourseId.getText().toString());
                Toast.makeText(ViewCourseDetailedActivity.this, viewTextCourseId.getText().toString(), Toast.LENGTH_SHORT).show();
                startActivityForResult(intent,ADD_COURSE_REQUEST);
            }
        });



        final CourseAdapter adapter = new CourseAdapter();

        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.getAllCourses().observe(this,new Observer<List<Course>>(){
            @Override
            public void onChanged(@Nullable List<Course> courses){
                adapter.setCourses(courses);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.view_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final AssessmentAdapter adapterC = new AssessmentAdapter();
        recyclerView.setAdapter(adapterC);
        AssessmentRepository.courseId = Integer.parseInt(viewTextCourseId.getText().toString());
        assessmentViewMode1 = ViewModelProviders.of(this).get(AssessmentViewModel.class);
        assessmentViewMode1.getCurrentAssessments().observe(this,new Observer<List<Assessment>>(){
            @Override
            public void onChanged(@Nullable List<Assessment> assessments){
                adapterC.setAssessments(assessments);
            }
        });

//        Toast.makeText(this, "Before the for loop " + courseViewMode1.getAllAssessments().getValue(), Toast.LENGTH_SHORT).show();
//       for (int i = 0; i < adapterC.getItemCount(); i++){
//           Toast.makeText(this, "Entering for loop " + adapterC.getItemCount(), Toast.LENGTH_SHORT).show();
//           viewTextCourseAssessments.setText(adapterC.getCourseAt(i).getTitle());
//       }



//        adapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Course course) {
//                Intent intent = new Intent (ViewCourseDetailedActivity.this, AddEditCourseActivity.class);
//                intent.putExtra(AddEditCourseActivity.EXTRA_TITLE, course.getTitle());
//                intent.putExtra(AddEditCourseActivity.EXTRA_DESCRIPTION, course.getDescription());
//                intent.putExtra(AddEditCourseActivity.EXTRA_ID, Integer.toString(course.getId()));
//                Toast.makeText(ViewCourseDetailedActivity.this, "this is detailed view extra_id  " + Integer.toString(course.getId()), Toast.LENGTH_SHORT).show();
//                intent.putExtra(AddEditCourseActivity.EXTRA_START_DATE, course.getStart());
//                intent.putExtra(AddEditCourseActivity.EXTRA_END_DATE, course.getEnd());
//                intent.putExtra(AddEditCourseActivity.EXTRA_TERM_COURSES,course.getCourseAssessments());
//                startActivityForResult(intent, EDIT_TERM_REQUEST);
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


            if(requestCode == EDIT_TERM_REQUEST && resultCode == RESULT_OK) {
                Toast.makeText(this, "Entering into RESULT_OK", Toast.LENGTH_SHORT).show();
            int id = data.getIntExtra(AddEditCourseActivity.EXTRA_ID, -1);
            if(id == -1){
                Toast.makeText(this, "Errors Encountered", Toast.LENGTH_SHORT).show();
            }
            String title = data.getStringExtra(AddEditCourseActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditCourseActivity.EXTRA_DESCRIPTION); //need non null default value for int
            String start = data.getStringExtra(AddEditCourseActivity.EXTRA_START_DATE);
            String end = data.getStringExtra(AddEditCourseActivity.EXTRA_END_DATE);
                viewTextTitle.setText(title);
                viewTextDescription.setText(description);
                viewTextStartDate.setText(start);
                viewTextEndDate.setText(end);
                viewTextCourseId.setText(Integer.toString(id));
//            Course course = new Course(title,description,start,end);
//            course.setId(id);
//            courseViewModel.update(course);
        }
            else if(requestCode == ADD_COURSE_REQUEST && resultCode == RESULT_OK) {
                Toast.makeText(this, "Entering into RESULT_OK", Toast.LENGTH_SHORT).show();
                int id = data.getIntExtra(AddEditCourseActivity.EXTRA_ID, -1);
                if(id == -1){
                    Toast.makeText(this, "Errors Encountered", Toast.LENGTH_SHORT).show();
                }


                viewTextCourseId.setText(Integer.toString(id));

            }

    }


}
