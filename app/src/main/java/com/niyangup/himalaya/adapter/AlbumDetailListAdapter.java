package com.niyangup.himalaya.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.niyangup.himalaya.R;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.ArrayList;
import java.util.List;

public class AlbumDetailListAdapter extends RecyclerView.Adapter<AlbumDetailListAdapter.AlbumViewHolder> {
    private List<Track> mTracks = new ArrayList<>();

    @NonNull
    @Override
    public AlbumDetailListAdapter.AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_album_detail, null);
        AlbumViewHolder holder = new AlbumViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumDetailListAdapter.AlbumViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mTracks.size();
    }

    public void setData(List<Track> tracks) {
        mTracks.clear();
        mTracks = tracks;
        notifyDataSetChanged();
    }

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {
        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
