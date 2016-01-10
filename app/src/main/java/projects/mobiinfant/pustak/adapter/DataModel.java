package projects.mobiinfant.pustak.adapter;

/**
 * Created by prem on 9/1/16.
 */
public class DataModel {

    private String imagPath;
    private String descriptionStr;
    private boolean isTitle ;

    public String getImagPath() {
        return imagPath;
    }

    public void setImagPath(String imagPath) {
        this.imagPath = imagPath;
    }

    public String getDescriptionStr() {
        return descriptionStr;
    }

    public void setDescriptionStr(String descriptionStr) {
        this.descriptionStr = descriptionStr;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setIsTitle(boolean isTitle) {
        this.isTitle = isTitle;
    }
}
