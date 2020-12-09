package vn.poly.mxmusic.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import vn.poly.mxmusic.fragment.AlbumFragment;
import vn.poly.mxmusic.fragment.FavouriteFragment;
import vn.poly.mxmusic.fragment.SingerFragment;
import vn.poly.mxmusic.fragment.SongFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new AlbumFragment();
            case 2:
                return new SingerFragment();
            case 3:
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
        switch (position) {
            case 0:
                title = "Bài Hát";
                break;
            case 1:
                title = "Album";
                break;
            case 2:
                title = "Ca Sĩ";
                break;
            case 3:
                title = "Yêu Thích";
                break;
        }
        return title;
    }
}
