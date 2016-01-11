package projects.mobiinfant.pustak.data;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import projects.mobiinfant.pustak.adapter.DataModel;

public class CommonMethods {

  public static  List<DataModel> IMG_DESCRIPTIONS = new ArrayList<DataModel>();
    public static  List<DataModel> INDEX_EPISODE = new ArrayList<DataModel>();

    private static String getJSONString(Context context)
    {
        String str = "";
        try
        {
            AssetManager assetManager = context.getAssets();
            InputStream in = assetManager.open("pustak_data.txt");
            InputStreamReader isr = new InputStreamReader(in);
            char [] inputBuffer = new char[100];

            int charRead;
            while((charRead = isr.read(inputBuffer))>0)
            {
                String readString = String.copyValueOf(inputBuffer,0,charRead);
                str += readString;
            }
        }
        catch(Exception ioe)
        {
            ioe.printStackTrace();
        }

        return str;
    }
    public static void setData(Context context){
        JSONObject json = new JSONObject();
        DataModel dataModel;
        try {
            json = new JSONObject(getJSONString(context));
            JSONArray jsonArray = json.getJSONArray("page");
            for (int i=0; i<jsonArray.length();i++){
                JSONObject jsonObjectTemp = jsonArray.getJSONObject(i);
                dataModel = new DataModel();
                dataModel.setDescriptionStr(jsonObjectTemp.getString("descriptionStr"));
                dataModel.setImagPath(jsonObjectTemp.getString("imagPath"));
                if(jsonObjectTemp.has("isTitle") && jsonObjectTemp.getBoolean("isTitle")){
                    dataModel.setIsTitle(true);
                    dataModel.setTitle(jsonObjectTemp.getString("title"));
                    INDEX_EPISODE.add(dataModel);
                }else {
                    dataModel.setIsTitle(false);
                }
                IMG_DESCRIPTIONS.add(dataModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
