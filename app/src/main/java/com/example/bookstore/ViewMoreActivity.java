package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewMoreActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BookAdapter adapter;
    ArrayList<Book> bookList = new ArrayList<>();
    private static final String sportUrl = "https://run.mocky.io/v3/f393cb45-4081-4e33-a45e-c2966a90f2f8";
    private static final String lifeUrl = "https://run.mocky.io/v3/b5adf54a-9e8a-47eb-a055-7ba3249aae9c";
    private static final String loveUrl = "https://run.mocky.io/v3/a1f58cce-5dc9-44e7-a5a8-1daa0824250e";
    private static final String timeUrl = "https://run.mocky.io/v3/e36cc68e-5e78-4f11-950b-fc6570bc5d03";
    private RequestQueue requestQueue;
    private BookAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedPref = new SharedPref(this);
        if(sharedPref.NightMode()) {
            setTheme(R.style.DarkTheme);
        }
        else  setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_more);


        Toolbar toolbar=findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setOnClickListener();

        recyclerView = findViewById(R.id.recyclerView5);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new BookAdapter(bookList, getApplicationContext(),listener);
        recyclerView.setAdapter(adapter);
        requestQueue = Volley.newRequestQueue(this);

        Intent get = getIntent();

        if (String.valueOf(get.getStringExtra("Sports")).equals("Sports")){
            Load(sportUrl,bookList,adapter);
            toolbar.setTitle(get.getStringExtra("Sports"));
        }if (String.valueOf(get.getStringExtra("Love")).equals("Love")){
            Load(loveUrl,bookList,adapter);
            toolbar.setTitle(get.getStringExtra("Love"));
        }if (String.valueOf(get.getStringExtra("Time")).equals("Time")){
            Load(loveUrl,bookList,adapter);
            toolbar.setTitle(get.getStringExtra("Time"));
        }if (String.valueOf(get.getStringExtra("Life")).equals("Life")){
            Load(lifeUrl,bookList,adapter);
            toolbar.setTitle(get.getStringExtra("Life"));
        }

    }

    public void Load(String apiLink, final List<Book> books, final BookAdapter bookAdapter) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiLink, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray= response.getJSONArray("items");
                    String name, title, preview, date, language, category1, uri, description;
                    String pageCount;
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        JSONObject volume = item.getJSONObject("volumeInfo");
                        JSONObject img = volume.getJSONObject("imageLinks");
                        try {
                            title = volume.getString("title");
                        } catch (JSONException jsonIOException) {
                            title = "No Title";
                        }
                        try {
                            JSONArray authors = volume.getJSONArray("authors");
                            name = authors.getString(0);
                        } catch (JSONException jsonIOException) {
                            name = "Unknown author";
                        }
                        try {
                            date = volume.getString("publishedDate");
                        } catch (JSONException jsonException) {
                            date = "Unknown date";
                        }
                        try {
                            JSONArray category = volume.getJSONArray("categories");
                            category1 = category.getString(0);
                        } catch (JSONException jsonException) {
                            category1 = "Unknown category";
                        }
                        try {
                            description = volume.getString("description");
                        } catch (JSONException jsonIOException) {
                            description = "No description Available";
                        }
                        try {
                            language = volume.getString("language");
                        } catch (JSONException jsonIOException) {
                            language = "Unknown language";
                        }
                        try {
                            pageCount = volume.getString("pageCount");
                        } catch (JSONException e) {
                            pageCount = "0";
                        }
                        try {
                            uri = img.getString("thumbnail");
                        } catch (JSONException jsonIOException) {
                            uri = "D:/Projects/AndriodStudioProject/BookStore/app/src/main/res/drawable/no_image.png";
                        }
                        try {
                            preview = volume.getString("previewLink");
                        } catch (JSONException jsonIOException) {
                            preview = "https://www.google.com/";
                        }
                        books.add(new Book(title, name, date, category1, description, language, pageCount, uri, preview));
                        bookAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    private void setOnClickListener() {
        listener = new BookAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), BookActivity.class);
                intent.putExtra("title",bookList.get(position).bookTitle);
                intent.putExtra("author",bookList.get(position).author);
                intent.putExtra("date",bookList.get(position).date);
                intent.putExtra("category",bookList.get(position).category);
                intent.putExtra("description",bookList.get(position).desc);
                intent.putExtra("language",bookList.get(position).language);
                intent.putExtra("nOfPages",bookList.get(position).numberOfPages);
                intent.putExtra("photo",bookList.get(position).imageUrl);
                intent.putExtra("link",bookList.get(position).bookUrl);
                startActivity(intent);
            }
        };
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
