package projects.mobiinfant.pustak.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.util.DisplayMetrics;

import junit.framework.Assert;

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
    public static double SCREEN_SIZE_INCHES = 0;

    public static final String PREFS_NAME = "AOP_PREFS";
    public static final String PREFS_KEY = "AOP_PREFS_String";
    public static void onSave(Context context, String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putString(PREFS_KEY, text); //3
        editor.commit(); //4
    }
    public static String getValue(Context context) {
        SharedPreferences settings;
        String text = "";
        try {
            settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
            text = settings.getString(PREFS_KEY, null); //2
        }catch (Exception e){
            System.out.print(e.getMessage());
        }
        return text;
    }
    private static String getJSONString(Context context)
    {
        String str = getValue(context);
        try {
            str = str.replaceAll("\\n+", "");
            str = str.replaceAll("\\t+", "");
        }catch (Exception e){
            str="";
        }
        if(!(str.length() > 0)) {
            try {
                str="";
                AssetManager assetManager = context.getAssets();
                InputStream in = assetManager.open("pustak_data.txt");
                InputStreamReader isr = new InputStreamReader(in);
                char[] inputBuffer = new char[100];

                int charRead;
                while ((charRead = isr.read(inputBuffer)) > 0) {
                    String readString = String.copyValueOf(inputBuffer, 0, charRead);
                    str += readString;
                }
            } catch (Exception ioe) {
                ioe.printStackTrace();
            }
            onSave(context,str);
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

                if(jsonObjectTemp.has("isTitle") && jsonObjectTemp.getBoolean("isTitle")){
                    dataModel.setIsTitle(true);
                    dataModel.setTitle(jsonObjectTemp.getString("title"));
                    dataModel.setIndexPostion(i);
                    dataModel.setImagPath(jsonObjectTemp.getString("imagPath"));
                    dataModel.setDescriptionStr(jsonObjectTemp.getString("descriptionStr"));
                    dataModel.setIndexPostion(IMG_DESCRIPTIONS.size());
                    INDEX_EPISODE.add(dataModel);
                    IMG_DESCRIPTIONS.add(dataModel);
                }else {

                   getStringPerPage(jsonObjectTemp.getString("descriptionStr"),jsonObjectTemp.getString("imagPath"));
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private static String getStringPerPage(String fullStr,String imagePath){
        DataModel dataModel = new DataModel();
        dataModel.setIsTitle(false);
        int tempStringLength = fullStr.length();
        if(tempStringLength > 1000){
            int index = 0;
            while (index < fullStr.length()) {
                dataModel = new DataModel();
                dataModel.setIsTitle(false);
                dataModel.setImagPath(imagePath);
                dataModel.setDescriptionStr(fullStr.substring(index, Math.min(index + 1000,fullStr.length())));
                IMG_DESCRIPTIONS.add(dataModel);
                index += 1000;
            }
        }else{
            dataModel.setImagPath(imagePath);
            dataModel.setDescriptionStr(fullStr);
            IMG_DESCRIPTIONS.add(dataModel);
        }



        return "";
    }
    public static int getDrawable(Context context, String name)
    {
        Assert.assertNotNull(context);
        Assert.assertNotNull(name);

        return context.getResources().getIdentifier(name,
                "drawable", context.getPackageName());
    }
    public static void getScreenInches(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(dm.widthPixels/dm.xdpi,2);
        double y = Math.pow(dm.heightPixels/dm.ydpi,2);
         SCREEN_SIZE_INCHES = Math.sqrt(x+y);
    }


}
