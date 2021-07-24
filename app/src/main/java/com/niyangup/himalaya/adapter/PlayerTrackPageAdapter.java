package com.niyangup.himalaya.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.niyangup.himalaya.R;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PlayerTrackPageAdapter extends PagerAdapter {
    private List<Track> mTracks = new ArrayList<>();

    @Override
    public int getCount() {
        return mTracks.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {
        return view == object;
    }

    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        View view = View.inflate(container.getContext(), R.layout.item_track_pager, null);
        container.addView(view);
        ImageView img = view.findViewById(R.id.track_pager_item);
        Track track = mTracks.get(position);
        String url = track.getCoverUrlSmall();
        Picasso.with(container.getContext()).load(url).into(img);
        return view;
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        container.removeView(container);
    }

    public void setData(List<Track> tracks) {
        mTracks.clear();
        mTracks.addAll(tracks);
        notifyDataSetChanged();
    }
}
