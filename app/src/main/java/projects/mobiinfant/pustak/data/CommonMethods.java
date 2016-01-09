package projects.mobiinfant.pustak.data;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
public class CommonMethods {

  public static final List<Data> IMG_DESCRIPTIONS = new ArrayList<Data>();

  static {
    CommonMethods.IMG_DESCRIPTIONS.add(new CommonMethods.Data("Potala Palace", "potala_palace.jpg",
                                                  "The <b>Potala Palace</b> is located in Lhasa, Tibet Autonomous Region, China. It is named after Mount Potalaka, the mythical abode of Chenresig or Avalokitesvara.",
                                                  "China", "Lhasa",
                                                  "http://en.wikipedia.org/wiki/Potala_Palace"));

  }

  public static final class Data {

    public final String title;
    public final String imageFilename;
    public final String description;
    public final String country;
    public final String city;
    public final String link;

    private Data(String title, String imageFilename, String description, String country,
                 String city, String link) {
      this.title = title;
      this.imageFilename = imageFilename;
      this.description = description;
      this.country = country;
      this.city = city;
      this.link = link;
    }
    private String getJSONString(Context context)
    {
        String str = "";
        try
        {
            AssetManager assetManager = context.getAssets();
            InputStream in = assetManager.open("json.txt");
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
  }
}
