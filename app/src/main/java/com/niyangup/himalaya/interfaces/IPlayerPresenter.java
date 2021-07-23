package com.niyangup.himalaya.interfaces;

import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

public interface IPlayerPresenter extends IBasePresenter<IPlayerViewCallback> {


    void player();

    void pause();

    void stop();

    void release();

    void playerPre();

    void playerNext();

    void switchPlayModel(XmPlayListControl.PlayMode mode);

    void getPlayList();

    void playerByIndex(int index);

    void seekTo(int progress);

    boolean isPlaying();


}
