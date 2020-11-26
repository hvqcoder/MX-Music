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
import com.example.mxmusic.model.SingerModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SingerAdapter extends BaseAdapter {
    List<SingerModel> list;
    public Context context;
    public LayoutInflater inflater;

    public SingerAdapter(Context context, List<SingerModel> list) {
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
        SingerHolder singerHolder;
        SingerModel singerModel = list.get(i);
        if (view == null) {
            singerHolder = new SingerHolder();
            view = inflater.inflate(R.layout.item_singer, null);
            singerHolder.tvSingerName = view.findViewById(R.id.tvSingerName);
            singerHolder.tvSingerSongNumber = view.findViewById(R.id.tvSingerSongNumber);
//            albumHolder.ivAlbumImage = view.findViewById(R.id.ivAlbumImage);
            singerHolder.lnlOpenSinger = view.findViewById(R.id.lnlOpenSinger);
            singerHolder.lnlOpenOptionSinger = view.findViewById(R.id.lnlOpenOptionSinger);

            view.setTag(singerHolder);
        } else {
            singerHolder = (SingerHolder) view.getTag();
        }

        singerHolder.tvSingerName.setText(singerModel.getName());
        singerHolder.tvSingerSongNumber.setText(singerModel.getCountSong() + " Bài hát");

//        final Uri albumUri = Uri.parse("content://media/external/audio/albumart");
//        Uri uri = ContentUris.withAppendedId(albumUri, Long.parseLong(albumModel.getId()));
//        Picasso.get().load(uri)
//                .fit()
//                .centerCrop()
//                .error(R.drawable.logobackgroup)
//                .into(albumHolder.ivAlbumImage);

        singerHolder.lnlOpenOptionSinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        singerHolder.lnlOpenSinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

    private static class SingerHolder {
        TextView tvSingerName, tvSingerSongNumber;
//        ImageView ivAlbumImage;
        LinearLayout lnlOpenSinger, lnlOpenOptionSinger;
    }
}
