package com.niyangup.himalaya;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.niyangup.himalaya.base.BaseActivity;
import com.niyangup.himalaya.interfaces.IDetailViewCallback;
import com.niyangup.himalaya.presenters.DetailPresenterImpl;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;


public class DetailActivity extends BaseActivity implements IDetailViewCallback {

    ImageView mLargeCover;
    ImageView mSmallCover;
    TextView mTitle;
    TextView mSubtitle;
    DetailPresenterImpl detailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
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

    }

    @Override
    public void onDetailListLoaded(List<Track> tracks) {

    }

    @Override
    public void onAlbumLoaded(Album album) {
        mTitle.setText(album.getAlbumTitle());
        mSubtitle.setText(album.getAnnouncer().getNickname());
        Picasso.with(this).load(album.getCoverUrlLarge()).into(mLargeCover);
        Picasso.with(this).load(album.getCoverUrlLarge()).into(mSmallCover);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detailPresenter.unRegisterViewCallback(this);
    }
}