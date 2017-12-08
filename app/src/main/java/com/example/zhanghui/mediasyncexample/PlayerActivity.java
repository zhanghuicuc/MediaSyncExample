package com.example.zhanghui.mediasyncexample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class PlayerActivity extends Activity implements SurfaceHolder.Callback{

    private static final String TAG = "PlayerActivity";
    private SurfaceView mSurfaceV;
    private SurfaceHolder mSurfaceHolder;
    private MediaSyncTest mMediaSyncTest;
    private Uri mFileUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_player);
        mSurfaceV = (SurfaceView) findViewById(R.id.surfaceView);
        mSurfaceV.getHolder().addCallback(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        mFileUrl = intent.getData();
        Intent i = new Intent("com.android.music.musicservicecommand");
        i.putExtra("command", "pause");
        this.sendBroadcast(i);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mMediaSyncTest != null) {
            try {
                mMediaSyncTest.tearDown();
                mMediaSyncTest = null;
            }catch (Exception e){}
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaSyncTest != null) {
            try {
                mMediaSyncTest.tearDown();
                mMediaSyncTest = null;
            }catch (Exception e){}
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.mSurfaceHolder = holder;
        this.mSurfaceHolder.setKeepScreenOn(true);
        new DecodeTask().execute();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mMediaSyncTest != null) {
            try {
                mMediaSyncTest.tearDown();
                mMediaSyncTest = null;
            }catch (Exception e){}
        }
    }

    public class DecodeTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            //this runs on a new thread
            initializePlayer();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            //this runs on ui thread
        }
    }

    private void initializePlayer() {
        mMediaSyncTest = new MediaSyncTest(mSurfaceHolder);
        if (mMediaSyncTest != null) {
            try {
                mMediaSyncTest.testPlayAudioAndVideo(mFileUrl);
            } catch (InterruptedException e){}
        }
    }
}
