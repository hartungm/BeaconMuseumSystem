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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    private DrawerLayout drawerLayout;
    private ListView beaconDrawer;
    private ArrayAdapter<String> beaconAdapter;
    private BroadcastReceiver receiver;
    private ActionBarDrawerToggle drawerToggle;

    private BeaconService beaconService;
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
        beaconDrawer = (ListView) findViewById(R.id.beacon_drawer);
        title = getTitle();

        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        beaconDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position, beaconAdapter.getItem(position));
            }
        });

        ActionBar actionBar = getActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);

            beaconAdapter = new ArrayAdapter<>(
                    actionBar.getThemedContext(),
                    android.R.layout.simple_list_item_activated_1,
                    android.R.id.text1,
                    new ArrayList<String>()
            );
            beaconDrawer.setAdapter(beaconAdapter);
        }

        doBindService();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                List<String> beaconList = intent.getStringArrayListExtra(BeaconService.BEACON_UPDATE);
                beaconAdapter.clear();
                beaconAdapter.addAll(beaconList);
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
            selectItem(currentSelectedPosition, beaconAdapter.getItem(currentSelectedPosition));
            beaconDrawer.setItemChecked(currentSelectedPosition, true);
        }

        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }

    private void selectItem(int position, String beaconID) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MainFragment.newInstance(beaconID))
                .commit();

        currentSelectedPosition = position;
        beaconDrawer.setItemChecked(currentSelectedPosition, true);

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
            return true;
        }

        return super.onOptionsItemSelected(item);
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
