package com.ftninformatika.moviesactors.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.ftninformatika.moviesactors.Adapters.DrawerListViewAdapter;
import com.ftninformatika.moviesactors.Fragments.WatchedFragment;
import com.ftninformatika.moviesactors.Fragments.SearchFragment;
import com.ftninformatika.moviesactors.Fragments.SettingsFragment;
import com.ftninformatika.moviesactors.Models.Movie;
import com.ftninformatika.moviesactors.Models.NavigationItem;
import com.ftninformatika.moviesactors.R;

import java.util.ArrayList;
import java.util.List;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.ftninformatika.moviesactors.Net.ORMLite.DatabaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity implements SearchFragment.onListItemClickListener , WatchedFragment.onItemClickListener{

    public static final int NOTIF_ID = 5;
    public static final String NOTIF_CHANNEL_ID = "Notification Channel";

    private DatabaseHelper databaseHelper;

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private CharSequence drawerTitle;
    private CharSequence title;

    private final List<NavigationItem> navigationItems = new ArrayList<>();

    private boolean searchShowed = false, settingShowed = false, watchShowed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        showWatchedFragment();
        setupDrawer();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIF_CHANNEL_ID, "Nas Notif Kanal", importance);
            channel.setDescription("Our channel");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void setupDrawer() {
        setupDrawerNavigationItems();
        title = drawerTitle = getTitle();
        setupDrawerItems();
        setupToolbar();
    }

    private void setupDrawerNavigationItems() {
        navigationItems.add(new NavigationItem("Watched Movies","See all movies", R.drawable.ic_baseline_list_alt_24));
        navigationItems.add(new NavigationItem("Settings", "Configure Application", R.drawable.ic_baseline_settings_applications_24));
    }

    private void setupDrawerItems() {
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerList = findViewById(R.id.leftDrawer);

        DrawerListViewAdapter adapter = new DrawerListViewAdapter(navigationItems, this);
        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    showWatchedFragment();
                    break;
                case 1:
                    showSettingsFragment();
                    break;
            }
            drawerLayout.closeDrawer(drawerList);
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
            actionBar.setHomeButtonEnabled(true);
            actionBar.show();
        }

        new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(title);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu();
            }
        };
    }

    private void showSearchFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        SearchFragment fragment = new SearchFragment();
        transaction.replace(R.id.root, fragment);
        transaction.commit();

        searchShowed = true;
        settingShowed = false;
        watchShowed = false;
    }

    private void showSettingsFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        SettingsFragment fragment = new SettingsFragment();
        transaction.replace(R.id.root, fragment);
        transaction.commit();

        searchShowed = false;
        settingShowed = true;
        watchShowed = false;

    }

    private void showWatchedFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        WatchedFragment fragment = new WatchedFragment();
        transaction.replace(R.id.root, fragment);
        transaction.commit();

        searchShowed = false;
        settingShowed = false;
        watchShowed = true;


    }



        public void onBackPressed() {
            if (searchShowed) {
                finish();
            } else if (watchShowed) {
                getSupportFragmentManager().popBackStack();
                showWatchedFragment();
            } else if (settingShowed) {
                getSupportFragmentManager().popBackStack();
                showWatchedFragment();
            }
        }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    private void showNotification(String text) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIF_CHANNEL_ID);
        builder.setContentTitle(getString(R.string.app_name))
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_launcher_foreground);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIF_ID, builder.build());
    }
    @Override
    public void onaddClicked() {
        showSearchFragment();
    }

    @Override
    public void showTechnicalDetails(Movie movie) {

    }

    @Override
    public void showDetails(Movie movie) {

    }

    @Override
    public void onListItemClicked(Movie movie) {
        try {
            List<Movie> movies = getDatabaseHelper().getMovieDao().queryForAll();

            if (movies.size() > 0) {
                if (!movies.contains(movie)) {
                    getDatabaseHelper().getMovieDao().create(movie);

                    showNotification(movie.getTitle() + " added to base");

                    showWatchedFragment();
                }else {
                    showNotification(movie.getTitle() + " already in base");
                }
            } else {
                getDatabaseHelper().getMovieDao().create(movie);
                showNotification(movie.getTitle() + " added to base");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        showWatchedFragment();
    }
}