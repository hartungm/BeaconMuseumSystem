package museubeacon.museubeacon.template;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by carlton on 11/1/15
 */
public class TemplateObject implements Parcelable {

    private String title;
    private String beaconID;
    private String text;
    private Bitmap image;
    private String audioURL;

    public TemplateObject(ParseObject parseObject) {
        this.title = parseObject.getString("Title");
        this.beaconID = parseObject.getString("BeaconID");
        this.text = parseObject.getString("Text");

        try {
            byte[] data = parseObject.getParseFile("Image").getData();
            this.image = BitmapFactory.decodeByteArray(data, 0, data.length);
        } catch (Exception e) {
            this.image = null;
            e.printStackTrace();
        }

        ParseFile pf = parseObject.getParseFile("Audio");
        this.audioURL = pf != null ? pf.getUrl() : null;
    }

    public String getTitle() {
        return title;
    }
    public String getBeaconID() {
        return beaconID;
    }
    public String getText() {
        return text;
    }
    public Bitmap getImage() {
        return image;
    }
    public String getAudioURL() {
        return audioURL;
    }


    public TemplateObject(Parcel in){
        String[] data = new String[4];
        in.readStringArray(data);

        this.title = data[0];
        this.beaconID = data[1];
        this.text = data[2];
        this.audioURL = data[3];
        this.image = in.readParcelable(Bitmap.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.title,
                this.beaconID,
                this.text,
                this.audioURL});
        dest.writeParcelable(this.image, 0);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public TemplateObject createFromParcel(Parcel in) {
            return new TemplateObject(in);
        }

        public TemplateObject[] newArray(int size) {
            return new TemplateObject[size];
        }
    };
}
