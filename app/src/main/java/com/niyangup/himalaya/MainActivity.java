package com.niyangup.himalaya;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.niyangup.himalaya.adapter.IndicatorAdapter;
import com.niyangup.himalaya.adapter.MainContentAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

public class MainActivity extends FragmentActivity {

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
        MainContentAdapter mainContentAdapter = new MainContentAdapter(getSupportFragmentManager());
        mContentPager.setAdapter(mainContentAdapter);


        mMainIndicator.setBackgroundColor(getColor(R.color.main_color));

        IndicatorAdapter adapter = new IndicatorAdapter(getApplicationContext());
        adapter.setOnIndicatorTapClickListener(new IndicatorAdapter.OnIndicatorTapClickListener() {
            @Override
            public void onTabClick(int index) {
                mContentPager.setCurrentItem(index);
            }
        });
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(adapter);

        mMainIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMainIndicator, mContentPager);
    }
}