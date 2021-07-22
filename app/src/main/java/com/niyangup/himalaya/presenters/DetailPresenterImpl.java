package com.niyangup.himalaya.presenters;

import android.util.Log;

import androidx.annotation.Nullable;

import com.niyangup.himalaya.interfaces.IDetailPresenter;
import com.niyangup.himalaya.interfaces.IDetailViewCallback;
import com.niyangup.himalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailPresenterImpl implements IDetailPresenter {

    private final List<IDetailViewCallback> callbacks = new ArrayList<>();

    private static final String TAG = "DetailPresenterImpl";
    private Album mAlbum = null;
    private final List<Track> mTracks = new ArrayList<>();

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

    @Override
    public void getAlbumDetail(int albumId, int page) {
        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.ALBUM_ID, "" + albumId);
        map.put(DTransferConstants.SORT, "asc");
        map.put(DTransferConstants.PAGE, "" + page);
        CommonRequest.getTracks(map, new IDataCallBack<TrackList>() {
            @Override
            public void onSuccess(@Nullable TrackList trackList) {
                Log.d(TAG, "onSuccess: " + trackList.getTracks());
                if (trackList != null) {
                    List<Track> tracks = trackList.getTracks();
                    LogUtil.d(TAG, "tracks size -- > " + tracks.size());
                    mTracks.addAll(0, tracks);
                    handlerAlbumDetailResult(mTracks);
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "onError: " + s);
                handleNetworkError(i,s);
            }
        });
    }

    private void handleNetworkError(int i, String s) {
        callbacks.forEach(iDetailViewCallback -> {
            iDetailViewCallback.onNetworkError(i,s);
        });
    }

    private void handlerAlbumDetailResult(List<Track> mTracks) {
        callbacks.forEach(iDetailViewCallback -> {
            iDetailViewCallback.onDetailListLoaded(mTracks);
        });
    }

    @Override
    public void pullToRefresh() {

    }

    @Override
    public void loadMore() {


    }

    @Override
    public void registerViewCallback(IDetailViewCallback callback) {
        if (callback != null && !callbacks.contains(callback)) {
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
