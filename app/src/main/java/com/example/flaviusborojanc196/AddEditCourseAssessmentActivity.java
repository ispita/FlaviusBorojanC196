package com.example.flaviusborojanc196;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AddEditCourseAssessmentActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.flaviusborojanc196.EXTRA_ID";
    public static final String EXTRA_COURSE_ASSESSMENTS=
            "com.example.flaviusborojanc196.EXTRA_TERM_COURSES";



    private AssessmentViewModel assessmentViewModel;
    private AssessmentViewModel assessmentViewModelB;
    private int courseId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_assessments);

        Intent intent = getIntent();
        setTitle("Add/Remove Course Assessments");
        courseId = Integer.parseInt(intent.getStringExtra(EXTRA_ID));





        RecyclerView recyclerView = findViewById(R.id.assessment_add_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final AssessmentAdapter adapter = new AssessmentAdapter();
        recyclerView.setAdapter(adapter);

        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
        assessmentViewModel.getAvailableAssessments().observe(this,new Observer<List<Assessment>>(){
            @Override
            public void onChanged(@Nullable List<Assessment> assessments){
                adapter.setAssessments(assessments);
            }
        });

        RecyclerView addedAssessments = findViewById(R.id.assessments_added);
        addedAssessments.setLayoutManager(new LinearLayoutManager(this));
        addedAssessments.setHasFixedSize(true);

        final AssessmentAdapter adapterCurrent = new AssessmentAdapter();
        addedAssessments.setAdapter(adapterCurrent);

        assessmentViewModelB = ViewModelProviders.of(this).get(AssessmentViewModel.class);
        assessmentViewModelB.getCurrentAssessments().observe(this,new Observer<List<Assessment>>(){
            @Override
            public void onChanged(@Nullable List<Assessment> assessments){
                adapterCurrent.setAssessments(assessments);
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                assessmentViewModel.delete(adapter.getAssessmentAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new AssessmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Assessment assessment) {
                CourseAssessments addCourseAssessments = new CourseAssessments(courseId,assessment.getId());
                assessmentViewModel.insertCourseAssessments(addCourseAssessments);
            }
        });

        adapterCurrent.setOnItemClickListener(new AssessmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Assessment assessment) {
                AssessmentRepository.courseId = courseId;
                AssessmentRepository.assessmentId = assessment.getId();
                assessmentViewModelB.deleteCourseAssessments();
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
                int id = courseId;
                if (id != -1){
                    data.putExtra(EXTRA_ID, id);
                }
                setResult(RESULT_OK, data);
                finish();
                return true;
    }
}
