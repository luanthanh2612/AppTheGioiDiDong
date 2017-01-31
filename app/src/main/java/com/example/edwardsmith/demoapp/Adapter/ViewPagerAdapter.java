package com.example.edwardsmith.demoapp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EdwardSmith on 1/17/17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> listFrag = new ArrayList<>();
    private List<String> mFragmentTitle = new ArrayList<>();
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return listFrag.get(position);
    }

    @Override
    public int getCount() {
        return listFrag.size();
    }

    public void addFragment(Fragment fragment,String title){
        listFrag.add(fragment);
        mFragmentTitle.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitle.get(position);
    }
}
