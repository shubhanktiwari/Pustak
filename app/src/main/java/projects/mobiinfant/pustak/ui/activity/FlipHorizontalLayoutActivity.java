

package projects.mobiinfant.pustak.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.aphidmobile.flip.FlipViewController;

import projects.mobiinfant.pustak.adapter.PustakAdapter;

public class FlipHorizontalLayoutActivity extends Activity {

  private FlipViewController flipView;

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setTitle("PUSTAK");

    flipView = new FlipViewController(this, FlipViewController.HORIZONTAL);

    flipView.setAdapter(new PustakAdapter(this));

    setContentView(flipView);
  }

  @Override
  protected void onResume() {
    super.onResume();
    flipView.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    flipView.onPause();
  }
}
