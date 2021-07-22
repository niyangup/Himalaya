package com.niyangup.himalaya.interfaces;

public interface IDetailPresenter extends IBasePresenter<IDetailViewCallback> {
    void getAlbumDetail(int albumId, int page);

    /**
     * 下拉刷新
     */
    void pullToRefresh();

    /**
     * 上拉加载更多
     */
    void loadMore();

}
