package vn.poly.mxmusic.fragment;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.Objects;

import vn.poly.mxmusic.R;
import vn.poly.mxmusic.adapter.SongAdapter;
import vn.poly.mxmusic.dao.FavouriteDao;
import vn.poly.mxmusic.loader.SongLoader;
import vn.poly.mxmusic.model.SongModel;


public class FavouriteFragment extends Fragment {

    ListView lvFavourite;
    SongAdapter songAdapter;
    SongLoader songLoader;
    List<SongModel> favouriteModelList;
    FavouriteDao favouriteDao;

    public static FavouriteFragment newInstance() {
        FavouriteFragment fragment = new FavouriteFragment();
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
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        songLoader = new SongLoader();
        favouriteDao = new FavouriteDao(Objects.requireNonNull(getContext()));
        List<String> listID = favouriteDao.getAllFavourite();
        if (listID.size() > 0) {
            String _where = " and _id = " + listID.get(0);
            for (int i = 1; i < listID.size(); i++) {
                _where += (" or _id = " + listID.get(i));
            }
            favouriteModelList = songLoader.getAllSong(getContext(), MediaStore.Audio.Media.IS_MUSIC + "=1" + _where);
            lvFavourite = view.findViewById(R.id.lvFavouriteSong);
            songAdapter = new SongAdapter(Objects.requireNonNull(getActivity()), favouriteModelList);
            lvFavourite.setAdapter(songAdapter);
        }
        return view;
    }
}