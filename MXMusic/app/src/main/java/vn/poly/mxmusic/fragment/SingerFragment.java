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
import vn.poly.mxmusic.adapter.SingerAdapter;
import vn.poly.mxmusic.loader.SingerLoader;
import vn.poly.mxmusic.model.SingerModel;


public class SingerFragment extends Fragment {
    ListView lvSinger;
    SingerAdapter singerAdapter;
    SingerLoader singerLoader;
    EditText edtSearchSinger;
    List<SingerModel> list;
    List<SingerModel> listSearch;

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
        edtSearchSinger = view.findViewById(R.id.edtSearchSinger);
        listSearch = new ArrayList<>();
        list = singerLoader.getAllSinger(Objects.requireNonNull(getContext()));

        singerAdapter = new SingerAdapter(Objects.requireNonNull(getActivity()), list);
        lvSinger.setAdapter(singerAdapter);

        edtSearchSinger.addTextChangedListener(new TextWatcher() {
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
                    if (list.get(i).getName().toLowerCase().contains(edtSearchSinger.getText().toString())) {
                        listSearch.add(list.get(i));
                    }
                }
                singerAdapter = new SingerAdapter(Objects.requireNonNull(getActivity()), listSearch);
                lvSinger.setAdapter(singerAdapter);
            }
        });
        return view;
    }
}