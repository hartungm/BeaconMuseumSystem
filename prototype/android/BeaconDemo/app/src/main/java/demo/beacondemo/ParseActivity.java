package demo.beacondemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by gina on 9/17/15.
 */
public class ParseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.parse_activity);

        final TextView textView = (TextView) findViewById(R.id.textView);
        textView.setTextSize(40);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("MusuemData");

        query.getInBackground("98yT6nwRBj", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    textView.setText(object.getString("Artist"));
                } else {
                    textView.setText("Parse object not found");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.parse_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_beacon) {
            startActivity(new Intent(this, BeaconActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
