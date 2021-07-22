package com.niyangup.himalaya.interfaces;

public interface IBasePresenter<T> {

    void registerViewCallback(T callback);

    void unRegisterViewCallback(T callback);
}
