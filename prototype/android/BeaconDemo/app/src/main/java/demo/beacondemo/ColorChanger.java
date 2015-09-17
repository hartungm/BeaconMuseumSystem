package demo.beacondemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;

import com.estimote.sdk.BeaconManager;

import prototype.android.closest_beacon_demo.R;

public class ColorChanger extends AppCompatActivity {

    private BeaconManager beaconManager = new BeaconManager(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        SurfaceView view = (SurfaceView) findViewById(R.id.surfaceView);
        view.setBackgroundColor(Color.BLACK);

        view.setOnClickListener(new OnClickListener() {
            private int i = 0;

            @Override
            public void onClick(View v) {
                switch (i % 10) {
                    case 0:
                        v.setBackgroundColor(Color.BLUE);
                        break;
                    case 1:
                        v.setBackgroundColor(Color.BLACK);
                        break;
                    case 2:
                        v.setBackgroundColor(Color.CYAN);
                        break;
                    case 3:
                        v.setBackgroundColor(Color.DKGRAY);
                        break;
                    case 4:
                        v.setBackgroundColor(Color.RED);
                        break;
                    case 5:
                        v.setBackgroundColor(Color.YELLOW);
                        break;
                    case 6:
                        v.setBackgroundColor(Color.WHITE);
                        break;
                    case 7:
                        v.setBackgroundColor(Color.MAGENTA);
                        break;
                    case 8:
                        v.setBackgroundColor(Color.GREEN);
                        break;
                    case 9:
                        v.setBackgroundColor(Color.LTGRAY);
                        break;
                }

                i++;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_blue) {
            View view = findViewById(R.id.surfaceView);
            view.setBackgroundColor(Color.BLUE);
        }

        return super.onOptionsItemSelected(item);
    }
}
