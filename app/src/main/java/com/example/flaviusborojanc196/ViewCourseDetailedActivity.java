package com.example.flaviusborojanc196;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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



    private TextView viewTextTitle;
    private TextView viewTextDescription;
    private TextView viewTextStartDate;
    private TextView viewTextEndDate;
    private TextView viewTextCourseId;
    private TextView viewTextStatus;
    private TextView viewTextMentor;
    private TextView viewTextPhone;
    private TextView viewTextEmail;
    private TextView viewTextNote;
    private RecyclerView viewTextCourseAssessments;
    private CourseViewModel courseViewModel;
    private AssessmentViewModel assessmentViewMode1;
    private List<Course> coursesList;


    public static final int ADD_COURSE_REQUEST = 1;
    public static final int EDIT_COURSE_REQUEST = 2;
    public static final int VIEW_COURSE_DETAILED_REQUEST = 3;
    public static final int ADD_ASSESSMENT_REQUEST = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course_detailed);

        viewTextTitle = findViewById(R.id.view_title);
        viewTextDescription = findViewById(R.id.view_description);
        viewTextStartDate = findViewById(R.id.view_course_start_date);
        viewTextEndDate = findViewById(R.id.view_course_end_date);
        viewTextCourseAssessments = findViewById(R.id.view_assessments);
        viewTextCourseId = findViewById(R.id.view_course_id);
        viewTextStatus = findViewById(R.id.view_course_status);
        viewTextMentor = findViewById(R.id.view_course_mentor);
        viewTextPhone = findViewById(R.id.view_course_mentor_phone);
        viewTextEmail = findViewById(R.id.view_course_mentor_email);
        viewTextNote = findViewById(R.id.view_course_note);


        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Course Detailed View");
            viewTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            viewTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            viewTextStartDate.setText(intent.getStringExtra(EXTRA_START_DATE));
            viewTextEndDate.setText(intent.getStringExtra(EXTRA_END_DATE));
            viewTextCourseId.setText(intent.getStringExtra(EXTRA_ID));
            viewTextStatus.setText(intent.getStringExtra(EXTRA_STATUS));
            viewTextMentor.setText(intent.getStringExtra(EXTRA_MENTOR));
            viewTextPhone.setText(intent.getStringExtra(EXTRA_PHONE));
            viewTextEmail.setText(intent.getStringExtra(EXTRA_EMAIL));
            viewTextNote.setText(intent.getStringExtra(EXTRA_NOTE));
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
                intent.putExtra(AddEditCourseActivity.EXTRA_START_DATE, viewTextStartDate.getText().toString());
                intent.putExtra(AddEditCourseActivity.EXTRA_END_DATE, viewTextEndDate.getText().toString());
                intent.putExtra(AddEditCourseActivity.EXTRA_STATUS, viewTextStatus.getText().toString());
                intent.putExtra(AddEditCourseActivity.EXTRA_MENTOR, viewTextMentor.getText().toString());
                intent.putExtra(AddEditCourseActivity.EXTRA_PHONE, viewTextPhone.getText().toString());
                intent.putExtra(AddEditCourseActivity.EXTRA_EMAIL, viewTextEmail.getText().toString());
                intent.putExtra(AddEditCourseActivity.EXTRA_NOTE, viewTextNote.getText().toString());
                        startActivityForResult(intent,EDIT_COURSE_REQUEST);
            }
        });

        FloatingActionButton buttonCourseAssessmentEdit = findViewById(R.id.button_add_course_assessment);
        buttonCourseAssessmentEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewCourseDetailedActivity.this, AddEditCourseAssessmentActivity.class);
                intent.putExtra(AddEditCourseActivity.EXTRA_ID, viewTextCourseId.getText().toString());
                Toast.makeText(ViewCourseDetailedActivity.this, viewTextCourseId.getText().toString(), Toast.LENGTH_SHORT).show();
                startActivityForResult(intent,ADD_ASSESSMENT_REQUEST);
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

        RecyclerView recyclerView = findViewById(R.id.view_assessments);
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


        FloatingActionButton buttonDeleteCourse = findViewById(R.id.button_delete_course);
        buttonDeleteCourse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                    for (int i = 0; i < adapter.getItemCount(); i++) {
                        Intent intent = new Intent(ViewCourseDetailedActivity.this, ViewCourseActivity.class);
                        if (Integer.parseInt(viewTextCourseId.getText().toString()) == adapter.getCourseAt(i).getId()) {
                            courseViewModel.delete(adapter.getCourseAt(i));
                            startActivity(intent);
                        }

                    }

            }
        });

        FloatingActionButton buttonRemindCourse = findViewById(R.id.button_reminder_course);
        buttonRemindCourse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewCourseDetailedActivity.this, "Alarm set for this course!", Toast.LENGTH_SHORT).show();
                AlarmManager alarm = (AlarmManager) ViewCourseDetailedActivity.this.getSystemService(Context.ALARM_SERVICE);
                DateBroadcast notification = new DateBroadcast();
                IntentFilter intentFilter = new IntentFilter("ALARM_ACTION");
                registerReceiver(notification, intentFilter);
                Intent intent = new Intent( ViewCourseDetailedActivity.this, DateBroadcast.class);
                intent.putExtra("title", "Course " + viewTextTitle.getText() + " Starting Today!");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(ViewCourseDetailedActivity.this, 0, intent, 0);
                alarm.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + Toast.LENGTH_SHORT, pendingIntent);
                unregisterReceiver(notification);

                }
        });
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


            if(requestCode == EDIT_COURSE_REQUEST && resultCode == RESULT_OK) {
                Toast.makeText(this, "Entering into RESULT_OK", Toast.LENGTH_SHORT).show();
            int id = data.getIntExtra(AddEditCourseActivity.EXTRA_ID, -1);
            if(id == -1){
                Toast.makeText(this, "Errors Encountered", Toast.LENGTH_SHORT).show();
            }
            String title = data.getStringExtra(AddEditCourseActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditCourseActivity.EXTRA_DESCRIPTION); //need non null default value for int
            String start = data.getStringExtra(AddEditCourseActivity.EXTRA_START_DATE);
            String end = data.getStringExtra(AddEditCourseActivity.EXTRA_END_DATE);
            String status = data.getStringExtra(AddEditCourseActivity.EXTRA_STATUS);
            String mentor = data.getStringExtra(AddEditCourseActivity.EXTRA_MENTOR);
            String phone = data.getStringExtra(AddEditCourseActivity.EXTRA_PHONE);
            String email = data.getStringExtra(AddEditCourseActivity.EXTRA_EMAIL);
            String note = data.getStringExtra(AddEditCourseActivity.EXTRA_NOTE);
                viewTextTitle.setText(title);
                viewTextDescription.setText(description);
                viewTextStartDate.setText(start);
                viewTextEndDate.setText(end);
                viewTextCourseId.setText(Integer.toString(id));
                viewTextStatus.setText(status);
                viewTextMentor.setText(mentor);
                viewTextPhone.setText(phone);
                viewTextEmail.setText(email);
                viewTextNote.setText(note);
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
