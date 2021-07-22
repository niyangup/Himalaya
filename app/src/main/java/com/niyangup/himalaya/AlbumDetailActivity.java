package com.niyangup.himalaya;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.niyangup.himalaya.adapter.AlbumDetailListAdapter;
import com.niyangup.himalaya.base.BaseActivity;
import com.niyangup.himalaya.interfaces.IDetailViewCallback;
import com.niyangup.himalaya.presenters.DetailPresenterImpl;
import com.niyangup.himalaya.utils.ImageBlur;
import com.niyangup.himalaya.utils.LogUtil;
import com.niyangup.himalaya.view.UILoader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;


public class AlbumDetailActivity extends BaseActivity implements IDetailViewCallback {

    private static final String TAG = "DetailActivity";
    ImageView mLargeCover;
    ImageView mSmallCover;
    TextView mTitle;
    TextView mSubtitle;
    DetailPresenterImpl detailPresenter;
    RecyclerView mRvAlbum;

    UILoader mUiLoader;
    private int mCurrentId = -1;
    AlbumDetailListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        initView();
        detailPresenter = DetailPresenterImpl.getInstance();
        detailPresenter.registerViewCallback(this);
    }

    private void initView() {
        mLargeCover = this.findViewById(R.id.iv_large_cover);
        mSmallCover = this.findViewById(R.id.iv_small_cover);
        mTitle = this.findViewById(R.id.tv_title);
        mSubtitle = this.findViewById(R.id.tv_subtitle);
        FrameLayout container = this.findViewById(R.id.fl_container);
        if (mUiLoader == null) {
            mUiLoader = new UILoader(this) {
                @Override
                public View getSuccessView(ViewGroup viewGroup) {
                    return createSuccessView(viewGroup);
                }

                @Override
                public void onRetry() {
                    detailPresenter.getAlbumDetail(mCurrentId, 1);
                }
            };
        }

        container.removeAllViews();
        container.addView(mUiLoader);
    }

    private View createSuccessView(ViewGroup viewGroup) {
        View view = View.inflate(this, R.layout.item_detail_list, null);
        mRvAlbum = view.findViewById(R.id.rv_album);
        mRvAlbum.setLayoutManager(new LinearLayoutManager(this));

        mRvAlbum.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = UIUtil.dip2px(view.getContext(), 5);
                outRect.bottom = UIUtil.dip2px(view.getContext(), 5);
                outRect.left = UIUtil.dip2px(view.getContext(), 5);
                outRect.right = UIUtil.dip2px(view.getContext(), 5);
            }
        });

        mAdapter = new AlbumDetailListAdapter();
        mRvAlbum.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onDetailListLoaded(List<Track> tracks) {
        Log.d(TAG, "onDetailListLoaded: " + tracks.size());
        if (tracks == null || tracks.size() == 0) {
            if (mUiLoader != null) {
                mUiLoader.updateState(UILoader.UIStatus.EMPTY);
            }
        } else {
            if (mUiLoader != null) {
                mUiLoader.updateState(UILoader.UIStatus.SUCCESS);
            }
            handleListView(tracks);
        }
    }

    private void handleListView(List<Track> tracks) {
        mAdapter.setData(tracks);
    }

    @Override
    public void onAlbumLoaded(Album album) {

        if (mUiLoader != null) {
            mUiLoader.updateState(UILoader.UIStatus.LOADING);
        }
        mCurrentId = (int) album.getId();

        detailPresenter.getAlbumDetail(mCurrentId, 1);

        mTitle.setText(album.getAlbumTitle());
        mSubtitle.setText(album.getAnnouncer().getNickname());

        if (mLargeCover != null) {
            Picasso.with(this).load(album.getCoverUrlLarge()).into(mLargeCover, new Callback() {
                @Override
                public void onSuccess() {
                    Drawable drawable = mLargeCover.getDrawable();
                    if (drawable != null) {
                        //新增图片模糊效果
                        ImageBlur.makeBlur(mLargeCover, AlbumDetailActivity.this);
                    }
                }

                @Override
                public void onError() {
                    LogUtil.e(TAG, "onError");
                }
            });

        }

        Picasso.with(this).load(album.getCoverUrlLarge()).into(mSmallCover);
    }

    @Override
    public void onNetworkError(int i, String s) {
        mUiLoader.updateState(UILoader.UIStatus.NETWORK_ERROR);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detailPresenter.unRegisterViewCallback(this);
    }
}