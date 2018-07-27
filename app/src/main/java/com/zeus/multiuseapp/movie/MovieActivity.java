package com.zeus.multiuseapp.movie;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.VideoView;

import com.zeus.multiuseapp.R;
import com.zeus.multiuseapp.common.SettingsFragment;
import com.zeus.multiuseapp.drawing.DrawingActivity;
import com.zeus.multiuseapp.notepad.NotepadActivity;
import com.zeus.multiuseapp.reminder.ReminderActivity;
import com.zeus.multiuseapp.todo.ToDoActivity;

public class MovieActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    FloatingActionButton mSearch, mLandscape, mPortrait, mAdd,mDefault;
    EditText mEditText;
    VideoView mVideoView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        mToolbar  = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mSearch = (FloatingActionButton) findViewById(R.id.btnSearch);
        mPortrait = (FloatingActionButton) findViewById(R.id.btnportrait);
        mLandscape = (FloatingActionButton) findViewById(R.id.btnlandscape);
        mAdd = (FloatingActionButton) findViewById(R.id.btnadd);
        mDefault=(FloatingActionButton) findViewById(R.id.defaultVideo);
        mEditText = (EditText) findViewById(R.id.edittextVideo);
        mVideoView = (VideoView) findViewById(R.id.videoView);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri urlData = Uri.parse(mEditText.getText().toString());
                MediaController mediaController = new MediaController(MovieActivity.this);
                mediaController.setAnchorView(mVideoView);
                mVideoView.setMediaController(mediaController);
                if (urlData != null) {
                    mVideoView.setVideoURI(urlData);
                    mVideoView.start();
                } else {
                    Snackbar.make(v, "URL is empty", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        mLandscape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        });
        mPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        });

        mDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final VideoView videoView=(VideoView)findViewById(R.id.videoView);
                MediaController mediaController=new MediaController(MovieActivity.this);
                mediaController.setAnchorView(videoView);
                Uri uri=Uri.parse("android.resource://"+ getPackageName()+"/"+R.raw.waheguru);
                if (videoView!=null) {
                    videoView.setMediaController(mediaController);
                    videoView.setVideoURI(uri);
                    videoView.requestFocus();
                    videoView.start();
                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            videoView.seekTo(0);
                            videoView.start();
                        }
                    });
                }
            }
        });

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("video/*");
                startActivityForResult(intent, 1);

            }
        });

      /*  FloatingActionButton fab=(FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Replace with your own action",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            }
        });*/



        DrawerLayout drawer =(DrawerLayout) findViewById(R.id.drawer_layout_mov);
        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,drawer,mToolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView=(NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void openFragment(Fragment fragment, String screenTitle) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container_mov, fragment)
                .addToBackStack(null)
                .commit();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(screenTitle);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 1 && resultCode == Activity.RESULT_OK)
        {
            try
            {
                String path = data.getData().toString();
                mVideoView.setVideoPath(path);
                mVideoView.requestFocus();
                mVideoView.start();

            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.nav_note:
                Intent a = new Intent(MovieActivity.this, NotepadActivity.class);
                startActivity(a);
                overridePendingTransition(R.animator.scaletocorner,R.animator.rotation);
                finish();
                break;
            case R.id.nav_drawing:
                Intent b = new Intent(MovieActivity.this, DrawingActivity.class);
                startActivity(b);
                overridePendingTransition(R.animator.scaletocorner,R.animator.rotation);
                finish();
                break;
            case R.id.nav_movie:
                Intent c = new Intent(MovieActivity.this, MovieActivity.class);
                startActivity(c);
                overridePendingTransition(R.animator.scaletocorner,R.animator.rotation);
                finish();
                break;

            case R.id.nav_reminder:
                Intent d = new Intent(MovieActivity.this, ReminderActivity.class);
                startActivity(d);
                overridePendingTransition(R.animator.scaletocorner, R.animator.rotation);
                finish();
                break;

            case R.id.nav_todo:
                Intent e = new Intent(MovieActivity.this, ToDoActivity.class);
                startActivity(e);
                overridePendingTransition(R.animator.scaletocorner,R.animator.rotation);
                finish();
                break;
            case R.id.nav_settings:
                openFragment(new SettingsFragment(), getString(R.string.settings));
                overridePendingTransition(R.animator.scaletocorner, R.animator.rotation);
                finish();
                break;

            default:
                break;
        }
        return false;
    }
}
