package vn.poly.mxmusic.activity;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.poly.mxmusic.Playable;
import vn.poly.mxmusic.R;
import vn.poly.mxmusic.model.SongModel;
import vn.poly.mxmusic.notification.CreateNotification;
import vn.poly.mxmusic.notification.NotificationReceiver;
import vn.poly.mxmusic.notification.OnClearFromRecentService;

import static vn.poly.mxmusic.adapter.SongAdapter.list;
import static vn.poly.mxmusic.notification.CreateNotification.ACTION_NEXT;
import static vn.poly.mxmusic.notification.CreateNotification.ACTION_PLAY;
import static vn.poly.mxmusic.notification.CreateNotification.ACTION_PREV;
import static vn.poly.mxmusic.notification.CreateNotification.CHANNEL_ID_2;


public class MusicPlaybackScreenActivity extends AppCompatActivity implements Playable {
    TextView tvPlayMusicName, tvPlayMusicSinger, tvStart, tvEnd;
    ImageView ivPlayMusicImage;
    ImageView ivBack, ivStop, ivNext;
    SeekBar seekBarPlayMusic;
    static int position = -1;
    public Uri uri;
    public static MediaPlayer player = new MediaPlayer();
    static List<SongModel> songs;
    static int current_pos = 0, total_duration = 0;
    MediaSessionCompat mediaSessionCompat;
    public static  NotificationManager notificationManager;
    public static byte[] icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_playback_screen);
        //Ân toolbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mediaSessionCompat = new MediaSessionCompat(getBaseContext(), "sonhandsome");
        //
        init();
        getIntentMethod();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(broadcastReceiver, new IntentFilter("MXMUSIC_MXMUSIC"));
            startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
        }
    }

    public void metaData(Uri uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        int duration = Integer.parseInt(songs.get(position).getDuration());
        tvEnd.setText(timerConversion(duration));
        byte[] art = retriever.getEmbeddedPicture();
        if (art != null) {
            Glide.with(this)
                    .asBitmap()
                    .load(art)
                    .into(ivPlayMusicImage);
        } else {
            Glide.with(this)
                    .asBitmap()
                    .load(R.drawable.logo)
                    .into(ivPlayMusicImage);
        }
    }

    private void seekBar() {
        setAudioProgress();
        seekBarPlayMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                current_pos = seekBar.getProgress();
                player.seekTo(current_pos);
            }
        });
    }

    private void getIntentMethod() {
        position = getIntent().getIntExtra("position", -1);
        songs = list;
        if (songs != null) {
            ivStop.setImageResource(R.drawable.ic_pause_black);
            uri = Uri.parse(songs.get(position).getSongUri());
        }
        //check player
        if (player != null && player.isPlaying()) {
            player.stop();
            player.reset();
            player.release();
            player = MediaPlayer.create(this, uri);
            player.start();
        } else {
            player = MediaPlayer.create(this, uri);
            player.start();
        }
        seekBarPlayMusic.setMax(player.getDuration());
        tvPlayMusicName.setText(songs.get(position).getTitle());
        tvPlayMusicSinger.setText(songs.get(position).getSinger());
        showNotification(R.drawable.ic_pause_black);
        metaData(uri);
    }

    public void init() {
        tvPlayMusicName = findViewById(R.id.tvPlayMusicName);
        tvPlayMusicSinger = findViewById(R.id.tvPlayMusicSinger);
        tvStart = findViewById(R.id.tvStart);
        tvEnd = findViewById(R.id.tvEnd);
        ivPlayMusicImage = findViewById(R.id.ivPlayMusicImage);
        ivBack = findViewById(R.id.ivBack);
        ivStop = findViewById(R.id.ivStop);
        ivNext = findViewById(R.id.ivNext);
        seekBarPlayMusic = findViewById(R.id.seekBarPlayMusic);
    }

    public void prev(View view) {
        onPrev();
    }

    public void pause(View view) {
        onPauses();
    }

    public void next(View view) {
        onNext();
    }

    private void setAudioProgress() {
        //time start
        current_pos = player.getCurrentPosition();
        //time end
        total_duration = player.getDuration();

        tvStart.setText(timerConversion(current_pos));
        tvEnd.setText(timerConversion(total_duration));
        seekBarPlayMusic.setMax(total_duration);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    current_pos = player.getCurrentPosition();
                    tvStart.setText(timerConversion((long) current_pos));
                    seekBarPlayMusic.setProgress((int) current_pos);
                    //khi hết bài hát tự động next
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            if (position < (songs.size() - 1)) {
                                position++;
                            } else {
                                position = 0;
                            }
                            onPlay(position);
                            tvPlayMusicName.setText(songs.get(position).getTitle());
                            tvPlayMusicSinger.setText(songs.get(position).getSinger());
                            Bitmap image;
                            if (icon!=null){
                                image=BitmapFactory.decodeByteArray(icon, 0,icon.length);
                            }
                            else{
                                image=BitmapFactory.decodeResource(getResources(), R.drawable.logo);
                            }
                            ivPlayMusicImage.setImageBitmap(image);
                            ivStop.setImageResource(R.drawable.ic_pause_black);
                            uri=Uri.parse(songs.get(position).getSongUri());
                            metaData(uri);
                            showNotification(R.drawable.ic_pause_black);
                        }
                    });
                    handler.postDelayed(this, 1000);
                } catch (IllegalStateException ed) {
                    ed.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    public String timerConversion(long value) {
        String audioTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            audioTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            audioTime = String.format("%02d:%02d", mns, scs);
        }
        return audioTime;
    }

    @Override
    public void onPrev() {
        if (position <= 0) {

            position = songs.size() - 1;
        } else {
            position--;
        }
        onPlay(position);
        tvPlayMusicName.setText(songs.get(position).getTitle());
        tvPlayMusicSinger.setText(songs.get(position).getSinger());
        Bitmap image;
        if (icon != null) {
            image = BitmapFactory.decodeByteArray(icon, 0, icon.length);
        } else {
            image = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        }
        ivPlayMusicImage.setImageBitmap(image);
        ivStop.setImageResource(R.drawable.ic_pause_black);
        showNotification(R.drawable.ic_pause_black);
        uri = Uri.parse(songs.get(position).getSongUri());
        metaData(uri);
        setAudioProgress();
    }

    @Override
    public void onPlay(int pos) {
        try {
            player.stop();//dừng
            player.reset();//reset
            player.release();//giải phóng
            player = null;
            player = new MediaPlayer();
            player.setDataSource(getApplicationContext(), Uri.parse(songs.get(pos).getSongUri()));
            player.prepare();//chuẩn bị
            player.start();//bắt đầu
            //
            Bitmap image;
            if (icon != null) {
                image = BitmapFactory.decodeByteArray(icon, 0, icon.length);
            } else {
                image = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
            }
            ivPlayMusicImage.setImageBitmap(image);
            showNotification(R.drawable.ic_pause_black);
            ivStop.setImageResource(R.drawable.ic_pause_black);
            uri = Uri.parse(songs.get(position).getSongUri());
            metaData(uri);
            //thay đổi tên ở bottomsheet
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPauses() {
        if (player.isPlaying()) {
            player.pause();
            ivStop.setImageResource(R.drawable.ic_play_black);
            showNotification(R.drawable.ic_play_black);
        } else {
            player.start();
            ivStop.setImageResource(R.drawable.ic_pause_black);
            showNotification(R.drawable.ic_pause_black);
        }
    }

    @Override
    public void onNext() {
        if (position < (songs.size() - 1)) {
            position++;
        } else {
            position = 0;
        }
        onPlay(position);
        tvPlayMusicName.setText(songs.get(position).getTitle());
        tvPlayMusicSinger.setText(songs.get(position).getSinger());
        Bitmap image;
        if (icon != null) {
            image = BitmapFactory.decodeByteArray(icon, 0, icon.length);
        } else {
            image = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        }
        setAudioProgress();
        ivPlayMusicImage.setImageBitmap(image);
        ivStop.setImageResource(R.drawable.ic_pause_black);
        uri = Uri.parse(songs.get(position).getSongUri());
        metaData(uri);
        showNotification(R.drawable.ic_pause_black);
    }

    private byte[] getPicture(String uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        return art;
    }

    public void showNotification(int playPauseBtn) {
        Intent i = new Intent(getBaseContext(), MusicPlaybackScreenActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(), 0, i,
                0);
        //prev
        Intent intentPrev = new Intent(getBaseContext(), NotificationReceiver.class)
                .setAction(ACTION_PREV);
        PendingIntent pendingIntentPrev = PendingIntent.getBroadcast(getBaseContext(),
                0, intentPrev, PendingIntent.FLAG_UPDATE_CURRENT);
        //play
        Intent intentPause = new Intent(getBaseContext(), NotificationReceiver.class)
                .setAction(ACTION_PLAY);
        PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(getBaseContext(),
                0, intentPause, PendingIntent.FLAG_UPDATE_CURRENT);
        //next
        Intent intentNext = new Intent(getBaseContext(), NotificationReceiver.class)
                .setAction(ACTION_NEXT);
        PendingIntent pendingIntentNext = PendingIntent.getBroadcast(getBaseContext(),
                0, intentNext, PendingIntent.FLAG_UPDATE_CURRENT);
        //
        byte[] picture = null;
        Bitmap icon = null;
        picture = getPicture(songs.get(position).getSongUri());
        if (picture != null) {
            icon = BitmapFactory.decodeByteArray(picture, 0, picture.length);
        } else {
            icon = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        }
        Notification notification = new NotificationCompat.Builder(getBaseContext(), CHANNEL_ID_2)
                .setSmallIcon(R.drawable.logonen)
                .setContentTitle(songs.get(position).getTitle())
                .setContentText(songs.get(position).getSinger())
                .setLargeIcon(icon)
                .setOnlyAlertOnce(true)
                .setShowWhen(false)
                .addAction(R.drawable.ic_previous_black, "Prev", pendingIntentPrev)
                .addAction(playPauseBtn, "Play", pendingIntentPlay)
                .addAction(R.drawable.ic_next_black, "Next", pendingIntentNext)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(mediaSessionCompat.getSessionToken()))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setOnlyAlertOnce(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .build();
       notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    @Override
    protected void onStart() {
        super.onStart();
        seekBar();
    }
    
    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String actions = intent.getExtras().getString("actionname");
            switch (actions) {
                case CreateNotification.ACTION_PREV:
                    onPrev();
                    break;
                case CreateNotification.ACTION_PLAY:
                    onPauses();
                    break;
                case CreateNotification.ACTION_NEXT:
                    onNext();
                    break;
            }
        }
    };

}