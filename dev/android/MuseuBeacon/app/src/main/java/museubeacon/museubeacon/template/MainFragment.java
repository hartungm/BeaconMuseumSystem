package museubeacon.museubeacon.template;

import android.app.Activity;
import android.app.Fragment;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import museubeacon.museubeacon.MainActivity;
import museubeacon.museubeacon.R;

public class MainFragment extends Fragment {

    private final static String PARSE_OBJ = "parse_object";

    TemplateObject obj;

    public static Fragment newInstance(TemplateObject obj) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putParcelable(PARSE_OBJ, obj);

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
        final Button playButton = (Button) view.findViewById(R.id.playButton);
        final Button pauseButton = (Button) view.findViewById(R.id.pauseButton);
        final MediaPlayer mediaPlayer = ((MainActivity) getActivity()).getMediaPlayer();

        titleView.setText(obj.getTitle());
        textView.setText(obj.getText());
        imageView.setImageBitmap(obj.getImage());

        getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(obj.getAudioURL());
            mediaPlayer.prepare();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        // When play button is clicked for MediaPlayer, plays the audio file
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                pauseButton.setVisibility(View.VISIBLE);
                v.setVisibility(View.GONE);
            }
        });

        // When pause button is clicked for MediaPlayer, pauses the audio file
        pauseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                playButton.setVisibility(View.VISIBLE);
                v.setVisibility(View.GONE);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        MediaPlayer mediaPlayer = ((MainActivity) getActivity()).getMediaPlayer();
        View view = this.getView();

        if(view != null && mediaPlayer.isPlaying()) {
            view.findViewById(R.id.pauseButton).setVisibility(View.GONE);
            view.findViewById(R.id.playButton).setVisibility(View.GONE);
        }
        super.onResume();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        obj = getArguments().getParcelable(PARSE_OBJ);
        if(obj != null) {
            ((MainActivity) activity).onSectionAttached(obj.getTitle());
        }
    }
}