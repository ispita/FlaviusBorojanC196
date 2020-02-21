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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private RecyclerView viewTextCourseAssessments;
    private CourseViewModel courseViewModel;
    private AssessmentViewModel assessmentViewMode1;
    private NoteViewModel noteViewModel;
    private List<Course> coursesList;


    public static final int ADD_COURSE_REQUEST = 1;
    public static final int EDIT_COURSE_REQUEST = 2;
    public static final int VIEW_COURSE_DETAILED_REQUEST = 3;
    public static final int ADD_ASSESSMENT_REQUEST = 4;
    public static final int VIEW_NOTE_DETAILED_REQUEST = 5;

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

        RecyclerView noteRecycler = findViewById(R.id.view_course_note);
        noteRecycler.setLayoutManager(new LinearLayoutManager(this));
        noteRecycler.setHasFixedSize(true);
        final NoteAdapter adapterN = new NoteAdapter();
        noteRecycler.setAdapter(adapterN);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getCurrentNotes().observe(this,new Observer<List<Note>>(){
            @Override
            public void onChanged(@Nullable List<Note> notes){
                adapterN.setNotes(notes);
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

        Button buttonRemindCourse = findViewById(R.id.button_reminder_course);
        buttonRemindCourse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SimpleDateFormat format = new SimpleDateFormat("MM/d/yyyy");
                Toast.makeText(ViewCourseDetailedActivity.this, "Alarm set for this course start date!", Toast.LENGTH_SHORT).show();
                AlarmManager alarm = (AlarmManager) ViewCourseDetailedActivity.this.getSystemService(Context.ALARM_SERVICE);
                final int requestCodeInt = (int) System.currentTimeMillis();
                DateBroadcast notification = new DateBroadcast();
                IntentFilter intentFilter = new IntentFilter("ALARM_ACTION");
                registerReceiver(notification, intentFilter);
                Intent intent = new Intent( ViewCourseDetailedActivity.this, DateBroadcast.class);
                intent.putExtra("title", "Course " + viewTextTitle.getText() + " Starting Today!");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(ViewCourseDetailedActivity.this, requestCodeInt, intent, 0);
                Calendar cal = Calendar.getInstance();
                try {
                    cal.setTime(format.parse(viewTextStartDate.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 15);
                cal.set(Calendar.MILLISECOND, 0);
                long alertDate = cal.getTimeInMillis();
                alarm.setExact(AlarmManager.RTC_WAKEUP, alertDate, pendingIntent);
                unregisterReceiver(notification);

                }
        });

        Button buttonRemindCourseEnd = findViewById(R.id.button_reminder_course_end);
        buttonRemindCourseEnd .setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SimpleDateFormat format = new SimpleDateFormat("MM/d/yyyy");
                Toast.makeText(ViewCourseDetailedActivity.this, "Alarm set for this course end date!", Toast.LENGTH_SHORT).show();
                AlarmManager alarm2 = (AlarmManager) ViewCourseDetailedActivity.this.getSystemService(Context.ALARM_SERVICE);
                final int requestCodeInt = (int) System.currentTimeMillis();
                DateBroadcast notification = new DateBroadcast();
                IntentFilter intentFilter = new IntentFilter("ALARM_ACTION");
                registerReceiver(notification, intentFilter);
                Intent intent2 = new Intent( ViewCourseDetailedActivity.this, DateBroadcast.class);
                intent2.putExtra("title", "Course " + viewTextTitle.getText() + " Ends Today!");
                PendingIntent pendingIntent2 = PendingIntent.getBroadcast(ViewCourseDetailedActivity.this, requestCodeInt, intent2, 0);
                Calendar calEnd = Calendar.getInstance();
                try {
                    calEnd.setTime(format.parse(viewTextEndDate.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                calEnd.set(Calendar.HOUR_OF_DAY, 0);
                calEnd.set(Calendar.MINUTE, 0);
                calEnd.set(Calendar.SECOND, 15);
                calEnd.set(Calendar.MILLISECOND, 0);
                long alertDateEnd = calEnd.getTimeInMillis();
                alarm2.setExact(AlarmManager.RTC_WAKEUP, alertDateEnd, pendingIntent2);
                unregisterReceiver(notification);

            }
        });

        adapterN.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent (ViewCourseDetailedActivity.this, ViewNoteDetailedActivity.class);
                intent.putExtra(ViewCourseDetailedActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(ViewCourseDetailedActivity.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(ViewCourseDetailedActivity.EXTRA_ID, Integer.toString(note.getId()));
                startActivityForResult(intent, VIEW_NOTE_DETAILED_REQUEST);
            }
        });
    }

    public void addNote(View v){
        Note newNote = new Note(AssessmentRepository.courseId,"Note","Optional");
        noteViewModel.insert(newNote);
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
                viewTextTitle.setText(title);
                viewTextDescription.setText(description);
                viewTextStartDate.setText(start);
                viewTextEndDate.setText(end);
                viewTextCourseId.setText(Integer.toString(id));
                viewTextStatus.setText(status);
                viewTextMentor.setText(mentor);
                viewTextPhone.setText(phone);
                viewTextEmail.setText(email);
        }
            else if(requestCode == ADD_COURSE_REQUEST && resultCode == RESULT_OK) {
                int id = data.getIntExtra(AddEditCourseActivity.EXTRA_ID, -1);
                if(id == -1){
                    Toast.makeText(this, "Errors Encountered", Toast.LENGTH_SHORT).show();
                }


                viewTextCourseId.setText(Integer.toString(id));

            }

    }


}
