package vn.poly.mxmusic.activity;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import vn.poly.mxmusic.R;
import vn.poly.mxmusic.adapter.PagerAdapter;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide toolbar
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).hide();
        setContentView(R.layout.activity_main);
        
        TabLayout mtabLayout = findViewById(R.id.tab_layout);
        ViewPager mviewPager = findViewById(R.id.viewPager);

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mviewPager.setAdapter(adapter);
        mtabLayout.setupWithViewPager(mviewPager);

    }
}