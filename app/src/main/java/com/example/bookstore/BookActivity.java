package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class BookActivity extends AppCompatActivity {
    int add =0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedPref = new SharedPref(this);
        if(sharedPref.NightMode()) {
            setTheme(R.style.DarkTheme);
        }
        else  setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Toolbar toolbar = findViewById(R.id.toolbar_book);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView tvtitle = findViewById(R.id.TvBookName2);
        TextView tvauthor = findViewById(R.id.TvBookAuthor);
        TextView tvdesc = findViewById(R.id.tvBookDesc2);
        TextView tvdate = findViewById(R.id.TvBookDate);
        TextView tvcategory = findViewById(R.id.tvBookCategory);
        TextView tvreviews = findViewById(R.id.tvBookReview);
        TextView tvpages = findViewById(R.id.tvBookPages);
        ImageView ivphoto = findViewById(R.id.imageView2);
        Button btnlink = findViewById(R.id.btnRead);
        final Button btnfav = findViewById(R.id.btnFav);

        Intent get = getIntent();
        final String title =get.getStringExtra("title");
        final String author = get.getStringExtra("author");
        final String desc = get.getStringExtra("description");
        final String date = get.getStringExtra("date");
        final String category = get.getStringExtra("category");
        final String language = get.getStringExtra("language");
        final String pages = get.getStringExtra("nOfPages");
        final String photo = get.getStringExtra("photo");
        final String link = get.getStringExtra("link");

        tvtitle.setText(title);
        tvauthor.setText("By: " + author);
        tvdesc.setText(desc);
        tvdate.setText(date);
        tvcategory.setText(category);
        tvreviews.setText("Language: " + language);
        tvpages.setText(pages + " Page");
        Picasso.get().load(photo).placeholder(R.drawable.no_image).fit().centerInside().into(ivphoto);

        btnlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(link);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        //To Get the last choice (the book add to favourite or not)
        final SharedPreferences visits = getSharedPreferences("number", Context.MODE_PRIVATE);
        add = visits.getInt("add", 0);
        if (add == 0) {
            btnfav.setText("Add to favourite");
        }
        if (add == 1){
            btnfav.setText("Remove from favourite");
        }

        btnfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseOpen databaseOpen = new DatabaseOpen(getApplicationContext());
                final SharedPreferences.Editor editor = visits.edit();

                if (add == 0){
                    databaseOpen.insert(title,author,date,category,desc,language,pages,photo,link);
                    btnfav.setText("Remove to favourite");
                    add++;
                } else if (add == 1){
                    databaseOpen.delete(title);
                    btnfav.setText("Add favourite");
                    add--;
                }
                editor.putInt("add", add);
                editor.apply();
            }
        });


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
