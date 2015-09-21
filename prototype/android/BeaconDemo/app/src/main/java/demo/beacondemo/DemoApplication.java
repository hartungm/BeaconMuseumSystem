package demo.beacondemo;

import android.app.Application;

import com.estimote.sdk.EstimoteSDK;
import com.parse.Parse;

public class DemoApplication extends Application {

    private final static String APPLICATION_ID = "pEb6VnboQG5je7L71lGDe8uaYcRAB4MtgVbdPPue";
    private final static String CLIENT_KEY = "ISaiuqXl4OZtA995a2yjhwdJdWgCQpJqlSJd64Xb";

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(getApplicationContext(), APPLICATION_ID, CLIENT_KEY);

        EstimoteSDK.initialize(this, "YOUR APP ID", "YOUR APP TOKEN");
        EstimoteSDK.enableDebugLogging(true);
    }
}