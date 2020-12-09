package vn.poly.mxmusic.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vn.poly.mxmusic.R;
import vn.poly.mxmusic.adapter.AlbumAdapter;
import vn.poly.mxmusic.loader.AlbumLoader;
import vn.poly.mxmusic.model.AlbumModel;


public class AlbumFragment extends Fragment {
    ListView lvAlbum;
    AlbumAdapter albumAdapter;
    AlbumLoader albumLoader;
    EditText edtSearchAlbum;
    List<AlbumModel> list;
    List<AlbumModel> listSearch;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        lvAlbum = view.findViewById(R.id.lvAlbum);
        albumLoader = new AlbumLoader();
        edtSearchAlbum = view.findViewById(R.id.edtSearchAlbum);
        listSearch = new ArrayList<>();
        list = albumLoader.getAllAlbum(Objects.requireNonNull(getContext()));
        albumAdapter = new AlbumAdapter(Objects.requireNonNull(getActivity()), list);
        lvAlbum.setAdapter(albumAdapter);

        edtSearchAlbum.addTextChangedListener(new TextWatcher() {
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
                    if (list.get(i).getName().toLowerCase().contains(edtSearchAlbum.getText().toString())) {
                        listSearch.add(list.get(i));
                    }
                }
                albumAdapter = new AlbumAdapter(Objects.requireNonNull(getActivity()), listSearch);
                lvAlbum.setAdapter(albumAdapter);
            }
        });
        return view;
    }
}