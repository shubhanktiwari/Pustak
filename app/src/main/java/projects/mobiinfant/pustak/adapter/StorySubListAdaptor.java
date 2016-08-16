package projects.mobiinfant.pustak.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import projects.mobiinfant.pustak.R;

/**
 * Created by prem on 16/1/16.
 */
public class StorySubListAdaptor extends BaseAdapter {

    private Context mContext;
    private  List<DataModel> sub_story_list;
    private static LayoutInflater inflater=null;
    public StorySubListAdaptor(Context mContext, List<DataModel> sub_story_list){
        this.mContext = mContext;
        this.sub_story_list = sub_story_list;
    }
    @Override
    public int getCount() {
        return this.sub_story_list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.sub_story_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = ( LayoutInflater )mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.list_item, null);
        LinearLayout linearLayout = (LinearLayout)convertView.findViewById(R.id.list_layout_id);
       /* if(position == 0){
            linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }else if(position%2 == 0 ){
            linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }else {
            linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.lt_gray));
        }*/
        DataModel storyMain = this.sub_story_list.get(position).getListDesc().get(0);
        TextView textViewTitle = (TextView)convertView.findViewById(R.id.title_list_id);
        TextView textViewTitleSub = (TextView)convertView.findViewById(R.id.sub_title_id);
        textViewTitle.setText(mContext.getResources().getString(R.string.aadhya)+":  "+storyMain.getTitle());
        try{
            textViewTitleSub.setText(this.sub_story_list.get(position).getListDesc().get(1).getDescriptionStr().replaceAll("<br>","").replaceAll("<b>",""));
        }catch (Exception e){
            textViewTitleSub.setText(storyMain.getDescriptionStr());
        }


        return convertView;
    }
}
