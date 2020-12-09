package vn.poly.mxmusic.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vn.poly.mxmusic.R;
import vn.poly.mxmusic.activity.AlbumDetailActivity;
import vn.poly.mxmusic.model.AlbumModel;

import static androidx.core.content.ContextCompat.startActivity;

public class AlbumAdapter extends BaseAdapter {
    final List<AlbumModel> list;
    public final Context context;
    public final LayoutInflater inflater;

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

        albumHolder.lnlOpenOptionAlbum.setOnClickListener(view12 -> {

        });
        albumHolder.lnlOpenAlbum.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, AlbumDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("albumID", albumModel.getId());
            bundle.putString("albumName", albumModel.getName());
            bundle.putString("albumSongNumber", albumModel.getCountSong());
            intent.putExtra("album", bundle);
            startActivity(context, intent, bundle);
        });
        return view;
    }

    private static class AlbumHolder {
        TextView tvAblumName, tvAbumSongNumber;
        ImageView ivAlbumImage;
        LinearLayout lnlOpenAlbum, lnlOpenOptionAlbum;
    }
}
