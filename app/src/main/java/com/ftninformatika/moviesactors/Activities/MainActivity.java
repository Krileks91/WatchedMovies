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
import com.ftninformatika.moviesactors.Models.NavigationItem;
import com.ftninformatika.moviesactors.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private CharSequence drawerTitle;
    private CharSequence title;

    private final List<NavigationItem> navigationItems = new ArrayList<>();

    private boolean showedSearch = false, showedDetails = false,
            showedSettings = false;     private boolean showerWatched = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupDrawer();

        showSearchFragment();
    }

    private void setupDrawer() {
        setupDrawerNavigationItems();
        title = drawerTitle = getTitle();
        setupDrawerItems();
        setupToolbar();
    }

    private void setupDrawerNavigationItems() {
        navigationItems.add(new NavigationItem("Watched Movies", "Go To collection", R.drawable.favorites_icon));
        navigationItems.add(new NavigationItem("Settings", "Application settings", R.drawable.settings_icon));
    }

    private void setupDrawerItems() {
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerList = findViewById(R.id.leftDrawer);

        DrawerListViewAdapter adapter = new DrawerListViewAdapter(navigationItems, this);
        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    showSearchFragment();
                    break;
                case 1:
                    showWatchedFragment();
                    break;
                case 2:
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
            actionBar.setHomeAsUpIndicator(R.drawable.menu_icon);
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

        showedSearch = true;
        showerWatched = false;
        showedSettings = false;
    }

    private void showWatchedFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        WatchedFragment fragment = new WatchedFragment();
        transaction.replace(R.id.root, fragment);
        transaction.commit();

        showedSearch = false;
        showerWatched = true;
        showedSettings = false;
    }

    private void showSettingsFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        SettingsFragment fragment = new SettingsFragment();
        transaction.replace(R.id.root, fragment);
        transaction.commit();

        showedSearch = false;
        showerWatched = false;
        showedSettings = true;
    }

    @Override
    public void onBackPressed() {
        if (showedSearch) {
            finish();
        } else if (showerWatched) {
            getSupportFragmentManager().popBackStack();
            showSearchFragment();
        } else if (showedSettings) {
            getSupportFragmentManager().popBackStack();
            showSearchFragment();
        }
    }

}