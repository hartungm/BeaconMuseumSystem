package demo.beacondemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;

public class BeaconActivity extends AppCompatActivity {

    private SurfaceView view;

    private BeaconManager beaconManager;
    private Region region;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beacon_activity);

        view = (SurfaceView) findViewById(R.id.surfaceView);

        beaconManager = new BeaconManager(this);
        region = new Region("ranged region", "B9407F30-F5F8-466E-AFF9-25556B57FE6D", null, null);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                try {
                    beaconManager.startRanging(region);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    Beacon nearestBeacon = list.get(0);
                    view.setBackgroundColor(getBeaconColor(nearestBeacon));
                }
            }
        });
    }

    public int getBeaconColor(Beacon beacon) {
        String id = beacon.getMajor() + ":" + beacon.getMinor();

        if("32500:61345".equals(id)) {
            return Color.parseColor("#2E348F");
        } else if("40109:57375".equals(id)) {
            return Color.parseColor("#68BDDF");
        } else if("29121:22674".equals(id)) {
            return Color.parseColor("#7CA38C");
        }
        
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.beacon_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_parse) {
            startActivity(new Intent(this, ParseActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
