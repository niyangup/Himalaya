package com.niyangup.himalaya;


import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.niyangup.himalaya.base.BaseActivity;
import com.niyangup.himalaya.interfaces.IPlayerPresenter;
import com.niyangup.himalaya.interfaces.IPlayerViewCallback;
import com.niyangup.himalaya.presenters.PlayerPresenterImpl;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PlayerActivity extends BaseActivity implements IPlayerViewCallback {
    private static final String TAG = "PlayerActivity";
    IPlayerPresenter mPlayerPresenter;
    ImageView mPlayOrPauseBtn;
    TextView mCurrentPosition, mTrackDuration;
    SeekBar mSeekBar;
    private final SimpleDateFormat mMinFormat = new SimpleDateFormat("MM:ss");
    private final SimpleDateFormat mHourFormat = new SimpleDateFormat("hh:ss:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        mPlayerPresenter = PlayerPresenterImpl.getInstance();
        mPlayerPresenter.registerViewCallback(this);

        initView();
        initEvent();

        startPlay();
    }

    private void startPlay() {
        if (mPlayerPresenter != null) {
            mPlayerPresenter.player();
        }
    }

    private void initEvent() {
        mPlayOrPauseBtn.setOnClickListener(v -> {
            if (mPlayerPresenter.isPlaying()) {
                mPlayOrPauseBtn.setImageResource(R.mipmap.play_press);
                mPlayerPresenter.pause();
            } else {
                mPlayOrPauseBtn.setImageResource(R.mipmap.stop_normal);
                mPlayerPresenter.player();
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "onProgressChanged: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initView() {
        mPlayOrPauseBtn = this.findViewById(R.id.play_or_pause_btn);
        mCurrentPosition = this.findViewById(R.id.current_position);
        mTrackDuration = this.findViewById(R.id.track_duration);
        mSeekBar = this.findViewById(R.id.track_seek_bar);
    }


    @Override
    public void onPlayStart() {

    }

    @Override
    public void onPlayerPause() {

    }

    @Override
    public void onPlayerStop() {

    }

    @Override
    public void onPlayerError() {

    }

    @Override
    public void onNextPlayer(Trace trace) {

    }

    @Override
    public void onPrePlayer(Trace trace) {

    }

    @Override
    public void onListLoaded(List<Trace> traces) {

    }

    @Override
    public void onPlayModeChange(XmPlayListControl.PlayMode mode) {

    }

    @Override
    public void onProgressChange(int currentProgress, int totalProgress) {
        Log.d(TAG, "onProgressChange: currentProgress:" + currentProgress + "totalProgress" + totalProgress);
        String current = mMinFormat.format(currentProgress);
        String total = mMinFormat.format(totalProgress);
        if (totalProgress > 1000 * 60 * 60) {
            current = mHourFormat.format(currentProgress);
            total = mHourFormat.format(totalProgress);
        }

        mCurrentPosition.setText(current);
        mTrackDuration.setText(total);

        int progress = (int) ((currentProgress * 1.0f / totalProgress) * 100);
        mSeekBar.setProgress(progress);
    }

    @Override
    public void onAdLoading() {

    }

    @Override
    public void onAdFinished() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayerPresenter.release();
        mPlayerPresenter.unRegisterViewCallback(this);
    }
}