package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class FavouriteActivity extends AppCompatActivity {

    ArrayList<Book> bookArrayList = new ArrayList<>();
    BookAdapter bookAdapter;
    RecyclerView recyclerView;
    DatabaseOpen databaseOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedPref = new SharedPref(this);
        if(sharedPref.NightMode()) {
            setTheme(R.style.DarkTheme);
        }
        else  setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recyclerView6);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        bookAdapter = new BookAdapter(bookArrayList,getApplicationContext());
        databaseOpen = new DatabaseOpen(this);
        recyclerView.setAdapter(bookAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                // true: consume touch event
                // false: dispatch touch event
                return false;
            }
        });
        show();
    }

    private void show() {
        Cursor cursor = databaseOpen.getSqlData();
        while(cursor.moveToNext()){
            bookArrayList.add(new Book(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8)));
            bookAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}