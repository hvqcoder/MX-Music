package vn.poly.mxmusic.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vn.poly.mxmusic.R;
import vn.poly.mxmusic.adapter.SongAdapter;
import vn.poly.mxmusic.loader.SongLoader;
import vn.poly.mxmusic.model.SongModel;

import static android.bluetooth.BluetoothGattCharacteristic.PERMISSION_READ;


public class SongFragment extends Fragment {

    ListView lvSong;
    SongAdapter songAdapter;
    SongLoader songLoader;
    EditText edtSearchSong;
    List<SongModel> list;
    List<SongModel> listSearch;

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
        edtSearchSong = view.findViewById(R.id.edtSearchSong);
        songLoader = new SongLoader();
        listSearch = new ArrayList<>();

        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ);
        }
        list = songLoader.getAllSong(getContext(), MediaStore.Audio.Media.IS_MUSIC + "=1");

        songAdapter = new SongAdapter(Objects.requireNonNull(getActivity()), list);
        lvSong.setAdapter(songAdapter);

        edtSearchSong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i0, int i1, int i2) {
                listSearch.clear();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getTitle().toLowerCase().contains(edtSearchSong.getText().toString())) {
                        listSearch.add(list.get(i));
                    }
                }
                songAdapter = new SongAdapter(Objects.requireNonNull(getActivity()), listSearch);
                lvSong.setAdapter(songAdapter);

                edtSearchSong.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i0, int i1, int i2) {
                        listSearch.clear();
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getTitle().toLowerCase().contains(edtSearchSong.getText().toString())) {
                                listSearch.add(list.get(i));
                            }
                        }
                        songAdapter = new SongAdapter(getActivity(), listSearch);
                        lvSong.setAdapter(songAdapter);
                    }
                });
            }
        });
        return view;
    }
}