package com.zeus.multiuseapp.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.zeus.multiuseapp.R;
import com.zeus.multiuseapp.common.SettingsFragment;
import com.zeus.multiuseapp.drawing.DrawingActivity;
import com.zeus.multiuseapp.movie.MovieActivity;
import com.zeus.multiuseapp.notepad.NotepadActivity;
import com.zeus.multiuseapp.reminder.ReminderActivity;


public class ToDoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        mToolbar  = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        DrawerLayout drawer =(DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,drawer,mToolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView=(NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (getSupportActionBar()!=null) {
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
        if(savedInstanceState==null) {
            openFragment(new ToDoListFragment(), "List of Todo");
        }
    }



    private void openFragment(Fragment fragment, String screenTitle) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(screenTitle);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.nav_note:
                Intent a = new Intent(ToDoActivity.this, NotepadActivity.class);
                startActivity(a);
                overridePendingTransition(R.animator.scaletocorner,R.animator.rotation);
                finish();
                break;
            case R.id.nav_drawing:
                Intent b = new Intent(ToDoActivity.this, DrawingActivity.class);
                startActivity(b);
                overridePendingTransition(R.animator.scaletocorner,R.animator.rotation);
                finish();
                break;
            case R.id.nav_movie:
                Intent c = new Intent(ToDoActivity.this, MovieActivity.class);
                startActivity(c);
                overridePendingTransition(R.animator.scaletocorner,R.animator.rotation);
                finish();
                break;

            case R.id.nav_reminder:
                Intent d = new Intent(ToDoActivity.this, ReminderActivity.class);
                startActivity(d);
                overridePendingTransition(R.animator.scaletocorner,R.animator.rotation);
                finish();
                break;

            case R.id.nav_todo:
                Intent e = new Intent(ToDoActivity.this, ToDoActivity.class);
                startActivity(e);
                overridePendingTransition(R.animator.scaletocorner,R.animator.rotation);
                finish();
                break;

            case R.id.nav_settings:
                openFragment(new SettingsFragment(), getString(R.string.settings));
                break;
            default:
                break;

        }
        return false;
    }
}
