package com.amaze.quit.app;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class SplashScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(5000);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    Intent startMainActivity = new Intent(getApplicationContext(),Home.class);
                    startActivity(startMainActivity);
                    finish();
                }
            }
        };
        timer.start();
    }
}
