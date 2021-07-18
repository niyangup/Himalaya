package com.niyangup.himalaya.interfaces;

public interface IDetailPresenter {
    void getAlbumDetail(int albumId, int page);

    /**
     * 下拉刷新
     */
    void pullToRefresh();

    /**
     * 上拉加载更多
     */
    void loadMore();


    void registerViewCallback(IDetailViewCallback callback);

    void unRegisterViewCallback(IDetailViewCallback callback);
}
