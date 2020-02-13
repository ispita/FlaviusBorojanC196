package com.example.flaviusborojanc196;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ViewTermDetailedActivity extends AppCompatActivity {
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
    public static final String EXTRA_TERM_COURSES=
            "com.example.flaviusborojanc196.EXTRA_TERM_COURSES";


//    private TermViewModel termViewModel;
    private TextView viewTextTitle;
    private TextView viewTextDescription;
    private TextView viewTextStartDate;
    private TextView viewTextEndDate;
    private TextView viewTextTermCourses;
    private TextView viewTextTermId;
    private TermViewModel termViewModel;
    public static final int EDIT_TERM_REQUEST = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_term_detailed);

        viewTextTitle = findViewById(R.id.view_title);
        viewTextDescription = findViewById(R.id.view_description);
        viewTextStartDate = findViewById(R.id.view_term_start_date);
        viewTextEndDate = findViewById(R.id.view_term_end_date);
        viewTextTermCourses = findViewById(R.id.view_courses);
        viewTextTermId = findViewById(R.id.view_term_id);
        FloatingActionButton buttonEditTerm = findViewById(R.id.button_edit_term);
        buttonEditTerm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewTermDetailedActivity.this, AddEditTermActivity.class);
                intent.putExtra(AddEditTermActivity.EXTRA_TITLE, viewTextTitle.getText().toString());
                intent.putExtra(AddEditTermActivity.EXTRA_DESCRIPTION, viewTextDescription.getText().toString());
                intent.putExtra(AddEditTermActivity.EXTRA_ID, viewTextTermId.getText().toString());
                Toast.makeText(ViewTermDetailedActivity.this, viewTextTermId.getText().toString(), Toast.LENGTH_SHORT).show();
                intent.putExtra(AddEditTermActivity.EXTRA_START_DATE, viewTextStartDate.getText().toString());
                intent.putExtra(AddEditTermActivity.EXTRA_END_DATE, viewTextEndDate.getText().toString());
                intent.putExtra(AddEditTermActivity.EXTRA_TERM_COURSES, viewTextTermCourses.getText().toString());
                startActivityForResult(intent,EDIT_TERM_REQUEST);
            }
        });


        final TermAdapter adapter = new TermAdapter();

        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        termViewModel.getAllTerms().observe(this,new Observer<List<Term>>(){
            @Override
            public void onChanged(@Nullable List<Term> terms){
                adapter.setTerms(terms);
            }
        });
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Term");
            viewTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            viewTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            viewTextStartDate.setText(intent.getStringExtra(EXTRA_START_DATE));
            viewTextEndDate.setText(intent.getStringExtra(EXTRA_END_DATE));
            viewTextTermCourses.setText(intent.getStringExtra(EXTRA_TERM_COURSES));
            viewTextTermId.setText(intent.getStringExtra(EXTRA_ID));
            Toast.makeText(this, viewTextTermId.getText().toString(), Toast.LENGTH_SHORT).show();

        }
        adapter.setOnItemClickListener(new TermAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Term term) {
                Intent intent = new Intent (ViewTermDetailedActivity.this, AddEditTermActivity.class);
                intent.putExtra(AddEditTermActivity.EXTRA_TITLE, term.getTitle());
                intent.putExtra(AddEditTermActivity.EXTRA_DESCRIPTION, term.getDescription());
                intent.putExtra(AddEditTermActivity.EXTRA_ID, Integer.toString(term.getId()));
                Toast.makeText(ViewTermDetailedActivity.this, "this is detailed view extra_id  " + Integer.toString(term.getId()), Toast.LENGTH_SHORT).show();
                intent.putExtra(AddEditTermActivity.EXTRA_START_DATE, term.getStart());
                intent.putExtra(AddEditTermActivity.EXTRA_END_DATE, term.getEnd());
                intent.putExtra(AddEditTermActivity.EXTRA_TERM_COURSES,term.getTermCourses());
                startActivityForResult(intent, EDIT_TERM_REQUEST);
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


            if(requestCode == EDIT_TERM_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditTermActivity.EXTRA_ID, -1);
            if(id == -1){
                Toast.makeText(this, "Errors Encountered", Toast.LENGTH_SHORT).show();
            }
            String title = data.getStringExtra(AddEditTermActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTermActivity.EXTRA_DESCRIPTION); //need non null default value for int
            String start = data.getStringExtra(AddEditTermActivity.EXTRA_START_DATE);
            String end = data.getStringExtra(AddEditTermActivity.EXTRA_END_DATE);
            String termCourses = data.getStringExtra(AddEditTermActivity.EXTRA_TERM_COURSES);

            Term term = new Term(title,description,start,end,termCourses);
            term.setId(id);
            termViewModel.update(term);

        }

    }


}
