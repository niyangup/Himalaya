package com.niyangup.himalaya.presenters;

import android.util.Log;

import com.niyangup.himalaya.base.BaseApplication;
import com.niyangup.himalaya.interfaces.IPlayerPresenter;
import com.niyangup.himalaya.interfaces.IPlayerViewCallback;
import com.niyangup.himalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.advertis.Advertis;
import com.ximalaya.ting.android.opensdk.model.advertis.AdvertisList;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.advertis.IXmAdsStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.IXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;

import java.util.ArrayList;
import java.util.List;

public class PlayerPresenterImpl implements IPlayerPresenter, IXmAdsStatusListener, IXmPlayerStatusListener {

    private static final String TAG = "PlayerPresenterImpl";
    private final XmPlayerManager mPlayerManager;
    private Track mTrack;
    private int mCurrentIndex = 0;

    private PlayerPresenterImpl() {
        mPlayerManager = XmPlayerManager.getInstance(BaseApplication.Companion.getContext());
        mPlayerManager.addAdsStatusListener(this);

        mPlayerManager.addPlayerStatusListener(this);
    }

    private static PlayerPresenterImpl instance = null;

    public static PlayerPresenterImpl getInstance() {
        if (instance == null) {
            synchronized (PlayerPresenterImpl.class) {
                if (instance == null) {
                    instance = new PlayerPresenterImpl();
                }
            }
        }
        return instance;
    }

    private final List<IPlayerViewCallback> mCallbackList = new ArrayList<>();

    public void setPlayList(List<Track> tracks, int currentIndex) {
        if (tracks != null && tracks.size() > 0 && mPlayerManager != null) {
            mPlayerManager.setPlayList(tracks, currentIndex);
            Track track = tracks.get(currentIndex);
            mTrack = track;
            mCurrentIndex = currentIndex;
        } else {
            LogUtil.e(TAG, "tracks is null || mPlayerManager is null");
        }
    }

    @Override
    public void player() {
        mPlayerManager.play();
    }

    @Override
    public void pause() {
        mPlayerManager.pause();
    }

    @Override
    public void stop() {

    }

    @Override
    public void release() {
        mPlayerManager.stop();
    }

    @Override
    public void playerPre() {
        if (mPlayerManager != null) {
            mPlayerManager.playPre();
        }

    }

    @Override
    public void playerNext() {
        if (mPlayerManager != null) {
            mPlayerManager.playNext();
        }
    }

    @Override
    public int getCurrentIndex() {
        Log.d(TAG, "getCurrentIndex: " + mCurrentIndex);
        return this.mCurrentIndex;
    }

    @Override
    public void switchPlayModel(XmPlayListControl.PlayMode mode) {
        Log.d(TAG, "switchPlayModel: " + mode.name());
    }

    @Override
    public void getPlayList() {
        List<Track> playList = mPlayerManager.getPlayList();
        int position = mPlayerManager.getCurrentIndex();
        Log.d(TAG, "getPlayList: " + position);
        mCallbackList.forEach(callback -> {
            callback.onListLoaded(playList);
            callback.onTrackUpdate(playList.get(position));
        });
    }

    @Override
    public void playerByIndex(int index) {
        mPlayerManager.play(index);
    }

    @Override
    public void seekTo(int progress) {
        mPlayerManager.seekTo(progress);
    }

    @Override
    public boolean isPlaying() {
        return mPlayerManager.isPlaying();
    }

    @Override
    public void registerViewCallback(IPlayerViewCallback callback) {
        if (!mCallbackList.contains(callback)) {
            mCallbackList.add(callback);
        }
    }

    @Override
    public void unRegisterViewCallback(IPlayerViewCallback callback) {
        if (callback != null) {
            mCallbackList.remove(callback);
        }
    }

    /**
     * 广告相关回调
     */
    @Override
    public void onStartGetAdsInfo() {

    }

    @Override
    public void onGetAdsInfo(AdvertisList advertisList) {

    }

    @Override
    public void onAdsStartBuffering() {

    }

    @Override
    public void onAdsStopBuffering() {

    }

    @Override
    public void onStartPlayAds(Advertis advertis, int i) {

    }

    @Override
    public void onCompletePlayAds() {

    }

    @Override
    public void onError(int i, int i1) {

    }

    /**
     * 广告相关回调结束
     */

    @Override
    public void onPlayStart() {

    }

    @Override
    public void onPlayPause() {

    }

    @Override
    public void onPlayStop() {

    }

    @Override
    public void onSoundPlayComplete() {

    }

    @Override
    public void onSoundPrepared() {

    }

    @Override
    public void onSoundSwitch(PlayableModel lastModel, PlayableModel currentModel) {
        Log.d(TAG, "onSoundSwitch: ");
        if (currentModel instanceof Track) {
            Track track = (Track) currentModel;
            Log.d(TAG, "onSoundSwitch: " + track.getTrackTitle());

            mCallbackList.forEach(iPlayerViewCallback -> {
                iPlayerViewCallback.onTrackUpdate(track);
            });
        }

    }

    @Override
    public void onBufferingStart() {

    }

    @Override
    public void onBufferingStop() {

    }

    @Override
    public void onBufferProgress(int i) {

    }

    @Override
    public void onPlayProgress(int current, int total) {
        mCallbackList.forEach(callback -> {
            callback.onProgressChange(current, total);
        });
    }

    @Override
    public boolean onError(XmPlayerException e) {
        return false;
    }

}
