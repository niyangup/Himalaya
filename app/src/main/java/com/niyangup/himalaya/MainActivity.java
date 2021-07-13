package com.niyangup.himalaya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.niyangup.himalaya.adapter.IndicatorAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private MagicIndicator mMainIndicator;
    private ViewPager mContentPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        mMainIndicator = findViewById(R.id.main_indicator);
        mContentPager = findViewById(R.id.content_pager);
        mMainIndicator.setBackgroundColor(getColor(R.color.main_color));

        IndicatorAdapter adapter = new IndicatorAdapter(getApplicationContext());
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(adapter);

        mMainIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMainIndicator, mContentPager);
    }
}