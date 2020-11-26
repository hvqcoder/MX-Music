package com.example.mxmusic.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.mxmusic.R;
import com.example.mxmusic.adapter.SongAdapter;
import com.example.mxmusic.loader.SongLoader;
import com.example.mxmusic.model.SongModel;

import java.util.ArrayList;
import java.util.List;

import static android.bluetooth.BluetoothGattCharacteristic.PERMISSION_READ;


public class SongFragment extends Fragment {

    ListView lvSong;
    SongAdapter songAdapter;
    SongLoader songLoader;
    public static SongFragment newInstance() {
        SongFragment fragment = new SongFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song, container, false);
        lvSong = view.findViewById(R.id.lvSong);
        songLoader = new SongLoader();
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ);
        }
        songAdapter = new SongAdapter(getActivity(), songLoader.getAllSong(getContext(), MediaStore.Audio.Media.IS_MUSIC + "=1"));//+" and "+ MediaStore.Audio.Media.ALBUM_ID + "=149"
        lvSong.setAdapter(songAdapter);
        return view;
    }
}