package museubeacon.museubeacon;

import android.app.Application;

import com.estimote.sdk.EstimoteSDK;
import com.parse.Parse;

public class MuseApp extends Application {

    private final static String APPLICATION_ID = "BYMFAF3TttqN60BhZ0dykTDeUsFhB2OEtpovqLVT";
    private final static String CLIENT_KEY = "4Di9098kJSxeY7Ddx82qCYRxdzO47OQdnqTpE6ff";

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(getApplicationContext(), APPLICATION_ID, CLIENT_KEY);

        EstimoteSDK.initialize(this, "YOUR APP ID", "YOUR APP TOKEN");
        EstimoteSDK.enableDebugLogging(true);
    }
}
