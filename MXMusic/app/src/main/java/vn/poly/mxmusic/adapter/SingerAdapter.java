package vn.poly.mxmusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import vn.poly.mxmusic.R;
import vn.poly.mxmusic.activity.SingerDetailActivity;
import vn.poly.mxmusic.model.SingerModel;

import static androidx.core.content.ContextCompat.startActivity;

public class SingerAdapter extends BaseAdapter {
    final List<SingerModel> list;
    public final Context context;
    public final LayoutInflater inflater;

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
            singerHolder.lnlOpenSinger = view.findViewById(R.id.lnlOpenSinger);
            singerHolder.lnlOpenOptionSinger = view.findViewById(R.id.lnlOpenOptionSinger);

            view.setTag(singerHolder);
        } else {
            singerHolder = (SingerHolder) view.getTag();
        }

        singerHolder.tvSingerName.setText(singerModel.getName());
        singerHolder.tvSingerSongNumber.setText(singerModel.getCountSong() + " Bài hát");

        singerHolder.lnlOpenOptionSinger.setOnClickListener(view12 -> {

        });
        singerHolder.lnlOpenSinger.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, SingerDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("singerID", singerModel.getId());
            bundle.putString("singerName", singerModel.getName());
            bundle.putString("singerSongNumber", singerModel.getCountSong());
            intent.putExtra("singer", bundle);
            startActivity(context, intent, bundle);
        });
        return view;
    }

    private static class SingerHolder {
        TextView tvSingerName, tvSingerSongNumber;
        LinearLayout lnlOpenSinger, lnlOpenOptionSinger;
    }
}
