

package projects.mobiinfant.pustak.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aphidmobile.flip.FlipViewController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import projects.mobiinfant.pustak.R;
import projects.mobiinfant.pustak.adapter.PustakAdapter;
import projects.mobiinfant.pustak.data.CommonMethods;

public class FlipHorizontalLayoutActivity extends Activity {

  private FlipViewController flipView;
  private TextToSpeech textToSpeech;
  public LinearLayout linearLayoutContent;
  private ImageView imageViewSound;
  private int postionIndex = 0;
  private boolean isSoundActive = false;
  private TextView textViewPageNumber;
  private Spinner episode_spinner;
  private  PustakAdapter pustakAdapter;

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setTitle("PUSTAK");
    setContentView(R.layout.main_activity);
    linearLayoutContent = (LinearLayout) findViewById(R.id.content_id);
    imageViewSound = (ImageView)findViewById(R.id.sound_id);
    textViewPageNumber =(TextView)findViewById(R.id.page_number_id);
    episode_spinner = (Spinner)findViewById(R.id.episode_spinner_id);
    episode_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
       flipView.setSelection(CommonMethods.INDEX_EPISODE.get(position).getIndexPostion());
      }

      @Override
      public void onNothingSelected(AdapterView<?> parentView) {
        // your code here
      }

    });
    new UpdatePage().execute("");
    imageViewSound.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (isSoundActive) {
          isSoundActive = false;
        } else {
          isSoundActive = true;
        }
        if (isSoundActive) {
          String editedTextReadable = android.text.Html.fromHtml(CommonMethods.IMG_DESCRIPTIONS.get(postionIndex).getDescriptionStr()).toString();
          textToSpeech.speak(editedTextReadable, TextToSpeech.QUEUE_FLUSH, null);
        } else {
          textToSpeech.stop();
        }
      }
    });

  }

  private void setSpinner(){
    List<String> categories = new ArrayList<String>();
    for (int i=0;i< CommonMethods.INDEX_EPISODE.size(); i++){
      categories.add(CommonMethods.INDEX_EPISODE.get(i).getTitle());
    }
    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    episode_spinner.setAdapter(dataAdapter);



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
      pustakAdapter = new PustakAdapter(FlipHorizontalLayoutActivity.this);
      flipView.setAdapter(pustakAdapter);
      linearLayoutContent.addView(flipView);
      textViewPageNumber.setText("Page:" + (postionIndex + 1) + "/" + CommonMethods.IMG_DESCRIPTIONS.size());
      speakText();
      setFlipsListner();
      setSpinner();
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
        if(textToSpeech.isSpeaking()){
          textToSpeech.stop();
        }
        if(isSoundActive) {
          String editedTextReadable = android.text.Html.fromHtml(CommonMethods.IMG_DESCRIPTIONS.get(position).getDescriptionStr()).toString();
          textToSpeech.speak(editedTextReadable, TextToSpeech.QUEUE_FLUSH, null);
        }
        postionIndex = position;
        textViewPageNumber.setText("Page:"+(postionIndex+1)+"/"+CommonMethods.IMG_DESCRIPTIONS.size());
      }
    });
  }
  private void speakText(){
    textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
      @Override
      public void onInit(int status) {
        if(status != TextToSpeech.ERROR) {
         int result = textToSpeech.setLanguage(new Locale("hin-IND"));
          if (result == TextToSpeech.LANG_MISSING_DATA
                  || result == TextToSpeech.LANG_NOT_SUPPORTED ) {
            Toast.makeText(FlipHorizontalLayoutActivity.this,"Hindi language is not updated, please update hindi language.",Toast.LENGTH_LONG).show();
            Intent installTTSIntent = new Intent();
            installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            ArrayList<String> languages = new ArrayList<String>();
            languages.add("hin-IND"); // hin - hindi, IND - INDIA
            installTTSIntent.putStringArrayListExtra(
                    TextToSpeech.Engine.EXTRA_CHECK_VOICE_DATA_FOR, languages);
            startActivity(installTTSIntent);
            Log.e("TTS", "This Language is not supported");
          }
        }

      }
    });

  }
  @Override
  public void onDestroy() {
    // Don't forget to shutdown tts!
    if (textToSpeech != null) {
      textToSpeech.stop();
      textToSpeech.shutdown();
    }
    super.onDestroy();
  }

}
