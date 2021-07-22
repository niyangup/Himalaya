package com.niyangup.himalaya.presenters;

import com.niyangup.himalaya.base.BaseApplication;
import com.niyangup.himalaya.interfaces.IPlayerPresenter;
import com.niyangup.himalaya.interfaces.IPlayerViewCallback;
import com.niyangup.himalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.model.advertis.Advertis;
import com.ximalaya.ting.android.opensdk.model.advertis.AdvertisList;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.advertis.IXmAdsStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import java.util.ArrayList;
import java.util.List;

public class PlayerPresenterImpl implements IPlayerPresenter, IXmAdsStatusListener {

    private static final String TAG = "PlayerPresenterImpl";
    private final XmPlayerManager mPlayerManager;

    private PlayerPresenterImpl() {
        mPlayerManager = XmPlayerManager.getInstance(BaseApplication.Companion.getContext());
        mPlayerManager.addAdsStatusListener(this);
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

    }

    @Override
    public void playerNext() {

    }

    @Override
    public void switchPlayModel(XmPlayListControl.PlayMode mode) {

    }

    @Override
    public void getPlayList() {

    }

    @Override
    public void playerByIndex(int index) {

    }

    @Override
    public void seekTo(int progress) {

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
}
