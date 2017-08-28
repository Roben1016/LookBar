package com.roshine.lookbar.mvp.view.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * TabFragmentAdapter
 *
 * @author Administrator
 * @extends FragmentPagerAdapter
 */
public class TabFragmentAdapter extends FragmentStatePagerAdapter {

    private List<String> mTitles;
    private List<Fragment> fragments;
    FragmentManager fm;

    public TabFragmentAdapter(FragmentManager fm, List<Fragment> list, List<String> mTitles) {
        super(fm);
        this.fragments = list;
        this.fm = fm;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int arg0) {
        return fragments.get(arg0);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    public void setFragments(List<Fragment> fragments) {
//        if (this.fragments != null) {
//            FragmentTransaction ft = fm.beginTransaction();
//            for (Fragment f : this.fragments) {
//                ft.remove(f);
//            }
//            ft.commit();
//            ft = null;
//            fm.executePendingTransactions();
//        }
//        this.fragments = fragments;
//        notifyDataSetChanged();

        if(this.fragments != null){
            FragmentTransaction ft = fm.beginTransaction();
            for(Fragment f:this.fragments){
                ft.remove(f);
            }
            ft.commit();
            ft=null;
            fm.executePendingTransactions();
        }
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    public void setmTitles(List<String> mTitles) {
        this.mTitles = mTitles;
        notifyDataSetChanged();
    }
}