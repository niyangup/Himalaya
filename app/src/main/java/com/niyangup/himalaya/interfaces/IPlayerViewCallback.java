package com.niyangup.himalaya.interfaces;

import android.os.Trace;

import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.util.List;

public interface IPlayerViewCallback {

    void onPlayStart();

    void onPlayerPause();

    void onPlayerStop();

    void onPlayerError();

    void onNextPlayer(Trace trace);

    void onPrePlayer(Trace trace);


    void onListLoaded(List<Track> traces);

    /**
     * 播放器模式改变
     *
     * @param mode
     */
    void onPlayModeChange(XmPlayListControl.PlayMode mode);

    void onProgressChange(int currentProgress, int totalProgress);

    void onAdLoading();

    void onAdFinished();

    void onTrackUpdate(Track track);
}
