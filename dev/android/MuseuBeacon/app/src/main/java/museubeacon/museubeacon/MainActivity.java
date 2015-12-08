package museubeacon.museubeacon;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;

import java.util.ArrayList;
import java.util.List;

import museubeacon.museubeacon.template.MainFragment;
import museubeacon.museubeacon.template.TemplateObject;

public class MainActivity extends Activity {

    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    private DrawerLayout drawerLayout;
    private ListView templateDrawer;
    private ArrayAdapter<String> templateDisplayAdapter;
    private BroadcastReceiver receiver;
    private ActionBarDrawerToggle drawerToggle;
    private final MediaPlayer mediaPlayer = new MediaPlayer();

    private BeaconService beaconService;
    private List<TemplateObject> templateList = new ArrayList<>();
    private ServiceConnection beaconServiceConnection = new BeaconServiceConnection();
    private boolean isBound;

    private int currentSelectedPosition = 0;
    private CharSequence title;

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(BeaconService.BEACON_UPDATE));
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        templateDrawer = (ListView) findViewById(R.id.beacon_drawer);
        title = "MuseBeacon";

        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        templateDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position, templateList.get(position));
            }
        });

        ActionBar actionBar = getActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(this.title);

            templateDisplayAdapter = new ArrayAdapter<>(
                    actionBar.getThemedContext(),
                    android.R.layout.simple_list_item_activated_1,
                    android.R.id.text1,
                    new ArrayList<String>()
            );
            templateDrawer.setAdapter(templateDisplayAdapter);
        }

        doBindService();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                templateList = beaconService.getTemplateList();

                templateDisplayAdapter.clear();
                templateDisplayAdapter.addAll(beaconService.getDisplayList());
            }
        };

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        drawerToggle = new ActionBarDrawerToggle(
                this,                    /* host Activity */
                drawerLayout,                    /* DrawerLayout object */
                null,                             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close         /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        if (savedInstanceState != null) {
            drawerLayout.openDrawer(GravityCompat.START);
            currentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            if(currentSelectedPosition < templateList.size()) {
                selectItem(currentSelectedPosition, templateList.get(currentSelectedPosition));
                templateDrawer.setItemChecked(currentSelectedPosition, true);
            }
        }

        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onDestroy() {
        doUnbindService();
        super.onDestroy();
    }

    private void selectItem(int position, TemplateObject obj) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MainFragment.newInstance(obj))
                .commit();

        if(position != currentSelectedPosition) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        currentSelectedPosition = position;
        templateDrawer.setItemChecked(currentSelectedPosition, true);

        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

    }

    void doBindService() {
        bindService(new Intent(this, BeaconService.class), beaconServiceConnection, Context.BIND_AUTO_CREATE);
        isBound = true;
    }

    void doUnbindService() {
        if(isBound) {
            unbindService(beaconServiceConnection);
        }
    }

    public void onSectionAttached(String title) {
        this.title = title;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        if(actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        restoreActionBar();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            List<Beacon> dummyBeacons = new ArrayList<>();
            Beacon beacon1 = new Beacon(BeaconService.UUID, "", "", 32500, 61345, 0, 0);
            Beacon beacon2 = new Beacon(BeaconService.UUID, "", "", 40109, 57375, 0, 0);
            Beacon beacon3 = new Beacon(BeaconService.UUID, "", "", 29121, 22674, 0, 0);
            dummyBeacons.add(beacon1);
            dummyBeacons.add(beacon2);
            dummyBeacons.add(beacon3);

            beaconService.updateBeaconList(dummyBeacons);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public MediaPlayer getMediaPlayer() {
        return this.mediaPlayer;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private class BeaconServiceConnection implements ServiceConnection {
        public void onServiceConnected(ComponentName className, IBinder service) {
            beaconService = ((BeaconService.BeaconBinder)service).getService();
            Toast.makeText(MainActivity.this, "Beacon Service Connected", Toast.LENGTH_SHORT).show();

            beaconService.startMonitoring();
        }

        public void onServiceDisconnected(ComponentName className) {
            beaconService = null;
            Toast.makeText(MainActivity.this, "Beacon Service Disconnected", Toast.LENGTH_SHORT).show();
        }
    }

}
