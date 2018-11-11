package com.example.linhongyu.smarter;



import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linhongyu on 26/4/18.
 */

public class ReportPage extends FragmentStatePagerAdapter {


    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitle = new ArrayList<>();

    public ReportPage(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position){

        return  mFragmentTitle.get(position);
    }

    public void addFragment(Fragment fragment, String title){

        mFragmentList.add(fragment);
        mFragmentTitle.add(title);
    }

    @Override
    public Fragment getItem(int position) {

        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {

        return mFragmentList.size();
    }
}
