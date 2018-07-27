package com.zeus.multiuseapp.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.zeus.multiuseapp.R;
import com.zeus.multiuseapp.common.SettingsFragment;
import com.zeus.multiuseapp.drawing.DrawingActivity;
import com.zeus.multiuseapp.movie.MovieActivity;
import com.zeus.multiuseapp.notepad.NotepadActivity;
import com.zeus.multiuseapp.todo.ToDoActivity;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private static ReminderActivity inst;
    private TextView alarmTextView;

    public static ReminderActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    ToggleButton alarmToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        mToolbar  = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
        alarmTextView = (TextView) findViewById(R.id.alarmText);
         alarmToggle = (ToggleButton) findViewById(R.id.alarmToggle);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((ToggleButton) v).isChecked()) {
                    Log.d("MyActivity", "Alarm On");
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
                    calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
                    Intent myIntent = new Intent(ReminderActivity.this, AlarmReceiver.class);
                    pendingIntent = PendingIntent.getBroadcast(ReminderActivity.this, 0, myIntent, 0);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),10000, pendingIntent);

                } else {

                    alarmManager.cancel(pendingIntent);
                    setAlarmText("");
                    Log.d("MyActivity", "Alarm Off");
                }

            }

        });

        FloatingActionButton fab=(FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        DrawerLayout drawer =(DrawerLayout) findViewById(R.id.drawer_layout_rem);
        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,drawer,mToolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView=(NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void onToggleClicked(View view) {
        if (((ToggleButton) view).isChecked()) {
            Log.d("MyActivity", "Alarm On");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
            Intent myIntent = new Intent(ReminderActivity.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(ReminderActivity.this, 0, myIntent, 0);
            alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
            
        } else {
            alarmManager.cancel(pendingIntent);
            setAlarmText("");
            Log.d("MyActivity", "Alarm Off");
        }
    }

    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }


    private void openFragment(Fragment fragment, String screenTitle) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container_rem, fragment)
                .addToBackStack(null)
                .commit();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(screenTitle);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
                alarmToggle.setVisibility(View.INVISIBLE);
        switch (id) {

            case R.id.nav_note:
                Intent a = new Intent(ReminderActivity.this, NotepadActivity.class);
                startActivity(a);
                overridePendingTransition(R.animator.scaletocorner,R.animator.rotation);
                finish();
                break;
            case R.id.nav_drawing:
                Intent b = new Intent(ReminderActivity.this, DrawingActivity.class);
                startActivity(b);
                overridePendingTransition(R.animator.scaletocorner,R.animator.rotation);
                finish();
                break;
            case R.id.nav_movie:
                Intent c = new Intent(ReminderActivity.this, MovieActivity.class);
                startActivity(c);
                overridePendingTransition(R.animator.scaletocorner,R.animator.rotation);
                finish();
                break;

            case R.id.nav_reminder:
                Intent d = new Intent(ReminderActivity.this, ReminderActivity.class);
                startActivity(d);
                overridePendingTransition(R.animator.scaletocorner,R.animator.rotation);
                finish();
                break;

            case R.id.nav_todo:
                Intent e = new Intent(ReminderActivity.this, ToDoActivity.class);
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
