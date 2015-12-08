package museubeacon.museubeacon.cache;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import museubeacon.museubeacon.template.TemplateObject;

/**
 * Created by carlton on 11/12/15
 */
public class CacheableList {

    private final List<TemplateObject> templateList = new ArrayList<>();
    private Date expirationDate;

    public CacheableList(TemplateObject templObject) {
        this.templateList.add(templObject);

        Calendar cal = Calendar.getInstance();
        cal.roll(Calendar.MINUTE, 5);
        // This cache object expires 5 minutes after creation
        this.expirationDate = cal.getTime();
    }

    public List<TemplateObject> getTemplateList() {
        return templateList;
    }
    public boolean isExpired() {
        return expirationDate.before(new Date());
    }
}
