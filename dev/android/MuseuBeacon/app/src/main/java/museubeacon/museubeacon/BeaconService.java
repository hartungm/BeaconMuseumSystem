package museubeacon.museubeacon;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import museubeacon.museubeacon.cache.BeaconCache;
import museubeacon.museubeacon.template.TemplateObject;

public class BeaconService extends Service {

    public final static String BEACON_UPDATE = "musebeacon.BeaconService.BEACON_UPDATE";
    public final static String UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";

    private final IBinder binder = new BeaconBinder();
    private final List<TemplateObject> templateList = new ArrayList<>();
    private final List<String> displayList = new ArrayList<>();

    private final Map<String, Set<String>> templateMap = new HashMap<>();

    private BeaconManager beaconManager;
    private LocalBroadcastManager broadcaster;

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service created", Toast.LENGTH_SHORT).show();
        broadcaster = LocalBroadcastManager.getInstance(this);

        List<ParseObject> list = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Main");
        try {
            list.addAll(query.find());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (ParseObject obj : list) {
            JSONArray arr = obj.getJSONArray("TemplateNames");

            if (arr != null) {
                Set<String> templateNames = new HashSet<>();
                for (int i = 0; i < arr.length(); i++) {
                    try {
                        templateNames.add(arr.getString(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                templateMap.put(obj.getString("BeaconID"), templateNames);
            }
        }
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service destroyed", Toast.LENGTH_SHORT).show();
    }

    public void startMonitoring() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // Hack to get it to work in the emulator
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            List<Beacon> dummyBeacons = new ArrayList<>();
            Beacon beacon1 = new Beacon(UUID, "", "", 32500, 61345, 0, 0);
            Beacon beacon2 = new Beacon(UUID, "", "", 40109, 57375, 0, 0);
            Beacon beacon3 = new Beacon(UUID, "", "", 29121, 22674, 0, 0);
            dummyBeacons.add(beacon1);
            dummyBeacons.add(beacon2);
            dummyBeacons.add(beacon3);

            updateBeaconList(dummyBeacons);
            return;
        }

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
        templateList.clear();
        displayList.clear();

        Map<String, Set<String>> templateToBeaconMap = new HashMap<>();
        for(Beacon beacon : beacons) {
            String beaconID = beacon.getMajor() + "-" + beacon.getMinor();

            List<TemplateObject> cachedList = BeaconCache.getList(beaconID);
            if(cachedList != null) {
                for(TemplateObject obj : cachedList) {
                    templateList.add(obj);
                    displayList.add(obj.getTitle());
                }
            } else {
                Set<String> templates = templateMap.get(beaconID);
                if (templates != null) {
                    for (String template : templates) {
                        Set<String> beaconIDs = templateToBeaconMap.get(template);
                        if (beaconIDs == null) {
                            beaconIDs = new HashSet<>();
                            beaconIDs.add(beaconID);
                            templateToBeaconMap.put(template, beaconIDs);
                        } else {
                            beaconIDs.add(beaconID);
                        }
                    }
                }
            }
        }

        for(Map.Entry<String, Set<String>> template : templateToBeaconMap.entrySet()) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery(template.getKey());
            query.whereContainedIn("BeaconID", template.getValue());

            try {
                for(ParseObject obj : query.find()) {
                    TemplateObject templObj = new TemplateObject(obj);
                    templateList.add(templObj);
                    displayList.add(templObj.getTitle());
                    BeaconCache.put(templObj);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if(templateList.isEmpty()) {
            displayList.add("No beacons in range");
        }

        broadcaster.sendBroadcast(new Intent(BEACON_UPDATE));
    }

    public List<TemplateObject> getTemplateList() {
        return templateList;
    }

    public List<String> getDisplayList() {
        return displayList;
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
