

package projects.mobiinfant.pustak.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aphidmobile.flip.FlipViewController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Logger;

import projects.mobiinfant.pustak.R;
import projects.mobiinfant.pustak.adapter.PustakAdapter;
import projects.mobiinfant.pustak.data.CommonMethods;

public class FlipHorizontalLayoutActivity extends Activity {

  private FlipViewController flipView;
  private TextToSpeech textToSpeech;
  public LinearLayout linearLayoutContent;
  private ImageView imageViewSound;
  private boolean isSoundActive = false;
  private TextView textViewPageNumber,textViewTitle;
  private  PustakAdapter pustakAdapter;
    private ImageView backImg;

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
     // getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
      setTitle("RAMAYANA CG");
    setContentView(R.layout.main_activity);
    linearLayoutContent = (LinearLayout) findViewById(R.id.content_id);
    imageViewSound = (ImageView)findViewById(R.id.sound_id);
    textViewPageNumber =(TextView)findViewById(R.id.page_number_id);
      backImg = (ImageView)findViewById(R.id.back_icon_id);
      textViewTitle =(TextView)findViewById(R.id.page_Title_id);
      textViewTitle.setText(getResources().getString(R.string.aadhya)+":  "+StorySubListActivity.INDEX_EPISODE_SELECTED.getListDesc().get(0).getTitle());
      UpdatePage();
    imageViewSound.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isSoundActive) {
                isSoundActive = false;
            } else {
                isSoundActive = true;
            }
            if (isSoundActive) {
                String editedTextReadable = android.text.Html.fromHtml(StorySubListActivity.INDEX_EPISODE_SELECTED.getListDesc().get(flipView.getSelectedItemPosition()).getDescriptionStr()).toString();
                if(flipView.getSelectedItemPosition() == 0){
                    editedTextReadable = getString(R.string.aadhya)+"  "+StorySubListActivity.INDEX_EPISODE_SELECTED.getListDesc().get(flipView.getSelectedItemPosition()).getTitle()+"   "+editedTextReadable;
                }
                onSpeakingSetting(editedTextReadable);
                imageViewSound.setImageResource(R.drawable.speak_off);

            } else {
                textToSpeech.stop();
                imageViewSound.setImageResource(R.drawable.speak_on);
            }
        }
    });
      backImg.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              onBack();
          }
      });
      textViewPageNumber.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              onShowPagePopup();
          }
      });

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
  private void UpdatePage() {
      flipView = new FlipViewController(FlipHorizontalLayoutActivity.this, FlipViewController.HORIZONTAL);
      pustakAdapter = new PustakAdapter(FlipHorizontalLayoutActivity.this);
      flipView.setAdapter(pustakAdapter);
      linearLayoutContent.addView(flipView);
      speakText();
      setFlipsListner();

      if(CommonMethods.getIndex(this) > 0){
         onGoPage(CommonMethods.getIndex(this));
      }else{
          onGoPage(0);
      }
      CommonMethods.onSetIndex(getApplicationContext(), flipView.getSelectedItemPosition(), StorySubListActivity.INDEX_EPISODE_SELECTED.getIndex());


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
             onGoPage(position);
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

    private void onSpeakingSetting(String stringText){
       // stringText =   stringText.replaceAll("||", "|");

        HashMap<String, String> myHashAlarm = new HashMap();

        final  String[] stringArray = stringText.split("[|]");
        speakinkTotalIndex = stringArray.length-1;
        textToSpeech.setOnUtteranceProgressListener(utteranceProgressListener);
        myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_STREAM,
                String.valueOf(AudioManager.STREAM_ALARM));
        textToSpeech.setSpeechRate(0.70f);
        myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "ID0");
        textToSpeech.speak(stringArray[0], TextToSpeech.QUEUE_FLUSH, myHashAlarm);

        for (int i=1;i<stringArray.length;i++){
            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,"ID"+i);
            textToSpeech.speak(stringArray[i], TextToSpeech.QUEUE_ADD, myHashAlarm);
        }

    }
    private int  speakinkTotalIndex = 0;
    UtteranceProgressListener utteranceProgressListener=new UtteranceProgressListener() {

        @Override
        public void onStart(String utteranceId) {
            Log.d("ppp", "onStart ( utteranceId :"+utteranceId+" ) ");
        }

        @Override
        public void onError(String utteranceId) {
            Log.d("ppp", "onError ( utteranceId :"+utteranceId+" ) ");
        }

        @Override
        public void onDone(String utteranceId) {
            if(("ID"+speakinkTotalIndex).equalsIgnoreCase(utteranceId)){
                final int selectedIndex= flipView.getSelectedItemPosition() + 1;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Sound",""+selectedIndex+"   "+flipView.getCount());
                        if((selectedIndex)<StorySubListActivity.INDEX_EPISODE_SELECTED.getListDesc().size()) {
                           onGoPage(selectedIndex);
                        }else{
                            textToSpeech.stop();
                        }
                    }
                });


            }
        }
    };

    private  void onShowPagePopup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
       // builder.setTitle("Title");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER );
        input.setHint("0-" + (StorySubListActivity.INDEX_EPISODE_SELECTED.getListDesc().size() - 1)+" number Allow!");
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    int selectedIndex = Integer.parseInt(input.getText().toString());
                    onGoPage(selectedIndex);
                }catch (Exception e){}
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CommonMethods.onSetIndex(getApplicationContext(),-1,-1);
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void onBack(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("क्या आप बहार जाना चाहते है ?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    private void onGoPage(int selectedIndex){
        if((selectedIndex) < StorySubListActivity.INDEX_EPISODE_SELECTED.getListDesc().size()) {
            flipView.setSelection(selectedIndex);
            if (textToSpeech.isSpeaking()) {
                textToSpeech.stop();
            }
            if (isSoundActive) {
                String editedTextReadable = android.text.Html.fromHtml(StorySubListActivity.INDEX_EPISODE_SELECTED.getListDesc().get(selectedIndex).getDescriptionStr()).toString();
                onSpeakingSetting(editedTextReadable);
            }
            textViewPageNumber.setText(getResources().getString(R.string.page)+" " + (selectedIndex) + "/" + (StorySubListActivity.INDEX_EPISODE_SELECTED.getListDesc().size()-1));

            CommonMethods.onSetIndex(getApplicationContext(), flipView.getSelectedItemPosition(), StorySubListActivity.INDEX_EPISODE_SELECTED.getIndex());

        }
    }
}
