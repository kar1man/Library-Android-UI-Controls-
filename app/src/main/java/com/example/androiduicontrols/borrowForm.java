package com.example.androiduicontrols;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class borrowForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button datePickerBtn, timePickerBtn;
    private TextView dateText, timeText;
    private int year, month, day, hour, minute;

    private Spinner yearLvl;

    private TextView bookTitle, bookAuthor, bookGenre;
    private ImageView bookImage;

    private String title, author, genre;
    private int imageResId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_borrow_form);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Start
        retrieveBookData();
        dateAndTime();

    }

    private void dateAndTime() {
        datePickerBtn = findViewById(R.id.datePicker);
        timePickerBtn = findViewById(R.id.timePicker);
        dateText = findViewById(R.id.dateTxt);
        timeText = findViewById(R.id.timeTxt);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(borrowForm.this,
                        (view1, selectedYear, selectedMonth, selectedDay) -> {
                            dateText.setText("Selected Date: " + selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                            year = selectedYear;
                            month = selectedMonth;
                            day = selectedDay;
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        timePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(borrowForm.this,
                        (view1, selectedHour, selectedMinute) -> {
                            // Display selected time
                            timeText.setText("Selected Time: " + String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
                            hour = selectedHour;
                            minute = selectedMinute;
                        }, hour, minute, true);
                timePickerDialog.show();
            }
        });

    }

    private void displayDetails() {
        bookTitle = findViewById(R.id.titleView);
        bookAuthor = findViewById(R.id.authorView);
        bookGenre = findViewById(R.id.genreView);
        bookImage = findViewById(R.id.bookPicture);

        yearLvl = findViewById(R.id.yearLevel);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.yrLvl, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearLvl.setAdapter(adapter);

        yearLvl.setOnItemSelectedListener(this);

        bookTitle.setText("Title: \n" +  title);
        bookAuthor.setText("Author: \n" +  author);
        bookGenre.setText("Genre: \n" +  genre);
        bookImage.setImageResource(imageResId);
    }

    private void retrieveBookData() {
        Intent intent = getIntent();
        title = intent.getStringExtra("bookTitle");
        author = intent.getStringExtra("bookAuthor");
        genre = intent.getStringExtra("bookGenre");
        imageResId = intent.getIntExtra("bookResId", -1);

        displayDetails();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String choice = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}