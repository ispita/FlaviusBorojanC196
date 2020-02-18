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

public class ViewAssessmentActivity extends AppCompatActivity {
    public static final int ADD_ASSESSMENT_REQUEST = 1;
    public static final int EDIT_ASSESSMENT_REQUEST = 2;
    public static final int VIEW_ASSESSMENT_DETAILED_REQUEST = 3;


    private AssessmentViewModel assessmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assessment);


        FloatingActionButton buttonAddAssessment = findViewById(R.id.button_add_assessment);
        buttonAddAssessment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAssessmentActivity.this, AddEditAssessmentActivity.class);
                startActivityForResult(intent,ADD_ASSESSMENT_REQUEST);
            }
        });




        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final AssessmentAdapter adapter = new AssessmentAdapter();
        recyclerView.setAdapter(adapter);

        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
        assessmentViewModel.getAllAssessments().observe(this,new Observer<List<Assessment>>(){
            @Override
            public void onChanged(@Nullable List<Assessment> assessments){
                adapter.setAssessments(assessments);
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
                Intent intent = new Intent (ViewAssessmentActivity.this, ViewAssessmentDetailedActivity.class);
                intent.putExtra(ViewAssessmentDetailedActivity.EXTRA_TITLE, assessment.getTitle());
                intent.putExtra(ViewAssessmentDetailedActivity.EXTRA_DESCRIPTION, assessment.getDescription());
                intent.putExtra(ViewAssessmentDetailedActivity.EXTRA_ID, Integer.toString(assessment.getId()));
                intent.putExtra(ViewAssessmentDetailedActivity.EXTRA_END_DATE, assessment.getEnd());
                startActivityForResult(intent, VIEW_ASSESSMENT_DETAILED_REQUEST);
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

        if(requestCode == ADD_ASSESSMENT_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(AddEditAssessmentActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditAssessmentActivity.EXTRA_DESCRIPTION); //need non null default value for int
            String start = data.getStringExtra(AddEditAssessmentActivity.EXTRA_START_DATE);
            String end = data.getStringExtra(AddEditAssessmentActivity.EXTRA_END_DATE);


           Assessment assessment = new Assessment(title,description,end);
           assessmentViewModel.insert(assessment);

            Toast.makeText(this, "Assessment Saved", Toast.LENGTH_SHORT).show();
        }
        else if(requestCode == EDIT_ASSESSMENT_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditAssessmentActivity.EXTRA_ID, -1);
            if(id == -1){
                Toast.makeText(this, "Issues Arised", Toast.LENGTH_SHORT).show();
            }
            String title = data.getStringExtra(AddEditAssessmentActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditAssessmentActivity.EXTRA_DESCRIPTION); //need non null default value for int
            String start = data.getStringExtra(AddEditAssessmentActivity.EXTRA_START_DATE);
            String end = data.getStringExtra(AddEditAssessmentActivity.EXTRA_END_DATE);


//            Assessment assessment = new Assessment(title,description,start,end,status,mentor,phone,email,note);
//            assessment.setId(id);
//            assessmentViewModel.update(assessment);

        }
        else{
            Toast.makeText(this, "ERROR SAVING ASSESSMENT", Toast.LENGTH_SHORT).show();
        }
    }
}
