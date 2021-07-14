package com.niyangup.himalaya.utils;

import com.niyangup.himalaya.base.BaseFragment;
import com.niyangup.himalaya.fragment.HistoryFragment;
import com.niyangup.himalaya.fragment.RecommendFragment;
import com.niyangup.himalaya.fragment.SubscriptionFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentCreator {
    public static final int RECOMMEND_FRAGMENT = 0;
    public static final int SUBSCRIPTION_FRAGMENT = 1;
    public static final int HISTORY_FRAGMENT = 2;

    public static final int PAGE_COUNTER = 3;

    public static Map<Integer, BaseFragment> mCacheFragment = new HashMap<>();


    public static BaseFragment getFragment(int index) {
        BaseFragment baseFragment = mCacheFragment.get(index);

        if (baseFragment != null) {
            return baseFragment;
        }
        switch (index) {
            case RECOMMEND_FRAGMENT:
                baseFragment = new RecommendFragment();
                break;
            case SUBSCRIPTION_FRAGMENT:
                baseFragment = new SubscriptionFragment();
                break;
            case HISTORY_FRAGMENT:
                baseFragment = new HistoryFragment();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + index);
        }

        return baseFragment;
    }
}
