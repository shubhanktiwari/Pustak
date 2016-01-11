package projects.mobiinfant.pustak.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aphidmobile.utils.AphidLog;
import com.aphidmobile.utils.IO;
import com.aphidmobile.utils.UI;

import java.util.ArrayList;
import java.util.List;

import projects.mobiinfant.pustak.R;
import projects.mobiinfant.pustak.data.CommonMethods;

public class PustakAdapter extends BaseAdapter {

  private LayoutInflater inflater;

  private int repeatCount = 1;

  private List<DataModel> travelData;

  public PustakAdapter(Context context) {
    inflater = LayoutInflater.from(context);
    travelData = new ArrayList<DataModel>(CommonMethods.IMG_DESCRIPTIONS);
  }

  @Override
  public int getCount() {
    return travelData.size() * repeatCount;
  }

  public int getRepeatCount() {
    return repeatCount;
  }

  public void setRepeatCount(int repeatCount) {
    this.repeatCount = repeatCount;
  }

  @Override
  public Object getItem(int position) {
    return position;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View layout = convertView;
    if (convertView == null) {
      layout = inflater.inflate(R.layout.complex1, null);
      AphidLog.d("created new view from adapter: %d", position);
    }

    final DataModel data = travelData.get(position % travelData.size());

      if(data.getImagPath() !=null && data.getImagPath().length() > 0) {
          UI.<ImageView>findViewById(layout, R.id.photo)
            .setImageBitmap(IO.readBitmap(inflater.getContext().getAssets(), data.getImagPath()));
      }else{
          UI.<ImageView>findViewById(layout, R.id.photo).setVisibility(ImageView.GONE);
      }

     if(data.isTitle()){
          UI.<TextView>findViewById(layout, R.id.description)
           .setText(Html.fromHtml(data.getTitle()));
          UI.<TextView>findViewById(layout, R.id.description).setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
          UI.<TextView>findViewById(layout, R.id.description).setGravity(Gravity.CENTER);
          UI.<TextView>findViewById(layout, R.id.description).setTextSize(30);
      }else{
         UI.<TextView>findViewById(layout, R.id.description)
           .setText(Html.fromHtml(data.getDescriptionStr()));
     }



    return layout;
  }

  public void removeData(int index) {
    if (travelData.size() > 1) {
      travelData.remove(index);
    }
  }
}
