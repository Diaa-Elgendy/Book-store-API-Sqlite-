package com.example.bookstore;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseOpen extends SQLiteOpenHelper {

    static final String BDName = "database.db";

    public DatabaseOpen(Context context) {
        super(context, BDName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE myFav (title TEXT PRIMARY KEY, author TEXT, date String, category TEXT, description TEXT, language TEXT, nOfPages TEXT, image TEXT, link TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS myFav");
    }

    public boolean insert(String bookTitle, String author, String date, String category, String desc, String language, String numberOfPages, String imageUrl, String bookUrl){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",bookTitle );
        contentValues.put("author", author);
        contentValues.put("date",date );
        contentValues.put("category", category);
        contentValues.put("description", desc);
        contentValues.put("language", language);
        contentValues.put("nOfPages",numberOfPages );
        contentValues.put("image", imageUrl);
        contentValues.put("link", bookUrl);
        int check = (int) sqLiteDatabase.insert("myFav", "No Data.",contentValues);
        if (check == -1){
            return false;
        }
        else
            return true;
    }

    public Cursor getSqlData(){
        ArrayList<Book> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM myFav",null);

        return result;
    }

    public int delete(String title){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete("myFav","title = ?",new String[]{title});
    }
}
