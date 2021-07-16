package com.niyangup.himalaya.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.niyangup.himalaya.R;
import com.niyangup.himalaya.base.BaseApplication;

public abstract class UILoader extends FrameLayout {
    View mLoadingView;
    View mSuccessView;
    View mNetworkErrorView;
    View mEmptyView;

    public enum UIStatus {
        LOADING, SUCCESS, NETWORK_ERROR, EMPTY, NONE
    }

    public UIStatus mCurrentStatus = UIStatus.NONE;

    public UILoader(@NonNull Context context) {
        this(context, null);
    }

    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void updateState(UIStatus status) {
        mCurrentStatus = status;
        BaseApplication.Companion.getHandle().post(this::switchUIByCurrentStatus);
    }

    private void init() {
        switchUIByCurrentStatus();
    }

    private void switchUIByCurrentStatus() {
        if (mLoadingView == null) {
            mLoadingView = getLoadView();
            addView(mLoadingView);
        }
        mLoadingView.setVisibility(mCurrentStatus == UIStatus.LOADING ? VISIBLE : GONE);

        if (mSuccessView == null) {
            mSuccessView = getSuccessView(this);
            addView(mSuccessView);
        }
        mSuccessView.setVisibility(mCurrentStatus == UIStatus.SUCCESS ? VISIBLE : GONE);

        if (mNetworkErrorView == null) {
            mNetworkErrorView = getNetworkErrorView();
            addView(mNetworkErrorView);
        }
        mNetworkErrorView.setVisibility(mCurrentStatus == UIStatus.NETWORK_ERROR ? VISIBLE : GONE);

        if (mEmptyView == null) {
            mEmptyView = getEmptyView();
            addView(mEmptyView);
        }
        mEmptyView.setVisibility(mCurrentStatus == UIStatus.EMPTY ? VISIBLE : GONE);
    }

    private View getEmptyView() {
        return View.inflate(getContext(), R.layout.fragment_empty_view, null);
    }

    private View getNetworkErrorView() {
        View view = View.inflate(getContext(), R.layout.fragment_network_error_view, null);
        View retryView = view.findViewById(R.id.ll_network_error_retry);
        retryView.setOnClickListener(v -> onRetry());
        return view;
    }

    public abstract View getSuccessView(ViewGroup viewGroup);

    public View getLoadView() {
        return View.inflate(getContext(), R.layout.fragment_loading_view, null);
    }

    public abstract void onRetry();
}
