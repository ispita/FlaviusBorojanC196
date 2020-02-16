package com.example.flaviusborojanc196;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.List;

public class AddEditTermCourseActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.flaviusborojanc196.EXTRA_ID";
    public static final String EXTRA_TERM_COURSES=
            "com.example.flaviusborojanc196.EXTRA_TERM_COURSES";



    private CourseViewModel courseViewModel;
    private CourseViewModel courseViewModelB;
    private int termId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term_courses);

        Intent intent = getIntent();
        setTitle("Add/Remove Term Courses");
        termId = Integer.parseInt(intent.getStringExtra(EXTRA_ID));




        RecyclerView recyclerView = findViewById(R.id.course_add_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CourseAdapter adapter = new CourseAdapter();
        recyclerView.setAdapter(adapter);

        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.getAvailableCourses().observe(this,new Observer<List<Course>>(){
            @Override
            public void onChanged(@Nullable List<Course> courses){
                adapter.setCourses(courses);
            }
        });

        RecyclerView addedCourses = findViewById(R.id.courses_added);
        addedCourses.setLayoutManager(new LinearLayoutManager(this));
        addedCourses.setHasFixedSize(true);

        final CourseAdapter adapterCurrent = new CourseAdapter();
        addedCourses.setAdapter(adapterCurrent);

        courseViewModelB = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModelB.getCurrentCourses().observe(this,new Observer<List<Course>>(){
            @Override
            public void onChanged(@Nullable List<Course> courses){
                adapterCurrent.setCourses(courses);
            }
        });


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
                    TermCourses addTermCourses = new TermCourses(termId,course.getId());
                    courseViewModel.insertTermCourses(addTermCourses);
            }
        });

        adapterCurrent.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                    CourseRepository.termId = termId;
                    CourseRepository.courseId = course.getId();
                    courseViewModelB.deleteTermCourses();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
                Intent data = new Intent();
                int id = termId;
                if (id != -1){
                    data.putExtra(EXTRA_ID, id);
                }
                setResult(RESULT_OK, data);
                finish();
                return true;
        }
}
