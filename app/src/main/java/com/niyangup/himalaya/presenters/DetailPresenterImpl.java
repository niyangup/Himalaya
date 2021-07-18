package com.niyangup.himalaya.presenters;

import com.niyangup.himalaya.interfaces.IDetailPresenter;
import com.niyangup.himalaya.interfaces.IDetailViewCallback;
import com.niyangup.himalaya.interfaces.IRecommendViewCallback;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.List;

public class DetailPresenterImpl implements IDetailPresenter {

    private final List<IDetailViewCallback> callbacks = new ArrayList<>();

    private static final String TAG = "DetailPresenterImpl";
    private Album mAlbum = null;

    private DetailPresenterImpl() {
    }

    private static DetailPresenterImpl instance = null;

    public static DetailPresenterImpl getInstance() {
        if (instance == null) {
            synchronized (RecommendPresenterImpl.class) {
                if (instance == null) {
                    instance = new DetailPresenterImpl();
                }
            }
        }
        return instance;
    }

    private final List<IRecommendViewCallback> callbackList = new ArrayList<>();

    @Override
    public void getAlbumDetail(int albumId, int page) {

    }

    @Override
    public void pullToRefresh() {

    }

    @Override
    public void loadMore() {


    }

    @Override
    public void registerViewCallback(IDetailViewCallback callback) {
        if (callback != null && !callbackList.contains(callback)) {
            callbacks.add(callback);
        }
        if (callback != null && mAlbum != null) {
            callback.onAlbumLoaded(mAlbum);
        }
    }

    @Override
    public void unRegisterViewCallback(IDetailViewCallback callback) {
        if (callback != null) {
            callbacks.remove(callback);
        }
    }

    public void setTargetAlbum(Album album) {
        this.mAlbum = album;
    }
}
