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

public class RecommendPresenter implements IRecommendPresenter {

    private static final String TAG = "RecommendPresenter";

    private RecommendPresenter() {
    }

    private static RecommendPresenter instance = null;

    public static RecommendPresenter getInstance() {
        if (instance == null) {
            synchronized (RecommendPresenter.class) {
                if (instance == null) {
                    instance = new RecommendPresenter();
                }
            }
        }
        return instance;
    }

    private List<IRecommendViewCallback> callbackList = new ArrayList<>();

    @Override
    public void getRecommendList() {
        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.LIKE_COUNT, String.valueOf(Constants.RECOMMEND_COUNT));
        CommonRequest.getGuessLikeAlbum(map, new IDataCallBack<GussLikeAlbumList>() {
            @Override
            public void onSuccess(@Nullable GussLikeAlbumList gussLikeAlbumList) {
                if (gussLikeAlbumList != null && !gussLikeAlbumList.getAlbumList().isEmpty()) {
                    LogUtil.d(TAG, "onSuccress:" + gussLikeAlbumList.getAlbumList().size());
                    //updateRecommendUI(gussLikeAlbumList.getAlbumList());
                    handleRecommendResult(gussLikeAlbumList.getAlbumList());
                } else {
                    LogUtil.d(TAG, "isEmpty");
                }

            }

            @Override
            public void onError(int i, String s) {
                LogUtil.d(TAG, "error: " + s);

            }
        });
    }

    private void handleRecommendResult(List<Album> albumList) {
        //通知Ui更新
        if (callbackList != null) {
            callbackList.forEach(callback -> {
                callback.onRecommendListLoaded(albumList);
            });

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
