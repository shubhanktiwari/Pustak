package projects.mobiinfant.pustak.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import projects.mobiinfant.pustak.R;
import projects.mobiinfant.pustak.data.CommonMethods;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        CommonMethods.getScreenInches(this);
        setContentView(R.layout.activity_splash_screen);
            {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Thread t = new Thread(new Runnable() {
                            public void run() {
                                onLaunchHomeActivity();
                            }
                        });
                        t.start();
                    }
                }, 5000);
            }
    }
    private void onLaunchHomeActivity(){

          Intent intent = new Intent(this, AboutAuthor.class);
          startActivity(intent);
          finish();
    }

}
