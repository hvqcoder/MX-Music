package com.example.mxmusic.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mxmusic.R;
import com.example.mxmusic.adapter.AlbumAdapter;
import com.example.mxmusic.adapter.SongAdapter;
import com.example.mxmusic.loader.AlbumLoader;
import com.example.mxmusic.loader.SongLoader;
import com.example.mxmusic.model.AlbumModel;

import java.util.List;

import static android.bluetooth.BluetoothGattCharacteristic.PERMISSION_READ;


public class AlbumFragment extends Fragment {
    ListView lvAlbum;
    AlbumAdapter albumAdapter;
    AlbumLoader albumLoader;
    public static AlbumFragment newInstance() {
        AlbumFragment fragment = new AlbumFragment();
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
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        lvAlbum = view.findViewById(R.id.lvAlbum);
        albumLoader = new AlbumLoader();
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ);
        }
        albumAdapter = new AlbumAdapter(getActivity(), albumLoader.getAllAlbum(getContext()));//+" and "+ MediaStore.Audio.Media.ALBUM_ID + "=149"
        lvAlbum.setAdapter(albumAdapter);
        return view;
    }
}