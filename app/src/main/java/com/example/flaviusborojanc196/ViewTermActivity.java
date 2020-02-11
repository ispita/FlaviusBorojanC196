package com.example.flaviusborojanc196;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ViewTermActivity extends AppCompatActivity {
    public static final int ADD_TERM_REQUEST = 1;


    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_term);


        FloatingActionButton buttonAddTerm = findViewById(R.id.button_add_term);
        buttonAddTerm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewTermActivity.this,AddTermActivity.class);
                startActivityForResult(intent,ADD_TERM_REQUEST);
            }
        });

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.term_item_layout);

        rl.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {}
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
            String title = data.getStringExtra(AddTermActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddTermActivity.EXTRA_DESCRIPTION); //need non null default value for int

            Term term = new Term(title,description);
            termViewModel.insert(term);

            Toast.makeText(this, "Term Saved", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "ERROR SAVING TERM", Toast.LENGTH_SHORT).show();
        }
    }
}
