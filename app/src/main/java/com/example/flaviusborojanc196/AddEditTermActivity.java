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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddEditTermActivity extends AppCompatActivity {
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


    private EditText editTextTitle;
    private EditText editTextDescription;
    private DatePicker editTextStartDate;
    private DatePicker editTextEndDate;
    private String editTermId;
    private Boolean editTerm = false;
    private TextView addedCourses;
    private int startSep;
    private int startSep2;
    private int endSep;
    private int endSep2;
    private int startYear;
    private int  startMonth;
    private int startDay;
    private int endYear;
    private int endMonth;
    private int endDay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        editTextTitle = findViewById(R.id.edit_title);
        editTextDescription = findViewById(R.id.edit_description);
        editTextStartDate = findViewById(R.id.edit_term_start_date);
        editTextEndDate = findViewById(R.id.edit_term_end_date);
        addedCourses = findViewById(R.id.courses_added);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Term");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            startSep = intent.getStringExtra(EXTRA_START_DATE).indexOf("/");
            startSep2 = intent.getStringExtra(EXTRA_START_DATE).indexOf("/", intent.getStringExtra(EXTRA_START_DATE).indexOf("/") + 1);
            startMonth = Integer.parseInt(intent.getStringExtra(EXTRA_START_DATE).substring(0, startSep));
            startDay = Integer.parseInt(intent.getStringExtra(EXTRA_START_DATE).substring(startSep + 1, startSep2));
            startYear = Integer.parseInt(intent.getStringExtra(EXTRA_START_DATE).substring(startSep2 + 1, startSep2 + 5));
            endSep = intent.getStringExtra(EXTRA_END_DATE).indexOf("/");
            endSep2 = intent.getStringExtra(EXTRA_END_DATE).indexOf("/", intent.getStringExtra(EXTRA_END_DATE).indexOf("/") + 1);
            endMonth = Integer.parseInt(intent.getStringExtra(EXTRA_END_DATE).substring(0, endSep));
            endDay = Integer.parseInt(intent.getStringExtra(EXTRA_END_DATE).substring(endSep + 1, endSep2));
            endYear = Integer.parseInt(intent.getStringExtra(EXTRA_END_DATE).substring(endSep2 + 1, endSep2 + 5));
            editTextStartDate.updateDate(startYear,startMonth - 1,startDay);
            editTextEndDate.updateDate(endYear,endMonth - 1,endDay);
            editTermId = intent.getStringExtra(EXTRA_ID);
            editTerm = true;


        }
        else {
            setTitle("Add Term");
        }

    }
    private void saveTerm() throws ParseException {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int startMonth = editTextStartDate.getMonth() + 1;
        int endMonth = editTextEndDate.getMonth() + 1;
        String start = startMonth + "/" + editTextStartDate.getDayOfMonth() + "/" + editTextStartDate.getYear();
        String end = endMonth + "/" + editTextEndDate.getDayOfMonth() + "/" + editTextEndDate.getYear();
        if(title.trim().isEmpty() || description.trim().isEmpty() || start.trim().isEmpty() || end.trim().isEmpty()){
            Toast.makeText(this, "Please Fill out all of the fields!", Toast.LENGTH_SHORT).show();
            return;
        }
        SimpleDateFormat format = new SimpleDateFormat("MM/d/yyyy");
        if (format.parse(end).before(format.parse(start)) || end.equals(start)) {
            Toast.makeText(this, "End date must be AFTER the start date!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_START_DATE, start);
        data.putExtra(EXTRA_END_DATE, end);
        int id = -1;
        if (!editTerm) {
             id = getIntent().getIntExtra(EXTRA_ID, -1);
        }
        else if (editTerm) {
            id = Integer.parseInt(editTermId);
        }
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
                try {
                    saveTerm();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
