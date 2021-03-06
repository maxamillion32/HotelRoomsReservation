package com.hotel.hotelroomreservation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.hotel.hotelroomreservation.App;
import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.adapters.RoomAdapter;
import com.hotel.hotelroomreservation.constants.Constants;
import com.hotel.hotelroomreservation.model.Room;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private static final int START_ITEM = 0;
    private Firebase firebase;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        setUpNavigationView();

        mRecyclerView = (RecyclerView) findViewById(R.id.rooms_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebase = App.getInstance().getFirebaseConnection();
        firebase.keepSynced(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        navigationView.getMenu().getItem(START_ITEM).setChecked(true);
        setRoomList();
    }

    private void setRoomList() {
        firebase.child(Constants.ROOMS_KEY).addValueEventListener(new ValueEventListener() {
            List<Room> rooms;

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                rooms = new ArrayList<>();

                for (DataSnapshot roomSnapshot : snapshot.getChildren()) {
                    Room room = roomSnapshot.getValue(Room.class);
                    rooms.add(room);
                }

                if (mAdapter == null) {
                    mAdapter = new RoomAdapter((ArrayList<Room>) rooms, new RoomAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Room room) {
                            Intent intent = new Intent(MainActivity.this, RoomActivity.class);
                            intent.putExtra(Constants.ROOM_INTENT_KEY, room);
                            startActivity(intent);
                        }
                    });
                } else {
                    mAdapter.notifyDataSetChanged();
                }

                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(FirebaseError error) {
                Log.i("Firebase", "The reading is failed: " + error.getMessage());
            }
        });
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.tab_home:
                        drawer.closeDrawers();
                        return true;
                    case R.id.tab_search:
                        startActivity(new Intent(MainActivity.this, RoomFinderActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.tab_gallery:
                        startActivity(new Intent(MainActivity.this, PhotoListActivity.class));
                        drawer.closeDrawers();
                        return true;
                }
                menuItem.setChecked(true);
                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;

            case R.id.about_app:
                startActivity(new Intent(this, AboutAppActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
