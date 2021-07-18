package com.niyangup.himalaya.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

public
interface IDetailViewCallback {
    void onDetailListLoaded(List<Track> tracks);

    void onAlbumLoaded(Album album);
}
