package museubeacon.museubeacon;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class BeaconService extends Service {

    private final IBinder binder = new BeaconBinder();

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service destroyed", Toast.LENGTH_SHORT).show();
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
