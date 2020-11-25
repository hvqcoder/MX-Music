package com.example.mxmusic.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mxmusic.fragment.AlbumFragment;
import com.example.mxmusic.fragment.FavouriteFragment;
import com.example.mxmusic.fragment.SingerFragment;
import com.example.mxmusic.fragment.SongFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return new SongFragment();
            case 1 :
                return new SingerFragment();
            case 2 :
                return new AlbumFragment();
            case 3 :
                return new FavouriteFragment();
            default:
                return new SongFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Bài Hát";
                break;
            case 1:
                title = "Ca Sĩ";
                break;
            case 2:
                title = "Album";
                break;
            case 3:
                title = "Yêu Thích";
                break;
        }
        return title;
    }
}
