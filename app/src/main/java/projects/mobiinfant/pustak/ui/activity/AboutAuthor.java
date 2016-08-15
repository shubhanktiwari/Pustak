package projects.mobiinfant.pustak.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import projects.mobiinfant.pustak.R;
import projects.mobiinfant.pustak.data.CommonMethods;

public class AboutAuthor extends AppCompatActivity {
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_author);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        Button btnStart = (Button)findViewById(R.id.startbook);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(AboutAuthor.this, StorySubListActivity.class);
                    startActivity(intent);



            }
        });
    }

    private void showDialogBar(){
        progress = new ProgressDialog(this);
        progress.setMessage("Please wait. App configuration in progress.");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }

}
