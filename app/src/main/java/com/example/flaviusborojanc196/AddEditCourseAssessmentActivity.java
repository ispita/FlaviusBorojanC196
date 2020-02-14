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

import java.util.ArrayList;
import java.util.List;

public class AddEditCourseAssessmentActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.flaviusborojanc196.EXTRA_ID";
    public static final String EXTRA_TERM_COURSES=
            "com.example.flaviusborojanc196.EXTRA_TERM_COURSES";



    private AssessmentViewModel assessmentViewModel;
    private AssessmentViewModel assessmentViewModelB;
    private RecyclerView addedAssessments;
    private int courseId;
    private List<String> assessmentArray = new ArrayList<>();
    private List<Integer> assessmentArrayId = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_assessments);

        Intent intent = getIntent();
        setTitle("Add/Remove Assessment Assessments");
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

        addedAssessments = findViewById(R.id.assessments_added);
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



//            addedAssessments.setText(intent.getStringExtra(EXTRA_TERM_COURSES));
//               Toast.makeText(this, "testing open " + courseId, Toast.LENGTH_SHORT).show();
//            String[] addedAssessmentsArray = (addedAssessments.getText().toString().split(","));
//            Collections.addAll(assessmentArray,addedAssessmentsArray);

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
                if (!assessmentArrayId.contains(assessment.getId())) {
                    assessmentArrayId.add(assessment.getId());
                    assessmentArray.add(assessment.getTitle());
                    CourseAssessments addCourseAssessments = new CourseAssessments(courseId,assessment.getId());
                    assessmentViewModel.insertCourseAssessments(addCourseAssessments);

//                    addedAssessments.append("\n" +assessmentArray.get(assessmentArray.size() - 1) + "\n");
                } else {
                    assessmentArrayId.remove(assessment.getId());
                    assessmentArray.remove(assessment.getTitle());
//                    addedAssessments.setText("");
//                    for (int j = 0; j < assessmentArray.size(); j++) {
//                        addedAssessments.append("\n" + assessmentArray.get(j) + "\n");
//                    }
                }

            }
        });

    }
    private void saveTermAssessments(){

//        addedAssessments.setText("");
//        for(int j = 0; j < assessmentArray.size(); j++) {
//            if (j == assessmentArray.size() - 1){
//                addedAssessments.append(assessmentArray.get(j));
//            }
//            else {
//                addedAssessments.append(assessmentArray.get(j) + ",");
//            }
//        }
//        String courseAssessments = addedAssessments.getText().toString();


        Intent data = new Intent();
//        data.putExtra(EXTRA_TERM_COURSES, courseAssessments);
        int id = courseId;
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
                saveTermAssessments();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
