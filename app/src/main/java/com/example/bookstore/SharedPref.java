package com.example.bookstore;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.ImageView;

public class SharedPref {
    private SharedPreferences mySharedPref ;
    public SharedPref(Context context) {
        mySharedPref = context.getSharedPreferences("filename", Context.MODE_PRIVATE);
    }

    // this method will save the nightMode State : True or False
    public void setNightModeState(Boolean state) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putBoolean("NightMode",state);
        editor.apply();
    }
    // this method will load the Night Mode State
    public Boolean NightMode (){
        Boolean state = mySharedPref.getBoolean("NightMode",false);
        return  state;
    }


}