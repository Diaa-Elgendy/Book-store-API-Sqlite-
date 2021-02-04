package com.example.bookstore;

import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonIOException;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    SharedPref sharedPref;
    RecyclerView recyclerView, recyclerView2, recyclerView3, recyclerView4;
    BookAdapter adapter, adapter2, adapter3, adapter4;
    Button viewMore, viewMore2, viewMore3, viewMore4;
    List<Book> bookList = new ArrayList<>();
    List<Book> bookList2 = new ArrayList<>();
    List<Book> bookList3 = new ArrayList<>();
    List<Book> bookList4 = new ArrayList<>();
    private static final String sportUrl = "https://run.mocky.io/v3/f393cb45-4081-4e33-a45e-c2966a90f2f8";
    private static final String lifeUrl = "https://run.mocky.io/v3/b5adf54a-9e8a-47eb-a055-7ba3249aae9c";
    private static final String loveUrl = "https://run.mocky.io/v3/a1f58cce-5dc9-44e7-a5a8-1daa0824250e";
    private static final String timeUrl = "https://run.mocky.io/v3/e36cc68e-5e78-4f11-950b-fc6570bc5d03";

    private RequestQueue requestQueue,requestQueue2,requestQueue3,requestQueue4;

    private BookAdapter.RecyclerViewClickListener listener,listener2,listener3,listener4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        if(sharedPref.NightMode()) {
            setTheme(R.style.DarkTheme);
        }
        else  setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setOnClickListener();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView3 = findViewById(R.id.recyclerView3);
        recyclerView4 = findViewById(R.id.recyclerView4);
        viewMore = findViewById(R.id.btn1);
        viewMore2 = findViewById(R.id.btn2);
        viewMore3 = findViewById(R.id.btn3);
        viewMore4 = findViewById(R.id.btn4);

        adapter = new BookAdapter(bookList, getApplicationContext(),listener);
        adapter2 = new BookAdapter(bookList2, getApplicationContext(),listener2);
        adapter3 = new BookAdapter(bookList3, getApplicationContext(),listener3);
        adapter4 = new BookAdapter(bookList4, getApplicationContext(),listener4);
        recyclerView.setAdapter(adapter);
        recyclerView2.setAdapter(adapter2);
        recyclerView3.setAdapter(adapter3);
        recyclerView4.setAdapter(adapter4);
        requestQueue = Volley.newRequestQueue(this);

        Load(sportUrl,bookList,adapter);
        Load(lifeUrl,bookList2,adapter2);
        Load(loveUrl,bookList3,adapter3);
        Load(timeUrl,bookList4,adapter4);

        viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewMoreActivity.class);
                intent.putExtra("Sports","Sports");
                startActivity(intent);
            }
        });
        viewMore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewMoreActivity.class);
                intent.putExtra("Life","Life");
                startActivity(intent);
            }
        });
        viewMore3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewMoreActivity.class);
                intent.putExtra("Love","Love");
                startActivity(intent);
            }
        });
        viewMore4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewMoreActivity.class);
                intent.putExtra("Time","Time");
                startActivity(intent);
            }
        });
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
        listener2 = new BookAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), BookActivity.class);
                intent.putExtra("title",bookList2.get(position).bookTitle);
                intent.putExtra("author",bookList2.get(position).author);
                intent.putExtra("date",bookList2.get(position).date);
                intent.putExtra("category",bookList2.get(position).category);
                intent.putExtra("description",bookList2.get(position).desc);
                intent.putExtra("language",bookList2.get(position).language);
                intent.putExtra("nOfPages",bookList2.get(position).numberOfPages);
                intent.putExtra("photo",bookList2.get(position).imageUrl);
                intent.putExtra("link",bookList2.get(position).bookUrl);
                startActivity(intent);
            }
        };
        listener3 = new BookAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), BookActivity.class);
                intent.putExtra("title",bookList3.get(position).bookTitle);
                intent.putExtra("author",bookList3.get(position).author);
                intent.putExtra("date",bookList3.get(position).date);
                intent.putExtra("category",bookList3.get(position).category);
                intent.putExtra("description",bookList3.get(position).desc);
                intent.putExtra("language",bookList3.get(position).language);
                intent.putExtra("nOfPages",bookList3.get(position).numberOfPages);
                intent.putExtra("photo",bookList3.get(position).imageUrl);
                intent.putExtra("link",bookList3.get(position).bookUrl);
                startActivity(intent);
            }
        };
        listener4 = new BookAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), BookActivity.class);
                intent.putExtra("title",bookList4.get(position).bookTitle);
                intent.putExtra("author",bookList4.get(position).author);
                intent.putExtra("date",bookList4.get(position).date);
                intent.putExtra("category",bookList4.get(position).category);
                intent.putExtra("description",bookList4.get(position).desc);
                intent.putExtra("language",bookList4.get(position).language);
                intent.putExtra("nOfPages",bookList4.get(position).numberOfPages);
                intent.putExtra("photo",bookList4.get(position).imageUrl);
                intent.putExtra("link",bookList4.get(position).bookUrl);
                startActivity(intent);
            }
        };
    }

    public void Load(String apiLink, final List<Book> books, final BookAdapter bookAdapter) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiLink, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray= response.getJSONArray("items");
                    String name, title, preview, date, language, category1, uri, description;
                    String pageCount;
                    for(int i=0;i<6;i++) {
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
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id ==R.id.nav_day){
            sharedPref.setNightModeState(false);
            restartApp();
        }
        else if (id ==R.id.nav_night){
            sharedPref.setNightModeState(true);
            restartApp();
        }
        else if (id ==R.id.nav_fav){
            Intent intent = new Intent(getApplicationContext(),FavouriteActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void restartApp() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        //overridePendingTransition(R.anim.fade_in,R.anim.fade_in);
    }
}
