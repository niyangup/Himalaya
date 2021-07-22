package com.niyangup.himalaya;


import android.os.Bundle;
import android.os.Trace;

import com.niyangup.himalaya.base.BaseActivity;
import com.niyangup.himalaya.interfaces.IPlayerPresenter;
import com.niyangup.himalaya.interfaces.IPlayerViewCallback;
import com.niyangup.himalaya.presenters.PlayerPresenterImpl;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.util.List;

public class PlayerActivity extends BaseActivity implements IPlayerViewCallback {
    IPlayerPresenter mPlayerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        mPlayerPresenter = PlayerPresenterImpl.getInstance();
        mPlayerPresenter.registerViewCallback(this);
        mPlayerPresenter.player();
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
    public void onProgressChange(long currentProgress, long totalProgress) {

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