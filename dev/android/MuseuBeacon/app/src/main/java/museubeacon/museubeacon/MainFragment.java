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
import com.parse.Parse;
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
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String APPLICATION_ID = "BYMFAF3TttqN60BhZ0dykTDeUsFhB2OEtpovqLVT";
    private static final String CLIENT_KEY = "4Di9098kJSxeY7Ddx82qCYRxdzO47OQdnqTpE6ff";
    private static final String TEMPLATE_ID = "WQhNeP01dY";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment newInstance(int sectionNumber) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
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
        Parse.enableLocalDatastore(getContext());
        Parse.initialize(getContext(), APPLICATION_ID, CLIENT_KEY);
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
        query.getInBackground("QPTjCZpUjV", new GetCallback<ParseObject>() {
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
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}