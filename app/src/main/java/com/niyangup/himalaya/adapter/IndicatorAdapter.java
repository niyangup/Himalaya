package com.niyangup.himalaya.adapter;

import android.content.Context;

import com.niyangup.himalaya.R;
import com.niyangup.himalaya.utils.LogUtil;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.model.PositionData;

import java.util.List;

public class IndicatorAdapter extends CommonNavigatorAdapter {

    private final Context context;

    public IndicatorAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        LogUtil.d("tag", "getCount" + context.getResources().getStringArray(R.array.indicator_title).length);
        return context.getResources().getStringArray(R.array.indicator_title).length;
    }

    @Override
    public IPagerTitleView getTitleView(Context context, int index) {
        return new IPagerTitleView() {
            @Override
            public void onSelected(int index, int totalCount) {

            }

            @Override
            public void onDeselected(int index, int totalCount) {

            }

            @Override
            public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

            }

            @Override
            public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

            }
        };
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        return new IPagerIndicator() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

            @Override
            public void onPositionDataProvide(List<PositionData> dataList) {

            }
        };
    }
}
