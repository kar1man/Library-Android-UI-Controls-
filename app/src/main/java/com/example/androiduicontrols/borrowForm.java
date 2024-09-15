package com.example.androiduicontrols;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class borrowForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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