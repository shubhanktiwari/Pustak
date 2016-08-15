package projects.mobiinfant.pustak.adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prem on 9/1/16.
 */
public class DataModel {

    private String imagPath;
    private String descriptionStr;
    private boolean isTitle ;
    private String title;
    private List<DataModel> listDesc = new ArrayList<>();

    public List<DataModel> getListDesc() {
        return listDesc;
    }

    public void setListDesc(DataModel dataModel) {
        listDesc.add(dataModel);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private int index;

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

}
