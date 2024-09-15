package com.example.androiduicontrols;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class borrowForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button datePickerBtn, timePickerBtn, confirm;
    private TextView dateText, timeText;
    private int year, month, day, hour, minute;
    private EditText fullname, section;

    private Spinner yearLvl;

    private TextView bookTitle, bookAuthor, bookGenre;
    private ImageView bookImage;

    private String title, author, genre;
    private int imageResId;
    ProgressBar pb;
    int counter = 0;



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

        finalConfirmation();

        fullname = findViewById(R.id.firstName);
        section = findViewById(R.id.section);

        pb = findViewById(R.id.progress);
        pb.setVisibility(View.GONE);

    }

    private void finalConfirmation() {
        confirm = findViewById(R.id.confirmBtn);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prog(100); // Start progress
            }
        });
    }

    private void prog(int targetProgress) {

        pb.setVisibility(View.VISIBLE);
        // Ensure the counter starts from 0
        counter = 0;
        pb.setProgress(counter); // Initialize progress bar to the starting point.

        final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        counter++;
                        pb.setProgress(counter);

                        // Stop the Timer when the counter reaches the target progress value.
                        if (counter >= targetProgress) {
                            t.cancel();
                            pb.setVisibility(View.GONE); // Hide the progress bar after completion
                            showUserDetailsOverlay(); // Show the user details overlay
                        }
                    }
                });
            }
        };

        // Schedule the TimerTask with a fixed delay (e.g., 100ms)
        t.schedule(tt, 0, 100);
    }

    private void showUserDetailsOverlay() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Create a custom dialog for showing user details
                AlertDialog.Builder builder = new AlertDialog.Builder(borrowForm.this);
                LayoutInflater inflater = borrowForm.this.getLayoutInflater();

                // Inflate the custom overlay layout
                View dialogView = inflater.inflate(R.layout.overlay_user_details, null);
                builder.setView(dialogView);

                // Initialize TextViews from the overlay layout
                TextView fullnameText = dialogView.findViewById(R.id.fullnameText);
                TextView sectionText = dialogView.findViewById(R.id.sectionText);
                TextView titleText = dialogView.findViewById(R.id.titleText);
                TextView authorText = dialogView.findViewById(R.id.authorText);
                TextView genreText = dialogView.findViewById(R.id.genreText);
                TextView dateText = dialogView.findViewById(R.id.dateText);
                TextView timeText = dialogView.findViewById(R.id.timeText);
                TextView yearLevelText = dialogView.findViewById(R.id.yearLevelText);

                // Set the actual details retrieved from the user's input
                titleText.setText("Title: " + title);
                authorText.setText("Author: " + author);
                genreText.setText("Genre: " + genre);

                // Retrieve and display selected date, time, and year level
                dateText.setText("Date: " + borrowForm.this.dateText.getText().toString());
                timeText.setText("Time: " + borrowForm.this.timeText.getText().toString());
                fullnameText.setText("Fullname: " + fullname.getText().toString());
                sectionText.setText("Section: " + section.getText().toString());
                yearLevelText.setText("Year Level: " + yearLvl.getSelectedItem().toString());

                Button okButton = dialogView.findViewById(R.id.okBtn);

                // Create and show the dialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

            }
        });
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