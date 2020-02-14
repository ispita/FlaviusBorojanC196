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
import androidx.room.Query;

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

public class AddEditTermCourseActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.flaviusborojanc196.EXTRA_ID";
    public static final String EXTRA_TERM_COURSES=
            "com.example.flaviusborojanc196.EXTRA_TERM_COURSES";



    private CourseViewModel courseViewModel;
    private TextView addedCourses;
    private int termId;
    private List<String> courseArray = new ArrayList<>();
    private List<Integer> courseArrayId = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term_courses);



        addedCourses = findViewById(R.id.courses_added);


        RecyclerView recyclerView = findViewById(R.id.course_add_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CourseAdapter adapter = new CourseAdapter();
        recyclerView.setAdapter(adapter);

        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.getAllCourses().observe(this,new Observer<List<Course>>(){
            @Override
            public void onChanged(@Nullable List<Course> courses){
                adapter.setCourses(courses);
            }
        });


            Intent intent = getIntent();
            setTitle("Add/Remove Term Courses");
            termId = Integer.parseInt(intent.getStringExtra(EXTRA_ID));
            addedCourses.setText(intent.getStringExtra(EXTRA_TERM_COURSES));
               Toast.makeText(this, "testing open " + termId, Toast.LENGTH_SHORT).show();
            String[] addedCoursesArray = (addedCourses.getText().toString().split(","));
            Collections.addAll(courseArray,addedCoursesArray);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                courseViewModel.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                if (!courseArrayId.contains(course.getId())) {
                    courseArrayId.add(course.getId());
                    courseArray.add(course.getTitle());
                    TermCourses addTermCourses = new TermCourses(termId,course.getId());
                    courseViewModel.insertTermCourses(addTermCourses);

                    addedCourses.append("\n" +courseArray.get(courseArray.size() - 1) + "\n");
                } else {
                    courseArrayId.remove(course.getId());
                    courseArray.remove(course.getTitle());
                    addedCourses.setText("");
                    for (int j = 0; j < courseArray.size(); j++) {
                        addedCourses.append("\n" + courseArray.get(j) + "\n");
                    }
                }

            }
        });

    }
    private void saveTermCourses(){

        addedCourses.setText("");
        for(int j = 0; j < courseArray.size(); j++) {
            if (j == courseArray.size() - 1){
                addedCourses.append(courseArray.get(j));
            }
            else {
                addedCourses.append(courseArray.get(j) + ",");
            }
        }
        String termCourses = addedCourses.getText().toString();


        Intent data = new Intent();
        data.putExtra(EXTRA_TERM_COURSES, termCourses);
        int id = termId;
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
                saveTermCourses();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
