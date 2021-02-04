package com.example.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private ImageView mv;
    SharedPref sharedpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);
        if(sharedpref.NightMode()) {
            setTheme(R.style.DarkTheme);
        } else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        mv = findViewById(R.id.splashLogo);
        if(sharedpref.NightMode()) {
            mv.setImageResource(R.drawable.logo);

        }
        else
            mv.setImageResource(R.drawable.logo);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.splash_transition);
        mv.startAnimation(animation);
        final Intent intent= new Intent(getApplicationContext(),MainActivity.class);
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(1500);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}
