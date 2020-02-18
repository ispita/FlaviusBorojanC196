package com.example.flaviusborojanc196;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.flaviusborojanc196.FlaviusBorojanC196.CHANNEL_1_ID;
import static com.example.flaviusborojanc196.FlaviusBorojanC196.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_TERM_REQUEST = 1;
    public static final int ADD_COURSE_REQUEST = 1;
    public static final int ADD_ASSESSMENT_REQUEST = 1;
//    private CourseViewModel courseViewModel;
//    private AssessmentViewModel assessmentViewModel;
//    private NotificationManagerCompat notificationManager;
//    public static List<String> courseStartDateList = new ArrayList<>();
//    public static List<String> courseEndDateList = new ArrayList<>();
//    public static List<String> assessmentDateList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        notificationManager = NotificationManagerCompat.from(this);


        Button buttonViewTerm = findViewById(R.id.view_term_button);
        buttonViewTerm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ViewTermActivity.class);
                startActivityForResult(intent,ADD_TERM_REQUEST);
            }
        });

        Button buttonViewCourse = findViewById(R.id.view_course_button);
        buttonViewCourse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ViewCourseActivity.class);
                startActivityForResult(intent,ADD_COURSE_REQUEST);
            }
        });


        Button buttonViewAssessment = findViewById(R.id.view_assessment_button);
        buttonViewAssessment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ViewAssessmentActivity.class);
                startActivityForResult(intent,ADD_ASSESSMENT_REQUEST);
            }
        });

//
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
//                .setSmallIcon(R.drawable.ic_delete)
//                .setContentTitle("test")
//                .setContentText("oncreateTest")
//                .setPriority(NotificationCompat.PRIORITY_LOW)
//                .build();
//        notificationManager.notify(2, notification);
//


//        SimpleDateFormat format = new SimpleDateFormat("MM/d/yyyy");
//        try {
//            courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
//            courseViewModel.getAllCourses().observe(this,new Observer<List<Course>>(){
//                @Override
//                public void onChanged(@Nullable List<Course> courses){
//                    for(int i = 0; i < courses.size(); i++) {
//                        MainActivity.courseStartDateList.add(courses.get(i).getStart());
//                        MainActivity.courseEndDateList.add(courses.get(i).getEnd());
//
//                    }
//                }
//            });
//
//            assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
//            assessmentViewModel.getAllAssessments().observe(this,new Observer<List<Assessment>>(){
//                @Override
//                public void onChanged(@Nullable List<Assessment> assessments){
//                    for(int i = 0; i < assessments.size(); i++) {
//                        MainActivity.assessmentDateList.add(assessments.get(i).getEnd());
//                    }
//                }
//            });
//
//            for (int i = 0; i < courseStartDateList.size(); i++) {
//                Long courseStartDateFormatted = format.parse(courseStartDateList.get(i)).getTime();
//                Toast.makeText(this, "toasting: " + i, Toast.LENGTH_SHORT).show();
//                if (DateUtils.isToday(courseStartDateFormatted)) {
//
//                    Toast.makeText(this, "this is today" + courseStartDateList.get(i), Toast.LENGTH_SHORT).show();
//                    AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//                    DateBroadcast notification = new DateBroadcast();
//                    IntentFilter intentFilter = new IntentFilter("ALARM_ACTION");
//                    registerReceiver(notification, intentFilter);
//                    Intent intent = new Intent("ALARM_ACTION");
//                    intent.putExtra("title", "Course Starting Today! DIFFERENTIAL");
//                    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
//                    alarm.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + Toast.LENGTH_SHORT, pendingIntent);
//
//                }
//                else{
//                    Toast.makeText(this, "this is not today121", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
    }

//    public void sendOnChannel1(View v) {
//        String title = editTextTitle.getText().toString();
//        String message = editTextMessage.getText().toString();
//
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
//                .setSmallIcon(R.drawable.ic_save)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                .build();
//
//        notificationManager.notify(1, notification);
//    }
//
//    public void sendOnChannel2(View v) {
//        String title = editTextTitle.getText().toString();
//        String message = editTextMessage.getText().toString();
//
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
//                .setSmallIcon(R.drawable.ic_delete)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setPriority(NotificationCompat.PRIORITY_LOW)
//                .build();
//
//        notificationManager.notify(2, notification);
//    }




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


    }
}
