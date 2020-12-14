package vn.poly.mxmusic.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import vn.poly.mxmusic.R;
import vn.poly.mxmusic.activity.MusicPlaybackScreenActivity;
import vn.poly.mxmusic.dao.FavouriteDao;
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

    @SuppressLint("ResourceAsColor")
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

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(songModel.getSongUri());
        final byte[] art = retriever.getEmbeddedPicture();
        Glide.with(context)
                .asBitmap()
                .load(art)
                .error(R.drawable.logobackgroup)
                .into(songHolder.ivSongImage);

        View finalView = view;
        //open option song
        songHolder.lnlOpenOption.setOnClickListener(view12 -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetTheme);
            View sheetView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_song, finalView.findViewById(R.id.bottom_sheet_song));

            ImageView ivBottomSheetSongImage = sheetView.findViewById(R.id.ivBottomSheetSongImage);
            ImageView ivBottomSheetFavourite = sheetView.findViewById(R.id.ivBottomSheetFavourite);
            TextView tvBottomSheetFavourite = sheetView.findViewById(R.id.tvBottomSheetFavourite);
            TextView tvBottomSheetSongName = sheetView.findViewById(R.id.tvBottomSheetSongName);
            TextView tvBottomSheetSingerName = sheetView.findViewById(R.id.tvBottomSheetSingerName);
            Glide.with(context)
                    .asBitmap()
                    .load(art)
                    .error(R.drawable.logobackgroup)
                    .into(ivBottomSheetSongImage);
            tvBottomSheetSongName.setText(songModel.getTitle());
            tvBottomSheetSingerName.setText(songModel.getSinger());

            List<String> listID = new FavouriteDao(context).getAllFavourite();
            if (listID.indexOf(songModel.getId()) >= 0) {
                ivBottomSheetFavourite.setImageResource(R.drawable.unfavourite);
                tvBottomSheetFavourite.setText("Đã thêm vào yêu thích");
                tvBottomSheetFavourite.setTextColor(Color.parseColor("#e84a2f"));
            }
            //add-delete song in favourite
            sheetView.findViewById(R.id.lnlFavouriteSong).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listID.indexOf(songModel.getId()) >= 0) {
                        new FavouriteDao(context).deleteFavourite(songModel.getId());
                        Toast.makeText(context,"Đã xóa bài hát khỏi thư mục yêu thích",Toast.LENGTH_SHORT).show();
//                        Context context1 = FavouriteFragment.newInstance().getActivity();
//                        if (context.toString().equals(context1.toString())){
//                            list.remove(i);
//                            notifyDataSetChanged();
//                        }
                        bottomSheetDialog.dismiss();
                    } else {
                        new FavouriteDao(context).insertFavourite(songModel.getId());
                        Toast.makeText(context,"Đã thêm bài hát vào thư mục yêu thích",Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    }
                }
            });

            //delete song in storage
            sheetView.findViewById(R.id.lnlDeleteSong).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottomSheetDialog.dismiss();
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.dialog_delete_song);
                    TextView tvDeleteSong = dialog.findViewById(R.id.tvDeleteSong);
                    tvDeleteSong.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            context.getContentResolver().delete(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, MediaStore.Audio.Media._ID + "=" + songModel.getId(), null);
                            if (listID.indexOf(songModel.getId()) >= 0) {
                                new FavouriteDao(context).deleteFavourite(songModel.getId());
                            }
                            list.remove(i);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });
                    TextView tvCancelSong = dialog.findViewById(R.id.tvCancelSong);
                    tvCancelSong.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });

            //open properties song
            sheetView.findViewById(R.id.lnlPropertiesSong).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottomSheetDialog.dismiss();
                    BottomSheetDialog bottomSheetDialog1 = new BottomSheetDialog(context, R.style.BottomSheetTheme);
                    View sheetView1 = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_properties, finalView.findViewById(R.id.bottom_sheet_properties));

                    TextView tvNameSong = sheetView1.findViewById(R.id.tvNameSong);
                    TextView tvFile = sheetView1.findViewById(R.id.tvFile);
                    TextView tvLocation = sheetView1.findViewById(R.id.tvLocation);
                    TextView tvSize = sheetView1.findViewById(R.id.tvSize);
                    TextView tvTitle = sheetView1.findViewById(R.id.tvTitle);
                    TextView tvLength = sheetView1.findViewById(R.id.tvLength);
                    TextView tvYear = sheetView1.findViewById(R.id.tvYear);
                    TextView tvAlbum = sheetView1.findViewById(R.id.tvAlbum);
                    TextView tvArtist = sheetView1.findViewById(R.id.tvArtist);
                    TextView tvComposer = sheetView1.findViewById(R.id.tvComposer);
                    TextView tvAlbumArtist = sheetView1.findViewById(R.id.tvAlbumArtist);

                    tvNameSong.setText(songModel.getTitle());
                    tvFile.setText(songModel.getDisplayName());
                    String dregs = songModel.getDisplayName();
                    String s = songModel.getSongUri();
                    String[] splits = s.split(dregs);
                    for (String item : splits)
                        tvLocation.setText(item);
                    double cVSize = (double) Math.round(((double) Integer.parseInt(songModel.getSize()) / 1000000) * 100) / 100;
                    tvSize.setText(cVSize + " MB");
                    tvFile.setText(songModel.getDisplayName());
                    tvTitle.setText(songModel.getTitle());

                    String audioTime;
                    int dur = Integer.parseInt(songModel.getDuration());
                    int hrs = (dur / 3600000);
                    int mns = (dur / 60000) % 60000;
                    int scs = dur % 60000 / 1000;
                    if (hrs > 0) {
                        audioTime = String.format("%02d : %02d : %02d", hrs, mns, scs);
                    } else {
                        audioTime = String.format("%02d : %02d", mns, scs);
                    }
                    tvLength.setText(audioTime);
                    tvYear.setText(songModel.getYear());
                    tvAlbum.setText(songModel.getAlbum());
                    tvArtist.setText(songModel.getSinger());
                    tvComposer.setText(songModel.getComposer());
                    tvAlbumArtist.setText(songModel.getAlbumArtist());

                    sheetView1.findViewById(R.id.closeProperties).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bottomSheetDialog1.dismiss();
                        }
                    });

                    bottomSheetDialog1.setContentView(sheetView1);
                    bottomSheetDialog1.show();
                }
            });
            bottomSheetDialog.setContentView(sheetView);
            bottomSheetDialog.show();
        });

        //play music
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
