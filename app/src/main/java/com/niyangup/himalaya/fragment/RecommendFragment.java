package com.niyangup.himalaya.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.niyangup.himalaya.DetailActivity;
import com.niyangup.himalaya.R;
import com.niyangup.himalaya.adapter.RecommendListAdapter;
import com.niyangup.himalaya.base.BaseFragment;
import com.niyangup.himalaya.interfaces.IDetailPresenter;
import com.niyangup.himalaya.interfaces.IRecommendPresenter;
import com.niyangup.himalaya.interfaces.IRecommendViewCallback;
import com.niyangup.himalaya.presenters.DetailPresenterImpl;
import com.niyangup.himalaya.presenters.RecommendPresenterImpl;
import com.niyangup.himalaya.view.UILoader;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

public class RecommendFragment extends BaseFragment implements IRecommendViewCallback, RecommendListAdapter.OnRecommendItemClickListener {

    View mRootView;
    RecyclerView mRv;
    RecommendListAdapter mAdapter;
    private UILoader mUILoader;
    private static final String TAG = "RecommendFragment";

    @Override
    public View onSubViewLoaded(LayoutInflater inflater, ViewGroup container) {
        IRecommendPresenter instance = RecommendPresenterImpl.getInstance();
        instance.registerViewCallback(this);
        mUILoader = new UILoader(getContext()) {
            @Override
            public View getSuccessView(ViewGroup viewGroup) {
                return createSuccessView(inflater, viewGroup);
            }

            @Override
            public void onRetry() {
                instance.getRecommendList();
            }
        };

        instance.getRecommendList();
        if (mUILoader.getParent() instanceof ViewGroup) {
            ((ViewGroup) mUILoader.getParent()).removeView(mUILoader);
        }
        return mUILoader;
    }

    private View createSuccessView(LayoutInflater inflater, ViewGroup container) {
        mRootView = inflater.inflate(R.layout.fragment_recommend, container, false);
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
        mAdapter.setOnRecommendItemClickListener(this);
        mRv.setAdapter(mAdapter);
        return mRootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RecommendPresenterImpl.getInstance().unRegisterViewCallback(this);
    }

    @Override
    public void onRecommendListLoaded(List<Album> result) {
        mAdapter.setData(result);
        mUILoader.updateState(UILoader.UIStatus.SUCCESS);
    }

    @Override
    public void onNetworkError() {
        mUILoader.updateState(UILoader.UIStatus.NETWORK_ERROR);
    }

    @Override
    public void onEmpty() {
        mUILoader.updateState(UILoader.UIStatus.EMPTY);
    }

    @Override
    public void onLoading() {
        mUILoader.updateState(UILoader.UIStatus.LOADING);
    }

    @Override
    public void onItemClick(int index, Album album) {
        DetailPresenterImpl.getInstance().setTargetAlbum(album);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        startActivity(intent);
    }
}