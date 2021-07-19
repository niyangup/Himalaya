package com.niyangup.himalaya;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.niyangup.himalaya.base.BaseActivity;
import com.niyangup.himalaya.interfaces.IDetailViewCallback;
import com.niyangup.himalaya.presenters.DetailPresenterImpl;
import com.niyangup.himalaya.utils.ImageBlur;
import com.niyangup.himalaya.utils.LogUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;


public class AlbumDetailActivity extends BaseActivity implements IDetailViewCallback {

    private static final String TAG = "DetailActivity";
    ImageView mLargeCover;
    ImageView mSmallCover;
    TextView mTitle;
    TextView mSubtitle;
    DetailPresenterImpl detailPresenter;
    RecyclerView mRvAlbum;

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
        mRvAlbum = this.findViewById(R.id.rv_album);
    }

    @Override
    public void onDetailListLoaded(List<Track> tracks) {
        Log.d(TAG, "onDetailListLoaded: " + tracks.size());
    }

    @Override
    public void onAlbumLoaded(Album album) {

        detailPresenter.getAlbumDetail((int) album.getId(), 1);

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
    protected void onDestroy() {
        super.onDestroy();
        detailPresenter.unRegisterViewCallback(this);
    }
}