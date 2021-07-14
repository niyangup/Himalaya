package com.niyangup.himalaya.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niyangup.himalaya.R;
import com.niyangup.himalaya.base.BaseFragment;

public class HistoryFragment extends BaseFragment {
    @Override
    public View onSubViewLoaded(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }
}
