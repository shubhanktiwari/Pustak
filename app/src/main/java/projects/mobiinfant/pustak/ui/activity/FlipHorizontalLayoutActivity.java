

package projects.mobiinfant.pustak.ui.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.aphidmobile.flip.FlipViewController;

import java.util.Locale;

import projects.mobiinfant.pustak.R;
import projects.mobiinfant.pustak.adapter.PustakAdapter;
import projects.mobiinfant.pustak.data.CommonMethods;

public class FlipHorizontalLayoutActivity extends Activity {

  private FlipViewController flipView;
  private TextToSpeech textToSpeech;
  public LinearLayout linearLayoutContent;

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setTitle("PUSTAK");
    setContentView(R.layout.main_activity);
    linearLayoutContent = (LinearLayout) findViewById(R.id.content_id);
    new UpdatePage().execute("");

  }

  @Override
  protected void onResume() {
    super.onResume();
    if(flipView !=null)
    flipView.onResume();
    hideNavBar();


  }

  @Override
  protected void onPause() {
    super.onPause();
    if(flipView !=null)
    flipView.onPause();
  }
  private class UpdatePage extends AsyncTask<String, String, String> {

    protected String doInBackground(String... urls) {
      CommonMethods.setData(FlipHorizontalLayoutActivity.this);
      return "";
    }
    protected void onPostExecute(String result) {
      flipView = new FlipViewController(FlipHorizontalLayoutActivity.this, FlipViewController.HORIZONTAL);

      flipView.setAdapter(new PustakAdapter(FlipHorizontalLayoutActivity.this));
      linearLayoutContent.addView(flipView);
     // FlipHorizontalLayoutActivity.this.setContentView(flipView);
      speakText();
      setFlipsListner();
    }
  }
  private void hideNavBar() {
    if (Build.VERSION.SDK_INT >= 19) {
      View v = getWindow().getDecorView();
      v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
              | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
              | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
              | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
              | View.SYSTEM_UI_FLAG_FULLSCREEN
              | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
  }
  private void setFlipsListner(){
    flipView.setOnViewFlipListener(new FlipViewController.ViewFlipListener() {
      @Override
      public void onViewFlipped(View view, int position) {
        textToSpeech.speak(CommonMethods.IMG_DESCRIPTIONS.get(position).getDescriptionStr(), TextToSpeech.QUEUE_FLUSH, null);
      }
    });
  }
  private void speakText(){
    textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
      @Override
      public void onInit(int status) {
        if(status != TextToSpeech.ERROR) {
          textToSpeech.setLanguage(new Locale("hin-IND"));
        }
      }
    });
  }

}
