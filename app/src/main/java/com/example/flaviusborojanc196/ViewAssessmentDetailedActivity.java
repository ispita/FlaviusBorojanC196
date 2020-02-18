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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ViewAssessmentDetailedActivity extends AppCompatActivity {
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
    private TextView viewTextEndDate;
    private TextView viewTextAssessmentId;
    private RecyclerView viewTextAssessmentCourses;
    private AssessmentViewModel assessmentViewModel;
    private CourseViewModel courseViewMode1;
    private List<Course> coursesList;

    public static final int ADD_ASSESSMENT_REQUEST = 1;
    public static final int EDIT_ASSESSMENT_REQUEST = 2;
    public static final int VIEW_ASSESSMENT_DETAILED_REQUEST = 3;
    public static final int ADD_COURSE_REQUEST = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assessment_detailed);

        viewTextTitle = findViewById(R.id.view_title);
        viewTextDescription = findViewById(R.id.view_description);
        viewTextEndDate = findViewById(R.id.view_assessment_end_date);
        viewTextAssessmentCourses = findViewById(R.id.view_courses);
        viewTextAssessmentId = findViewById(R.id.view_assessment_id);


        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Assessment Detailed View");
            viewTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            viewTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            viewTextEndDate.setText(intent.getStringExtra(EXTRA_END_DATE));
            //   viewTextAssessmentCourses.setText(intent.getStringExtra(EXTRA_ASSESSMENT_COURSES));
            viewTextAssessmentId.setText(intent.getStringExtra(EXTRA_ID));
            Toast.makeText(this, viewTextAssessmentId.getText().toString(), Toast.LENGTH_SHORT).show();

        }

        FloatingActionButton buttonEditAssessment = findViewById(R.id.button_edit_assessment);
        buttonEditAssessment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAssessmentDetailedActivity.this, AddEditAssessmentActivity.class);
                intent.putExtra(AddEditAssessmentActivity.EXTRA_TITLE, viewTextTitle.getText().toString());
                intent.putExtra(AddEditAssessmentActivity.EXTRA_DESCRIPTION, viewTextDescription.getText().toString());
                intent.putExtra(AddEditAssessmentActivity.EXTRA_ID, viewTextAssessmentId.getText().toString());
                Toast.makeText(ViewAssessmentDetailedActivity.this, viewTextAssessmentId.getText().toString(), Toast.LENGTH_SHORT).show();
                intent.putExtra(AddEditAssessmentActivity.EXTRA_END_DATE, viewTextEndDate.getText().toString());
                startActivityForResult(intent,EDIT_ASSESSMENT_REQUEST);
            }
        });



        final AssessmentAdapter adapter = new AssessmentAdapter();

        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
        assessmentViewModel.getAllAssessments().observe(this,new Observer<List<Assessment>>(){
            @Override
            public void onChanged(@Nullable List<Assessment> assessments){
                adapter.setAssessments(assessments);
            }
        });

        FloatingActionButton buttonDeleteAssessment = findViewById(R.id.button_delete_assessment);
        buttonDeleteAssessment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                for (int i = 0; i < adapter.getItemCount(); i++) {
                    Intent intent = new Intent(ViewAssessmentDetailedActivity.this, ViewAssessmentActivity.class);
                    if (Integer.parseInt(viewTextAssessmentId.getText().toString()) == adapter.getAssessmentAt(i).getId()) {
                        assessmentViewModel.delete(adapter.getAssessmentAt(i));
                        startActivity(intent);
                    }

                }

            }
        });

        FloatingActionButton buttonRemindAssessment = findViewById(R.id.button_reminder_assessment);
        buttonRemindAssessment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SimpleDateFormat format = new SimpleDateFormat("MM/d/yyyy");
                Toast.makeText(ViewAssessmentDetailedActivity.this, "Alarm set for this assessment!", Toast.LENGTH_SHORT).show();
                AlarmManager alarm = (AlarmManager) ViewAssessmentDetailedActivity.this.getSystemService(Context.ALARM_SERVICE);
                final int requestCodeInt = (int) System.currentTimeMillis();
                DateBroadcast notification = new DateBroadcast();
                IntentFilter intentFilter = new IntentFilter("ALARM_ACTION");
                registerReceiver(notification, intentFilter);
                Intent intent = new Intent( ViewAssessmentDetailedActivity.this, DateBroadcast.class);
                intent.putExtra("title", "Assessment " + viewTextTitle.getText() + " Starting Today!");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(ViewAssessmentDetailedActivity.this, requestCodeInt, intent, 0);
                Calendar cal = Calendar.getInstance();
                try {
                    cal.setTime(format.parse(viewTextEndDate.getText().toString()));
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


            if(requestCode == EDIT_ASSESSMENT_REQUEST && resultCode == RESULT_OK) {
                Toast.makeText(this, "Entering into RESULT_OK", Toast.LENGTH_SHORT).show();
            int id = data.getIntExtra(AddEditAssessmentActivity.EXTRA_ID, -1);
            if(id == -1){
                Toast.makeText(this, "Errors Encountered", Toast.LENGTH_SHORT).show();
            }
            String title = data.getStringExtra(AddEditAssessmentActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditAssessmentActivity.EXTRA_DESCRIPTION); //need non null default value for int
            String start = data.getStringExtra(AddEditAssessmentActivity.EXTRA_START_DATE);
            String end = data.getStringExtra(AddEditAssessmentActivity.EXTRA_END_DATE);
                viewTextTitle.setText(title);
                viewTextDescription.setText(description);
                viewTextEndDate.setText(end);
                viewTextAssessmentId.setText(Integer.toString(id));
            Assessment assessment = new Assessment(title,description,end);
            assessment.setId(id);
            assessmentViewModel.update(assessment);
        }


    }


}
