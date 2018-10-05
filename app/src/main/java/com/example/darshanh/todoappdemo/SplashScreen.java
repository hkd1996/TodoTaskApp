package com.example.darshanh.todoappdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
      RotateAnimation rotate = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(3000);
        rotate.setRepeatCount(0);
        rotate.setInterpolator(new LinearInterpolator());

        ImageView image= (ImageView) findViewById(R.id.appLogo);

        image.startAnimation(rotate);
        Animation fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.blink);
        TextView appName=(TextView)findViewById(R.id.appName);
        appName.startAnimation(fadeAnimation);

        Thread timer = new Thread(){

            @Override
            public void run() {

                try {
                    sleep(4000);
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                    super.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        timer.start();
    }
}
