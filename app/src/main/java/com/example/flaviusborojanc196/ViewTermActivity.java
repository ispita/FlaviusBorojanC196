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

public class ViewTermActivity extends AppCompatActivity {
    public static final int ADD_TERM_REQUEST = 1;
    public static final int EDIT_TERM_REQUEST = 2;
    public static final int VIEW_TERM_DETAILED_REQUEST = 3;


    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_term);


        FloatingActionButton buttonAddTerm = findViewById(R.id.button_add_term);
        buttonAddTerm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewTermActivity.this, AddEditTermActivity.class);
                startActivityForResult(intent,ADD_TERM_REQUEST);
            }
        });




        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TermAdapter adapter = new TermAdapter();
        recyclerView.setAdapter(adapter);

        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        termViewModel.getAllTerms().observe(this,new Observer<List<Term>>(){
            @Override
            public void onChanged(@Nullable List<Term> terms){
                adapter.setTerms(terms);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                termViewModel.delete(adapter.getTermAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new TermAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Term term) {
                Intent intent = new Intent (ViewTermActivity.this, ViewTermDetailedActivity.class);
                intent.putExtra(AddEditTermActivity.EXTRA_TITLE, term.getTitle());
                intent.putExtra(AddEditTermActivity.EXTRA_DESCRIPTION, term.getDescription());
                intent.putExtra(ViewTermDetailedActivity.EXTRA_ID, term.getId());
                intent.putExtra(AddEditTermActivity.EXTRA_START_DATE, term.getStart());
                intent.putExtra(AddEditTermActivity.EXTRA_END_DATE, term.getEnd());
                intent.putExtra(AddEditTermActivity.EXTRA_TERM_COURSES,term.getTermCourses());
                startActivityForResult(intent, VIEW_TERM_DETAILED_REQUEST);
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

        if(requestCode == ADD_TERM_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(AddEditTermActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTermActivity.EXTRA_DESCRIPTION); //need non null default value for int
            String start = data.getStringExtra(AddEditTermActivity.EXTRA_START_DATE);
            String end = data.getStringExtra(AddEditTermActivity.EXTRA_END_DATE);
            String termCourses = data.getStringExtra(AddEditTermActivity.EXTRA_TERM_COURSES);

            Term term = new Term(title,description,start,end,termCourses);
            termViewModel.insert(term);

            Toast.makeText(this, "Term Saved", Toast.LENGTH_SHORT).show();
        }
        else if(requestCode == EDIT_TERM_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditTermActivity.EXTRA_ID, -1);
            if(id == -1){
                Toast.makeText(this, "Issues Arised", Toast.LENGTH_SHORT).show();
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
        else{
            Toast.makeText(this, "ERROR SAVING TERM", Toast.LENGTH_SHORT).show();
        }
    }
}
