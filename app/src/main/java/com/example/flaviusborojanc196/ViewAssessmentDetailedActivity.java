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
    private TextView viewTextGoalDate;
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
        viewTextGoalDate = findViewById(R.id.view_assessment_goal_date);
        viewTextEndDate = findViewById(R.id.view_assessment_end_date);
        viewTextAssessmentCourses = findViewById(R.id.view_courses);
        viewTextAssessmentId = findViewById(R.id.view_assessment_id);


        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Assessment Detailed View");
            viewTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            viewTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            viewTextGoalDate.setText(intent.getStringExtra(EXTRA_START_DATE));
            viewTextEndDate.setText(intent.getStringExtra(EXTRA_END_DATE));
            viewTextAssessmentId.setText(intent.getStringExtra(EXTRA_ID));

        }

        FloatingActionButton buttonEditAssessment = findViewById(R.id.button_edit_assessment);
        buttonEditAssessment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAssessmentDetailedActivity.this, AddEditAssessmentActivity.class);
                intent.putExtra(AddEditAssessmentActivity.EXTRA_TITLE, viewTextTitle.getText().toString());
                intent.putExtra(AddEditAssessmentActivity.EXTRA_DESCRIPTION, viewTextDescription.getText().toString());
                intent.putExtra(AddEditAssessmentActivity.EXTRA_ID, viewTextAssessmentId.getText().toString());
                intent.putExtra(AddEditAssessmentActivity.EXTRA_END_DATE, viewTextEndDate.getText().toString());
                intent.putExtra(AddEditAssessmentActivity.EXTRA_START_DATE, viewTextGoalDate.getText().toString());

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
        Button buttonRemindAssessmentGoal = findViewById(R.id.button_reminder_assessment);
        buttonRemindAssessmentGoal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewAssessmentDetailedActivity.this, "Alarm set for this Assessment Goal date!", Toast.LENGTH_SHORT).show();
                SimpleDateFormat format = new SimpleDateFormat("MM/d/yyyy");
                AlarmManager alarm = (AlarmManager) ViewAssessmentDetailedActivity.this.getSystemService(Context.ALARM_SERVICE);
                final int requestCodeInt = (int) System.currentTimeMillis();
                DateBroadcast notification = new DateBroadcast();
                IntentFilter intentFilter = new IntentFilter("ALARM_ACTION");
                registerReceiver(notification, intentFilter);
                Intent intent = new Intent( ViewAssessmentDetailedActivity.this, DateBroadcast.class);
                intent.putExtra("title", "Assessment " + viewTextTitle.getText() + " Goal Date Today!");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(ViewAssessmentDetailedActivity.this, requestCodeInt, intent, 0);
                Calendar cal = Calendar.getInstance();
                try {
                    cal.setTime(format.parse(viewTextGoalDate.getText().toString()));
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
        Button buttonRemindAssessmentEnd = findViewById(R.id.button_reminder_assessment_end);
        buttonRemindAssessmentEnd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewAssessmentDetailedActivity.this, "Alarm set for this Assessment End date!", Toast.LENGTH_SHORT).show();
                SimpleDateFormat format = new SimpleDateFormat("MM/d/yyyy");
                AlarmManager alarm = (AlarmManager) ViewAssessmentDetailedActivity.this.getSystemService(Context.ALARM_SERVICE);
                final int requestCodeInt = (int) System.currentTimeMillis();
                DateBroadcast notification = new DateBroadcast();
                IntentFilter intentFilter = new IntentFilter("ALARM_ACTION");
                registerReceiver(notification, intentFilter);
                Intent intent2 = new Intent( ViewAssessmentDetailedActivity.this, DateBroadcast.class);
                intent2.putExtra("title", "Assessment " + viewTextTitle.getText() + " Due Today!");
                PendingIntent pendingIntent2 = PendingIntent.getBroadcast(ViewAssessmentDetailedActivity.this, requestCodeInt, intent2, 0);
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
                long alertDate2 = cal.getTimeInMillis();
                alarm.setExact(AlarmManager.RTC_WAKEUP, alertDate2, pendingIntent2);
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
            int id = data.getIntExtra(AddEditAssessmentActivity.EXTRA_ID, -1);
            if(id == -1){
                Toast.makeText(this, "Errors Encountered", Toast.LENGTH_SHORT).show();
            }
            String title = data.getStringExtra(AddEditAssessmentActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditAssessmentActivity.EXTRA_DESCRIPTION); //need non null default value for int
            String goal = data.getStringExtra(AddEditAssessmentActivity.EXTRA_START_DATE);
            String end = data.getStringExtra(AddEditAssessmentActivity.EXTRA_END_DATE);

                viewTextTitle.setText(title);
                viewTextDescription.setText(description);
                viewTextGoalDate.setText(goal);
                viewTextEndDate.setText(end);
                viewTextAssessmentId.setText(Integer.toString(id));
            Assessment assessment = new Assessment(title,description,end,goal);
            assessment.setId(id);
            assessmentViewModel.update(assessment);
        }


    }


}
