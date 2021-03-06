package com.example.flaviusborojanc196;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ViewCourseActivity extends AppCompatActivity {
    public static final int ADD_COURSE_REQUEST = 1;
    public static final int EDIT_COURSE_REQUEST = 2;
    public static final int VIEW_COURSE_DETAILED_REQUEST = 3;


    private CourseViewModel courseViewModel;
    private NoteViewModel noteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);


        FloatingActionButton buttonAddCourse = findViewById(R.id.button_add_course);
        buttonAddCourse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewCourseActivity.this, AddEditCourseActivity.class);
                startActivityForResult(intent,ADD_COURSE_REQUEST);
            }
        });




        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CourseAdapter adapter = new CourseAdapter();
        final NoteAdapter adapterN = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.getAllCourses().observe(this,new Observer<List<Course>>(){
            @Override
            public void onChanged(@Nullable List<Course> courses){
                adapter.setCourses(courses);
            }
        });

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this,new Observer<List<Note>>(){
            @Override
            public void onChanged(@Nullable List<Note> notes){
                adapterN.setNotes(notes);
            }
        });



        adapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                Intent intent = new Intent (ViewCourseActivity.this, ViewCourseDetailedActivity.class);
                intent.putExtra(ViewCourseDetailedActivity.EXTRA_TITLE, course.getTitle());
                intent.putExtra(ViewCourseDetailedActivity.EXTRA_DESCRIPTION, course.getDescription());
                intent.putExtra(ViewCourseDetailedActivity.EXTRA_ID, Integer.toString(course.getId()));
                intent.putExtra(ViewCourseDetailedActivity.EXTRA_START_DATE, course.getStart());
                intent.putExtra(ViewCourseDetailedActivity.EXTRA_END_DATE, course.getEnd());
                intent.putExtra(ViewCourseDetailedActivity.EXTRA_STATUS, course.getStatus());
                intent.putExtra(ViewCourseDetailedActivity.EXTRA_MENTOR, course.getMentor());
                intent.putExtra(ViewCourseDetailedActivity.EXTRA_PHONE, course.getPhone());
                intent.putExtra(ViewCourseDetailedActivity.EXTRA_EMAIL, course.getEmail());
                startActivityForResult(intent, VIEW_COURSE_DETAILED_REQUEST);
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

        if(requestCode == ADD_COURSE_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(AddEditCourseActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditCourseActivity.EXTRA_DESCRIPTION); //need non null default value for int
            String start = data.getStringExtra(AddEditCourseActivity.EXTRA_START_DATE);
            String end = data.getStringExtra(AddEditCourseActivity.EXTRA_END_DATE);
            String status = data.getStringExtra(AddEditCourseActivity.EXTRA_STATUS);
            String mentor = data.getStringExtra(AddEditCourseActivity.EXTRA_MENTOR);
            String phone = data.getStringExtra(AddEditCourseActivity.EXTRA_PHONE);
            String email = data.getStringExtra(AddEditCourseActivity.EXTRA_EMAIL);


            Course course = new Course(title,description,start,end,status,mentor,phone,email);
            courseViewModel.insert(course);
            noteViewModel.newCourseNoteInsert();

            Toast.makeText(this, "Course Saved", Toast.LENGTH_SHORT).show();
        }
        else if(requestCode == EDIT_COURSE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditCourseActivity.EXTRA_ID, -1);
            if(id == -1){
                Toast.makeText(this, "Issues Arised", Toast.LENGTH_SHORT).show();
            }
            String title = data.getStringExtra(AddEditCourseActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditCourseActivity.EXTRA_DESCRIPTION); //need non null default value for int
            String start = data.getStringExtra(AddEditCourseActivity.EXTRA_START_DATE);
            String end = data.getStringExtra(AddEditCourseActivity.EXTRA_END_DATE);
            String status = data.getStringExtra(AddEditCourseActivity.EXTRA_STATUS);
            String mentor = data.getStringExtra(AddEditCourseActivity.EXTRA_MENTOR);
            String phone = data.getStringExtra(AddEditCourseActivity.EXTRA_PHONE);
            String email = data.getStringExtra(AddEditCourseActivity.EXTRA_EMAIL);

            Course course = new Course(title,description,start,end,status,mentor,phone,email);
            course.setId(id);
            courseViewModel.update(course);

        }
        else{
            Toast.makeText(this, "ERROR SAVING COURSE", Toast.LENGTH_SHORT).show();
        }
    }
}
