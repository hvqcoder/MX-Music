package vn.poly.mxmusic.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import vn.poly.mxmusic.R;
import vn.poly.mxmusic.activity.SingerDetailActivity;
import vn.poly.mxmusic.model.SingerModel;

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

        View finalView = view;
        //open option singer
        singerHolder.lnlOpenOptionSinger.setOnClickListener(view12 -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetTheme);
            View sheetView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_singer, finalView.findViewById(R.id.bottom_sheet_singer));

            TextView tvBottomSheetSingerName = sheetView.findViewById(R.id.tvBottomSheetSingerName);
            TextView tvBottomSheetSingerNumber = sheetView.findViewById(R.id.tvBottomSheetSingerNumber);

            tvBottomSheetSingerName.setText(singerModel.getName());
            tvBottomSheetSingerNumber.setText(singerModel.getCountSong() + " Bài hát");


            //delete song of the singer in storage
            sheetView.findViewById(R.id.lnlDeleteSinger).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottomSheetDialog.dismiss();
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.dialog_delete_singer);
                    TextView tvDeleteSinger = dialog.findViewById(R.id.tvDeleteSinger);
                    tvDeleteSinger.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "Hiện tại chưa xóa được!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                    TextView tvCancelSinger = dialog.findViewById(R.id.tvCancelSinger);
                    tvCancelSinger.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });

            bottomSheetDialog.setContentView(sheetView);
            bottomSheetDialog.show();
        });
        singerHolder.lnlOpenSinger.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, SingerDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("singerID", singerModel.getId());
            bundle.putString("singerName", singerModel.getName());
            bundle.putString("singerSongNumber", singerModel.getCountSong());
            intent.putExtra("singer", bundle);
            context.startActivity(intent);
        });
        return view;
    }

    private static class SingerHolder {
        TextView tvSingerName, tvSingerSongNumber;
        LinearLayout lnlOpenSinger, lnlOpenOptionSinger;
    }
}
