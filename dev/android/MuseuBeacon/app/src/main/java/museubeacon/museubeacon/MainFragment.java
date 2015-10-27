package museubeacon.museubeacon;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String TEMPLATE_ID = "WQhNeP01dY";
    private final static String BEACON_ID = "beacon_id";

    private String beaconID;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment newInstance(String beaconID) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(BEACON_ID, beaconID);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater secondInflater = getActivity().getLayoutInflater();
        View viewToReturn = secondInflater.inflate(R.layout.main_fragment, container, false);
        super.onCreate(savedInstanceState);
        final TextView titleView = (TextView) viewToReturn.findViewById(R.id.fragmentTitle);
        final TextView textView = (TextView) viewToReturn.findViewById(R.id.fragmentText);
        final ImageView imageView = (ImageView) viewToReturn.findViewById(R.id.fragmentImage);

        /*ParseQuery<ParseObject> query = ParseQuery.getQuery("Main");
        query.getInBackground("WQhNeP01dY", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    String templateName = object.getString("TemplateName");
                    ParseQuery<ParseObject> query2 = ParseQuery.getQuery(templateName);
                    query2.getInBackground("QPTjCZpUjV", new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                titleView.setText(object.getString("Title"));
                                ParseFile file = object.getParseFile("Image");
                    file.getDataInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            imageView.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
                        }

                    });
                                textView.setText(object.getString("Text"));
                            } else {
                                titleView.setText("Parse object not found");
                                textView.setText("Parse object not found");
                            }
                        }
                    });
                } else {
                }
            }
        });
        */
        ParseQuery<ParseObject> query = ParseQuery.getQuery("DefaultTemplate");

        String objectID = null;
        if(beaconID.equals("29121:22674")) {
            objectID = "muk1jro3GV";
        } else if(beaconID.equals("40109:57375")) {
            objectID = "JnSBNP9dME";
        } else if(beaconID.equals("32500:61345")) {
            objectID = "hiOPfchAqo";
        }

        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    titleView.setText(object.getString("Title"));
                    ParseFile file = object.getParseFile("Image");
                    file.getDataInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            imageView.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
                        }

                    });
                    textView.setText(object.getString("Text"));
                } else {
                    titleView.setText("Parse object not found");
                    textView.setText("Parse object not found");
                }
            }
        });
        return viewToReturn;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        beaconID = getArguments().getString(BEACON_ID);
        ((MainActivity) activity).onSectionAttached(beaconID);
    }
}