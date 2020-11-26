package com.example.mxmusic.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mxmusic.R;
import com.example.mxmusic.adapter.AlbumAdapter;
import com.example.mxmusic.adapter.SingerAdapter;
import com.example.mxmusic.loader.AlbumLoader;
import com.example.mxmusic.loader.SingerLoader;

import static android.bluetooth.BluetoothGattCharacteristic.PERMISSION_READ;


public class SingerFragment extends Fragment {
    ListView lvSinger;
    SingerAdapter singerAdapter;
    SingerLoader singerLoader;
    public static SingerFragment newInstance() {
        SingerFragment fragment = new SingerFragment();
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
        View view = inflater.inflate(R.layout.fragment_singer, container, false);
        lvSinger = view.findViewById(R.id.lvSinger);
        singerLoader = new SingerLoader();
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ);
        }
        singerAdapter = new SingerAdapter(getActivity(), singerLoader.getAllSinger(getContext()));//+" and "+ MediaStore.Audio.Media.ALBUM_ID + "=149"
        lvSinger.setAdapter(singerAdapter);
        return view;
    }
}