package com.niyangup.himalaya.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.niyangup.himalaya.R;
import com.niyangup.himalaya.adapter.RecommendListAdapter;
import com.niyangup.himalaya.base.BaseFragment;
import com.niyangup.himalaya.utils.Constants;
import com.niyangup.himalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendFragment extends BaseFragment {

    View mRootView;
    RecyclerView mRv;
    RecommendListAdapter mAdapter;
    private static final String TAG = "RecommendFragment";

    @Override
    public View onSubViewLoaded(LayoutInflater inflater, ViewGroup container) {
        mRootView = inflater.inflate(R.layout.fragment_recommend, container, false);
        getRecommendData();
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRv = mRootView.findViewById(R.id.recommend_rv);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(manager);
        mRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = UIUtil.dip2px(view.getContext(), 5);
                outRect.bottom = UIUtil.dip2px(view.getContext(), 5);
                outRect.left = UIUtil.dip2px(view.getContext(), 5);
                outRect.right = UIUtil.dip2px(view.getContext(), 5);
            }
        });

        mAdapter = new RecommendListAdapter();
        mRv.setAdapter(mAdapter);
    }

    private void getRecommendData() {
        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.LIKE_COUNT, String.valueOf(Constants.RECOMMEND_COUNT));
        CommonRequest.getGuessLikeAlbum(map, new IDataCallBack<GussLikeAlbumList>() {
            @Override
            public void onSuccess(@Nullable GussLikeAlbumList gussLikeAlbumList) {
                if (gussLikeAlbumList != null && !gussLikeAlbumList.getAlbumList().isEmpty()) {
                    LogUtil.d(TAG, "onSuccress:" + gussLikeAlbumList.getAlbumList().size());
                    updateRecommendUI(gussLikeAlbumList.getAlbumList());
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

    private void updateRecommendUI(List<Album> albumList) {
        mAdapter.setData(albumList);
    }
}