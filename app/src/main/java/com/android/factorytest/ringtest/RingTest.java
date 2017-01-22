package com.android.factorytest.ringtest;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import com.android.factorytest.BaseActivity;
import com.android.factorytest.Log;
import com.android.factorytest.R;

import java.io.IOException;

public class RingTest extends BaseActivity {

    private TextView mRingFilePathTv;
    private TextView mPlayTimeTv;
    private MediaPlayer mMediaPlayer;

    private String mRingFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ring_test);
        setTitle(R.string.ring_test_title);
        mRingFilePath = "/system/media/Test.ogg";

        mRingFilePathTv = (TextView) findViewById(R.id.ring_file_Path);
        mPlayTimeTv = (TextView) findViewById(R.id.play_time);
        mRingFilePathTv.setText(getString(R.string.ring_tip_title, mRingFilePath));
    }

    @Override
    protected void onStart() {
        super.onStart();
        playRing();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopRing();
    }

    private void playRing() {
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(mRingFilePath);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            Log.e(this, "playRing=>error: ", e);
            mMediaPlayer = null;
        }
    }

    private void stopRing() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
