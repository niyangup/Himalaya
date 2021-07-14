package com.niyangup.himalaya.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mRootView = onSubViewLoaded(inflater, container);
        return mRootView;
    }

    public abstract View onSubViewLoaded(LayoutInflater inflater, ViewGroup container);
}
