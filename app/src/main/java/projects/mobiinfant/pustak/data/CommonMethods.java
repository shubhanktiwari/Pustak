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
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

import projects.mobiinfant.pustak.adapter.DataModel;
import projects.mobiinfant.pustak.ui.activity.SplashScreen;

public class CommonMethods {

  public static  List<DataModel> IMG_DESCRIPTIONS = new ArrayList<DataModel>();
    public static  List<DataModel> INDEX_EPISODE = new ArrayList<DataModel>();
    public static double SCREEN_SIZE_INCHES = 0;
    public static final String PREFS_NAME = "AOP_PREFS";
    public static final String PREFS_KEY_INDEX = "AOP_PREFS_Index";
    public static final String PREFS_KEY_EP_INDEX = "AOP_PREFS_EP_Index";

    private static String getJSONString(Context context)
    {
        String str = "";
        try {
                str="";
                AssetManager assetManager = context.getAssets();
                InputStream in = assetManager.open("pustak_data.txt");
                str = readFromStandardNIO(in);
            } catch (Exception ioe) {
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

        return context.getResources().getIdentifier(name,
                "drawable", context.getPackageName());
    }
    public static void getScreenInches(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels/dm.ydpi,2);
         SCREEN_SIZE_INCHES = Math.sqrt(x+y);
    }
    public static void onSetIndex(Context context, int intValue,int intEpisode) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putInt(PREFS_KEY_INDEX, intValue); //3
        editor.putInt(PREFS_KEY_EP_INDEX, intEpisode); //3
        editor.commit(); //4
    }
    public static int getIndex(Context context) {
        SharedPreferences settings;
        int text =0;
        try {
            settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
            text = settings.getInt(PREFS_KEY_INDEX, 0); //2
        }catch (Exception e){
            System.out.print(e.getMessage());
        }
        return text;
    }
    public static int getEPIndex(Context context) {
        SharedPreferences settings;
        int text =0;
        try {
            settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
            text = settings.getInt(PREFS_KEY_EP_INDEX, 0); //2
        }catch (Exception e){
            System.out.print(e.getMessage());
        }
        return text;
    }
    public static String readFromStandardNIO(InputStream inputStream) {
        java.nio.ByteBuffer buffer = java.nio.ByteBuffer.allocate(20480000);
        try {
            ReadableByteChannel channel = Channels.newChannel(inputStream);
            channel.read(buffer);
            channel.close();
        } catch (Exception e) {

        }
        return new String(buffer.array());
    }

}
