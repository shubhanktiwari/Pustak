package projects.mobiinfant.pustak.adapter;

/**
 * Created by prem on 9/1/16.
 */
public class DataModel {

    private String imagPath;
    private String descriptionStr;
    private boolean isTitle ;
    private String title;
    private int indexPostion;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIndexPostion() {
        return indexPostion;
    }

    public void setIndexPostion(int indexPostion) {
        this.indexPostion = indexPostion;
    }
}
