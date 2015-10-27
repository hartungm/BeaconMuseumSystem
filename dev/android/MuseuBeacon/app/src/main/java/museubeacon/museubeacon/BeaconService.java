package museubeacon.museubeacon;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.ArrayList;
import java.util.List;

public class BeaconService extends Service {

    public final static String BEACON_UPDATE = "musebeacon.BeaconService.BEACON_UPDATE";
    public final static String UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";

    private final IBinder binder = new BeaconBinder();
    private final ArrayList<String> beaconList = new ArrayList<>();

    private BeaconManager beaconManager;
    private LocalBroadcastManager broadcaster;

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service created", Toast.LENGTH_SHORT).show();
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service destroyed", Toast.LENGTH_SHORT).show();
    }

    public void startMonitoring() {
        beaconManager = new BeaconManager(this);
        beaconManager.setForegroundScanPeriod(3000, 20000);

        final Region region = new Region("ranged region", UUID, null, null);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                updateBeaconList(list);
            }
        });

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
    }

    public void updateBeaconList(List<Beacon> beacons) {
        beaconList.clear();
        for(Beacon beacon : beacons) {
            beaconList.add(beacon.getMajor() + ":" + beacon.getMinor());
        }

        if(beaconList.isEmpty()) {
            beaconList.add("No beacons in range");
        }

        Intent intent = new Intent(BEACON_UPDATE);
        intent.putExtra(BEACON_UPDATE, beaconList);
        broadcaster.sendBroadcast(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class BeaconBinder extends Binder {
        BeaconService getService() {
            return BeaconService.this;
        }
    }
}
