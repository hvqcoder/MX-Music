package vn.poly.mxmusic.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import vn.poly.mxmusic.activity.MusicPlaybackScreenActivity;
import vn.poly.mxmusic.model.SongModel;

public class SongAdapter extends BaseAdapter {
    public static List<SongModel> list;
    public final Context context;
    public final LayoutInflater inflater;

    public SongAdapter(Context context, List<SongModel> list) {
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
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SongHolder songHolder;
        SongModel songModel = list.get(i);
        if (view == null) {
            songHolder = new SongHolder();
            view = inflater.inflate(R.layout.item_song, null);
            songHolder.tvSongName = view.findViewById(R.id.tvSongName);
            songHolder.tvSongSinger = view.findViewById(R.id.tvSongSinger);
            songHolder.ivSongImage = view.findViewById(R.id.ivSongImage);
            songHolder.lnlPlayerSong = view.findViewById(R.id.lnlPlayerSong);
            songHolder.lnlOpenOption = view.findViewById(R.id.lnlOpenOption);

            view.setTag(songHolder);
        } else {
            songHolder = (SongHolder) view.getTag();
        }

        songHolder.tvSongName.setText(songModel.getTitle());
        songHolder.tvSongSinger.setText(songModel.getSinger());

        final Uri albumUri = Uri.parse("content://media/external/audio/albumart");
        Uri uri = ContentUris.withAppendedId(albumUri, Long.parseLong(songModel.getAlbumID()));
        Picasso.get().load(uri)
                .fit()
                .centerCrop()
                .error(R.drawable.logobackgroup)
                .into(songHolder.ivSongImage);

        songHolder.lnlOpenOption.setOnClickListener(view12 -> {

        });
        songHolder.lnlPlayerSong.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, MusicPlaybackScreenActivity.class);
            intent.putExtra("position", i);
            context.startActivity(intent);
        });
        return view;
    }

    private static class SongHolder {
        TextView tvSongName, tvSongSinger;
        ImageView ivSongImage;
        LinearLayout lnlPlayerSong, lnlOpenOption;
    }
}
