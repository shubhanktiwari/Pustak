package projects.mobiinfant.pustak.adapter;

/**
 * Created by prem on 6/8/16.
 */

import java.util.ArrayList;
        import java.util.List;
public class MainJson {

    private List<DataModel> page = new ArrayList<DataModel>();

    /**
     *
     * @return
     * The page
     */
    public List<DataModel> getPage() {
        return page;
    }

    /**
     *
     * @param page
     * The page
     */
    public void setPage(List<DataModel> page) {
        this.page = page;
    }

}