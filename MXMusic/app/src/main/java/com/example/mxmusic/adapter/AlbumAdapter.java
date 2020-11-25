package com.example.mxmusic.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mxmusic.R;
import com.example.mxmusic.model.AlbumModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AlbumAdapter extends BaseAdapter {
    List<AlbumModel> list;
    public Context context;
    public LayoutInflater inflater;

    public AlbumAdapter(Context context, List<AlbumModel> list) {
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AlbumHolder albumHolder;
        AlbumModel albumModel = list.get(i);
        if (view == null) {
            albumHolder = new AlbumHolder();
            view = inflater.inflate(R.layout.item_album, null);
            albumHolder.tvAblumName = view.findViewById(R.id.tvAblumName);
            albumHolder.tvAbumSongNumber = view.findViewById(R.id.tvAbumSongNumber);
            albumHolder.ivAlbumImage = view.findViewById(R.id.ivAlbumImage);
            albumHolder.lnlOpenAlbum = view.findViewById(R.id.lnlOpenAlbum);
            albumHolder.lnlOpenOptionAlbum = view.findViewById(R.id.lnlOpenOptionAlbum);

            view.setTag(albumHolder);
        } else {
            albumHolder = (AlbumHolder) view.getTag();
        }

        albumHolder.tvAblumName.setText(albumModel.getName());
        albumHolder.tvAbumSongNumber.setText(albumModel.getCountSong() + " Bài hát");

        final Uri albumUri = Uri.parse("content://media/external/audio/albumart");
        Uri uri = ContentUris.withAppendedId(albumUri, Long.parseLong(albumModel.getId()));
        Picasso.get().load(uri)
                .fit()
                .centerCrop()
                .error(R.drawable.logobackgroup)
                .into(albumHolder.ivAlbumImage);

        albumHolder.lnlOpenOptionAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        albumHolder.lnlOpenAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

    private static class AlbumHolder {
        TextView tvAblumName, tvAbumSongNumber;
        ImageView ivAlbumImage;
        LinearLayout lnlOpenAlbum, lnlOpenOptionAlbum;
    }
}
