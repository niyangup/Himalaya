package com.niyangup.himalaya.presenters;

import androidx.annotation.Nullable;

import com.niyangup.himalaya.interfaces.IRecommendPresenter;
import com.niyangup.himalaya.interfaces.IRecommendViewCallback;
import com.niyangup.himalaya.utils.Constants;
import com.niyangup.himalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendPresenterImpl implements IRecommendPresenter {

    private static final String TAG = "RecommendPresenter";

    private RecommendPresenterImpl() {
    }

    private static RecommendPresenterImpl instance = null;

    public static RecommendPresenterImpl getInstance() {
        if (instance == null) {
            synchronized (RecommendPresenterImpl.class) {
                if (instance == null) {
                    instance = new RecommendPresenterImpl();
                }
            }
        }
        return instance;
    }

    private final List<IRecommendViewCallback> callbackList = new ArrayList<>();

    @Override
    public void getRecommendList() {
        updateLoading();
        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.LIKE_COUNT, String.valueOf(Constants.RECOMMEND_COUNT));
        CommonRequest.getGuessLikeAlbum(map, new IDataCallBack<GussLikeAlbumList>() {
            @Override
            public void onSuccess(@Nullable GussLikeAlbumList gussLikeAlbumList) {
                if (gussLikeAlbumList != null && !gussLikeAlbumList.getAlbumList().isEmpty()) {
                    LogUtil.d(TAG, "onSuccress:" + gussLikeAlbumList.getAlbumList().size());
                    //updateRecommendUI(gussLikeAlbumList.getAlbumList());
                    if (gussLikeAlbumList.getAlbumList() == null || gussLikeAlbumList.getAlbumList().size() == 0) {
                        updateEmpty();
                    } else {
                        handleRecommendResult(gussLikeAlbumList.getAlbumList());
                    }
                } else {
                    LogUtil.d(TAG, "isEmpty");
                }
            }

            @Override
            public void onError(int i, String s) {
                LogUtil.d(TAG, "error: " + s);
                handleError();
            }
        });
    }

    private void updateEmpty() {
        callbackList.forEach(IRecommendViewCallback::onEmpty);
    }

    private void updateLoading() {
        callbackList.forEach(IRecommendViewCallback::onLoading);
    }

    private void handleError() {
        callbackList.forEach(IRecommendViewCallback::onNetworkError);
    }

    private void handleRecommendResult(List<Album> albumList) {
        if (albumList != null && albumList.size() > 0) {
            //通知Ui更新
            callbackList.forEach(callback -> {
                callback.onRecommendListLoaded(albumList);
            });
        } else {
            callbackList.forEach(IRecommendViewCallback::onEmpty);
        }
    }

    @Override
    public void pullToRefresh() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void registerViewCallback(IRecommendViewCallback callback) {
        if (callback != null && !callbackList.contains(callback)) {
            callbackList.add(callback);
        }
    }

    @Override
    public void unRegisterViewCallback(IRecommendViewCallback callback) {
        if (callback != null) {
            callbackList.remove(callback);
        }
    }
}
