package com.niyangup.himalaya.interfaces;


public interface IRecommendPresenter {
    /**
     * 获取推荐列表
     */
    void getRecommendList();

    /**
     * 下拉刷新
     */
    void pullToRefresh();

    /**
     * 上拉加载更多
     */
    void loadMore();

    void registerViewCallback(IRecommendViewCallback callback);

    void unRegisterViewCallback(IRecommendViewCallback callback);


}
























