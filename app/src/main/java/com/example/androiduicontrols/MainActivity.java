package com.example.androiduicontrols;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Book selectedBook;
    View viewbg;
    TextView bookTitle, bookAuthor, bookGenre;
    AutoCompleteTextView searchAutoComplete;
    ImageButton bookButton, quitButton;
    Button borrowButton;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        searchButton();
        quitt();
        layoutView(); // for custom view or other layouts
    }

    private void quitt() {
        quitButton = findViewById(R.id.quitBtn);
        builder = new AlertDialog.Builder(MainActivity.this);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Exit App")
                        .setMessage("Are you sure you want to close the application?")
                        .setCancelable(true)
                        .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finishAffinity();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
        });

    }

    private void searchButton() {
        searchAutoComplete = findViewById(R.id.search_auto);
        bookButton = findViewById(R.id.seachedBook);
        bookTitle = findViewById(R.id.title);
        bookAuthor = findViewById(R.id.author);
        bookGenre = findViewById(R.id.genre);
        borrowButton = findViewById(R.id.borrowBtn);
        builder = new AlertDialog.Builder(MainActivity.this);

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Notice!")
                        .setMessage("Are you sure you want to borrow this book?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (selectedBook != null) {
                                    Intent borrowIntent = new Intent(MainActivity.this, borrowForm.class);
                                    borrowIntent.putExtra("bookTitle", selectedBook.getTitle());
                                    borrowIntent.putExtra("bookAuthor", selectedBook.getAuthor());
                                    borrowIntent.putExtra("bookGenre", selectedBook.getGenre());
                                    borrowIntent.putExtra("bookResId", selectedBook.getBookResId());
                                    startActivity(borrowIntent);
                                } else {
                                    showAlertDialog("Please select a book first.");
                                }

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        });

        borrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Notice!")
                        .setMessage("Are you sure you want to borrow this book?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (selectedBook != null) {
                                    Intent borrowIntent = new Intent(MainActivity.this, borrowForm.class);
                                    borrowIntent.putExtra("bookTitle", selectedBook.getTitle());
                                    borrowIntent.putExtra("bookAuthor", selectedBook.getAuthor());
                                    borrowIntent.putExtra("bookGenre", selectedBook.getGenre());
                                    borrowIntent.putExtra("bookResId", selectedBook.getBookResId());
                                    startActivity(borrowIntent);
                                } else {
                                    showAlertDialog("Please select a book first.");
                                }

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        });

        List<Book> bookCollections = new ArrayList<>();

        bookCollections.add(new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", R.drawable.mockingbird));
        bookCollections.add(new Book("Sun Tzu's Art of War", "Sun Tzu", "Philosophy", R.drawable.art_of_war));
        bookCollections.add(new Book("Ikigai: The Japanese Secret to a Long and Happy Life", "Héctor García, Francesc Miralles", "Self-help", R.drawable.ikigai));
        bookCollections.add(new Book("A Rose for Emily", "William Faulkner", "Southern Gothic", R.drawable.rose_for_emily));
        bookCollections.add(new Book("The Picture of Dorian Gray", "Oscar Wilde", "Fiction", R.drawable.dorian));
        bookCollections.add(new Book("The Cask of Amontillado", "Edgar Allan Poe", "Fiction", R.drawable.cask1));
        bookCollections.add(new Book("The Odyssey", "Homer", "Epic Poetry", R.drawable.odysey));
        bookCollections.add(new Book("The Subtle Art of Not Giving a F*ck", "Mark Manson", "Self-help", R.drawable.subtle));
        bookCollections.add(new Book("1984", "George Orwell", "Dystopian Fiction", R.drawable.nine_eight));;
        bookCollections.add(new Book("Moby-Dick", "Herman Melville", "Adventure", R.drawable.moby));

        ArrayAdapter<Book> bookAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, bookCollections);
        searchAutoComplete.setAdapter(bookAdapter);
        searchAutoComplete.setThreshold(1);

        searchAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
            selectedBook = (Book) parent.getItemAtPosition(position);
            bookButton.setImageResource(selectedBook.getBookResId());
            bookButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
            bookTitle.setText(selectedBook.getTitle());
            bookAuthor.setText(selectedBook.getAuthor());
            bookGenre.setText(selectedBook.getGenre());
        });
    }

    private void showAlertDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Action Required")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void layoutView() {
        viewbg = findViewById(R.id.view1);
        viewbg.setAlpha(0.5f);
    }
}

// OOP para associated yung book title sa author
class Book {
    private String title;
    private String author;
    private String genre;
    private int bookResId;

    public Book(String title, String author, String genre, int bookResId) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.bookResId = bookResId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getBookResId(){
        return bookResId;
    }

    @Override
    public String toString() {
        return title;
    }
}