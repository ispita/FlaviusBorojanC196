package com.example.flaviusborojanc196;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ViewNoteDetailedActivity extends AppCompatActivity {
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



    private TextView viewTextTitle;
    private TextView viewTextDescription;
    private TextView viewTextNoteId;
    private RecyclerView viewTextNoteCourses;
    private NoteViewModel noteViewModel;
    private CourseViewModel courseViewMode1;
    private List<Course> coursesList;

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    public static final int VIEW_NOTE_DETAILED_REQUEST = 3;
    public static final int ADD_COURSE_REQUEST = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note_detailed);

        viewTextTitle = findViewById(R.id.view_title);
        viewTextDescription = findViewById(R.id.view_description);
        viewTextNoteCourses = findViewById(R.id.view_courses);
        viewTextNoteId = findViewById(R.id.view_note_id);


        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Note Detailed View");
            viewTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            viewTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            //   viewTextNoteCourses.setText(intent.getStringExtra(EXTRA_NOTE_COURSES));
            viewTextNoteId.setText(intent.getStringExtra(EXTRA_ID));
            Toast.makeText(this, viewTextNoteId.getText().toString(), Toast.LENGTH_SHORT).show();

        }

        FloatingActionButton buttonEditNote = findViewById(R.id.button_edit_note);
        buttonEditNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewNoteDetailedActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, viewTextTitle.getText().toString());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, viewTextDescription.getText().toString());
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, viewTextNoteId.getText().toString());
                Toast.makeText(ViewNoteDetailedActivity.this, viewTextNoteId.getText().toString(), Toast.LENGTH_SHORT).show();
                startActivityForResult(intent,EDIT_NOTE_REQUEST);
            }
        });



        final NoteAdapter adapter = new NoteAdapter();

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this,new Observer<List<Note>>(){
            @Override
            public void onChanged(@Nullable List<Note> notes){
                adapter.setNotes(notes);
            }
        });

        FloatingActionButton buttonDeleteNote = findViewById(R.id.button_delete_note);
        buttonDeleteNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                for (int i = 0; i < adapter.getItemCount(); i++) {
                    Intent intent = new Intent(ViewNoteDetailedActivity.this, ViewCourseDetailedActivity.class);
                    if (Integer.parseInt(viewTextNoteId.getText().toString()) == adapter.getNoteAt(i).getId()) {
                        noteViewModel.delete(adapter.getNoteAt(i));
                        startActivity(intent);
                    }

                }

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


            if(requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
                Toast.makeText(this, "Entering into RESULT_OK", Toast.LENGTH_SHORT).show();
            int id = data.getIntExtra(ViewCourseDetailedActivity.EXTRA_ID, -1);
            if(id == -1){
                Toast.makeText(this, "Errors Encountered", Toast.LENGTH_SHORT).show();
            }
            String title = data.getStringExtra(ViewCourseDetailedActivity.EXTRA_TITLE);
            String description = data.getStringExtra(ViewCourseDetailedActivity.EXTRA_DESCRIPTION); //need non null default value for int
            String start = data.getStringExtra(ViewCourseDetailedActivity.EXTRA_START_DATE);
            String end = data.getStringExtra(ViewCourseDetailedActivity.EXTRA_END_DATE);
                viewTextTitle.setText(title);
                viewTextDescription.setText(description);
                viewTextNoteId.setText(Integer.toString(id));
            Note note = new Note(AssessmentRepository.courseId,title,description);
            note.setId(id);
            noteViewModel.update(note);
        }


    }


}
