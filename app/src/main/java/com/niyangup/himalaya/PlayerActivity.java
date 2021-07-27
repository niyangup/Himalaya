package com.niyangup.himalaya;


import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.niyangup.himalaya.adapter.PlayerTrackPageAdapter;
import com.niyangup.himalaya.base.BaseActivity;
import com.niyangup.himalaya.interfaces.IPlayerPresenter;
import com.niyangup.himalaya.interfaces.IPlayerViewCallback;
import com.niyangup.himalaya.presenters.PlayerPresenterImpl;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PlayerActivity extends BaseActivity implements IPlayerViewCallback, ViewPager.OnPageChangeListener {
    private static final String TAG = "PlayerActivity";
    private IPlayerPresenter mPlayerPresenter;
    private ImageView mPlayOrPauseBtn, mPlayModeSwitchBtn;
    private TextView mCurrentPosition, mTrackDuration, mTvTrackTitle;
    private SeekBar mSeekBar;
    private final SimpleDateFormat mMinFormat = new SimpleDateFormat("MM:ss");
    private final SimpleDateFormat mHourFormat = new SimpleDateFormat("hh:MM:ss");

    private int mCurrentProgress = 0;
    private boolean isUserTouch = false;
    private ImageView mPlayPre, mPlayNext;
    private PlayerTrackPageAdapter adapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        mPlayerPresenter = PlayerPresenterImpl.getInstance();
        initView();

        mPlayerPresenter.registerViewCallback(this);
        mPlayerPresenter.getPlayList();
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
                if (fromUser) {
                    mCurrentProgress = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isUserTouch = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isUserTouch = false;
                mPlayerPresenter.seekTo(mCurrentProgress);
            }
        });

        mPlayPre.setOnClickListener(v -> {
            if (mPlayerPresenter != null) {
                mPlayerPresenter.playerPre();
            }

        });
        mPlayNext.setOnClickListener(v -> {
            if (mPlayerPresenter != null) {
                mPlayerPresenter.playerNext();
            }
        });

        viewPager.addOnPageChangeListener(this);

        mPlayModeSwitchBtn.setOnClickListener(v -> {
            //TODO 修改播放模式
        });
    }

    private void initView() {
        mPlayOrPauseBtn = this.findViewById(R.id.play_or_pause_btn);
        mCurrentPosition = this.findViewById(R.id.current_position);
        mTrackDuration = this.findViewById(R.id.track_duration);
        mSeekBar = this.findViewById(R.id.track_seek_bar);
        mPlayPre = this.findViewById(R.id.play_pre);
        mPlayNext = this.findViewById(R.id.play_next);
        mTvTrackTitle = this.findViewById(R.id.track_title);
        viewPager = this.findViewById(R.id.track_pager_view);
        adapter = new PlayerTrackPageAdapter();
        Log.d(TAG, "initView: "+mPlayerPresenter.getCurrentIndex());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(mPlayerPresenter.getCurrentIndex());
        mPlayModeSwitchBtn = this.findViewById(R.id.player_mode_switch_btn);
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
    public void onListLoaded(List<Track> tracks) {
        adapter.setData(tracks);
    }

    @Override
    public void onPlayModeChange(XmPlayListControl.PlayMode mode) {

    }

    @Override
    public void onTrackUpdate(Track track) {
        if (mTvTrackTitle != null) {
            mTvTrackTitle.setText(track.getTrackTitle());
        }
    }

    @Override
    public void onProgressChange(int currentProgress, int totalProgress) {
        mSeekBar.setMax(totalProgress);
        String current = mMinFormat.format(currentProgress);
        String total = mMinFormat.format(totalProgress);
        if (totalProgress > 1000 * 60 * 60) {
            current = mHourFormat.format(currentProgress);
            total = mHourFormat.format(totalProgress);
        }

        mCurrentPosition.setText(current);
        mTrackDuration.setText(total);

        if (!isUserTouch) {
            mSeekBar.setProgress(currentProgress);
        }
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d(TAG, "onPageScrolled: ");
    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, "onPageSelected: ");
        if (mPlayerPresenter != null) {
            mPlayerPresenter.playerByIndex(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d(TAG, "onPageScrollStateChanged: ");
    }
}