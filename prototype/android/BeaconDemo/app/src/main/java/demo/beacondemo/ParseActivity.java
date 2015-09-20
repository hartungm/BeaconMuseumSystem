package demo.beacondemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

import prototype.android.closest_beacon_demo.R;

/**
 * Created by gina on 9/17/15.
 */
public class ParseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this, "pEb6VnboQG5je7L71lGDe8uaYcRAB4MtgVbdPPue", "ISaiuqXl4OZtA995a2yjhwdJdWgCQpJqlSJd64Xb");
        ParseObject testObject = new ParseObject("DemoMuseum");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("MuseumData");
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setTextSize(40);
        query.whereEqualTo("objectId", "98yT6nwRBj");
        textView.setText(testObject.getString("Artist"));
        setContentView(R.layout.activity_main_menu);
    }
}
