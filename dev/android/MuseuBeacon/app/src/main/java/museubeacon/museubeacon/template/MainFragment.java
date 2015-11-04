package museubeacon.museubeacon.template;

import android.app.Fragment;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import museubeacon.museubeacon.MainActivity;
import museubeacon.museubeacon.R;

public class MainFragment extends Fragment {

    private final static String PARSE_OBJ = "parse_object";

    TemplateObject obj;

    public static Fragment newInstance(TemplateObject obj) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARSE_OBJ, obj);

        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = getActivity().getLayoutInflater().inflate(R.layout.main_fragment, container, false);

        final TextView titleView = (TextView) view.findViewById(R.id.fragmentTitle);
        final TextView textView = (TextView) view.findViewById(R.id.fragmentText);
        final ImageView imageView = (ImageView) view.findViewById(R.id.fragmentImage);

        titleView.setText(obj.getTitle());
        textView.setText(obj.getText());
        ParseFile file = obj.getImage();
        file.getDataInBackground(new GetDataCallback() {
            public void done(byte[] data, ParseException e) {
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
            }

        });

        return view;
    }

    public void onAttach(Context activity) {
        super.onAttach(activity);
        obj = (TemplateObject) getArguments().getSerializable(PARSE_OBJ);
        if(obj != null) {
            ((MainActivity) activity).onSectionAttached(obj.getTitle());
        }
    }
}