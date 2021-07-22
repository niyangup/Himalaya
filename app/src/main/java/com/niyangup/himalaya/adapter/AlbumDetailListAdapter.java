package com.niyangup.himalaya.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.niyangup.himalaya.R;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AlbumDetailListAdapter extends RecyclerView.Adapter<AlbumDetailListAdapter.AlbumViewHolder> {
    private List<Track> mTracks = new ArrayList<>();
    private final DateFormat mUpdateDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final DateFormat mDurationFormat = new SimpleDateFormat("mm:ss");
    private OnItemClickListener mListener = null;


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }


    @NonNull
    @Override
    public AlbumDetailListAdapter.AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_album_detail, null);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumDetailListAdapter.AlbumViewHolder holder, int position) {
        //找到控件，设置数据
        View itemView = holder.itemView;
        //顺序Id
        TextView orderTv = itemView.findViewById(R.id.order_text);
        //标题
        TextView titleTv = itemView.findViewById(R.id.detail_item_title);
        //播放次数
        TextView playCountTv = itemView.findViewById(R.id.detail_item_play_count);
        //时长
        TextView durationTv = itemView.findViewById(R.id.detail_item_duration);
        //更新日期
        TextView updateDateTv = itemView.findViewById(R.id.detail_item_update_time);

        //设置数据
        final Track track = mTracks.get(position);
        orderTv.setText((position + 1) + "");
        titleTv.setText(track.getTrackTitle());
        playCountTv.setText(track.getPlayCount() + "");

        int durationMil = track.getDuration() * 1000;
        String duration = mDurationFormat.format(durationMil);
        durationTv.setText(duration);
        String updateTimeText = mUpdateDateFormat.format(track.getUpdatedAt());
        updateDateTv.setText(updateTimeText);


        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(mTracks,position);
            }
        });
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

    public interface OnItemClickListener {
        void onItemClick(List<Track> tracks, int position);
    }
}
