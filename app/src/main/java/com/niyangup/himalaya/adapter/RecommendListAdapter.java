package com.niyangup.himalaya.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.niyangup.himalaya.R;
import com.squareup.picasso.Picasso;
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
        holder.setData(data.get(position));
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

        public void setData(Album album) {
            ImageView img = itemView.findViewById(R.id.album_cover);
            TextView tvTitle = itemView.findViewById(R.id.album_title_tv);
            TextView tvSubtitleTitle = itemView.findViewById(R.id.album_subtitle_tv);
            TextView tvCount = itemView.findViewById(R.id.album_play_count);
            TextView tvSize = itemView.findViewById(R.id.album_content_size);


            tvTitle.setText(album.getAlbumTitle());
            tvSubtitleTitle.setText(album.getAlbumIntro());
            tvCount.setText(album.getPlayCount() + "");
            tvSize.setText(album.getIncludeTrackCount() + "");

            if (!TextUtils.isEmpty(album.getCoverUrlLarge())) {
                Picasso.with(itemView.getContext()).load(album.getCoverUrlLarge()).into(img);
            }
        }
    }
}
