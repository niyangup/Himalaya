package com.niyangup.himalaya.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.niyangup.himalaya.R;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.List;

public class RecommendListAdapter extends RecyclerView.Adapter<RecommendListAdapter.InnerHolder> {
    private List<Album> data = new ArrayList<>();

    @NonNull
    @Override
    public RecommendListAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = View.inflate(parent.getContext(), R.layout.item_recommend, null);
        InnerHolder holder = new InnerHolder(item);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendListAdapter.InnerHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Album> albumList) {
        if (data != null) {
            data.clear();
            data.addAll(albumList);
        }
        notifyDataSetChanged();
    }

    public static class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
