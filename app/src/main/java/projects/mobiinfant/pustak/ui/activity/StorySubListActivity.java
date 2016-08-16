package projects.mobiinfant.pustak.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import projects.mobiinfant.pustak.R;
import projects.mobiinfant.pustak.adapter.DataModel;
import projects.mobiinfant.pustak.adapter.StorySubListAdaptor;
import projects.mobiinfant.pustak.data.CommonMethods;

/**
 * Created by prem on 16/1/16.
 */
public class StorySubListActivity extends Activity {

    ListView listView;
    public static DataModel INDEX_EPISODE_SELECTED;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setTitle("RAMAYANA CG");
        setContentView(R.layout.storysub_list_activity);
        listView = (ListView)findViewById(R.id.substorylist_id);
        if(CommonMethods.INDEX_EPISODE !=null) {
            StorySubListAdaptor storySubListAdaptor = new StorySubListAdaptor(this, CommonMethods.INDEX_EPISODE);
            if (storySubListAdaptor != null) {
                listView.setAdapter(storySubListAdaptor);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        StorySubListActivity.INDEX_EPISODE_SELECTED = CommonMethods.INDEX_EPISODE.get(position);
                        StorySubListActivity.INDEX_EPISODE_SELECTED.setIndex(position);
                        CommonMethods.onSetIndex(getApplicationContext(),-1,-1);
                        onSelectedItemDetail();
                    }
                });
            }
        }
        if(CommonMethods.getEPIndex(this)>0){
            onSavedPage();
        }
    }
    private void onSelectedItemDetail(){
        if(INDEX_EPISODE_SELECTED !=null && INDEX_EPISODE_SELECTED.getListDesc().size() > 0) {
            Intent intent = new Intent(this, FlipHorizontalLayoutActivity.class);
            startActivity(intent);
        }
    }
    private void onSavedPage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.logo);
        builder.setTitle(getString(R.string.app_name_hindi));
        builder.setMessage("पुराना अध्याय, क्या आप जारी रखना चाहते हैं? "+getResources().getString(R.string.aadhya)+": " +CommonMethods.INDEX_EPISODE.get(CommonMethods.getEPIndex(getApplicationContext())).getListDesc().get(0).getTitle() +" , "+getResources().getString(R.string.page)+" "+CommonMethods.getIndex(this));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StorySubListActivity.INDEX_EPISODE_SELECTED = CommonMethods.INDEX_EPISODE.get(CommonMethods.getEPIndex(getApplicationContext()));
                onSelectedItemDetail();
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

}
