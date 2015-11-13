package museubeacon.museubeacon.template;

import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.Serializable;

/**
 * Created by carlton on 11/1/15
 */
public class TemplateObject implements Serializable {

    private ParseObject parseObject;

    public TemplateObject(ParseObject parseObject) {
        this.parseObject = parseObject;
    }

    public String getTitle() {
        return parseObject.getString("Title");
    }
    public String getBeaconID() {
        return parseObject.getString("BeaconID");
    }
    public String getText() {
        return parseObject.getString("Text");
    }
    public ParseFile getImage() {
        return parseObject.getParseFile("Image");
    }
}
