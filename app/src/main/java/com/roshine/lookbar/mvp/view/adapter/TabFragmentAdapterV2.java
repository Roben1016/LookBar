package com.roshine.lookbar.mvp.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.roshine.lookbar.mvp.view.book.BookNormalFragment;

import java.util.List;

/**
 * @author Roshine
 * @date 2017/8/28 13:50
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class TabFragmentAdapterV2 extends FixedPagerAdapter<String> {
    private List<String> mTitles;
    public TabFragmentAdapterV2(FragmentManager fragmentManager,List<String> mTitles) {
        super(fragmentManager);
        this.mTitles = mTitles;
    }

    @Override
    public String getItemData(int position) {
        return position < mTitles.size()?mTitles.get(position):null;
    }

    @Override
    public int getDataPosition(String s) {
        return mTitles.indexOf(s);
    }

    @Override
    public boolean equals(String oldD, String newD) {
        return TextUtils.equals(oldD, newD);
    }

    @Override
    public Fragment getItem(int position) {
        return BookNormalFragment.newInstanse(mTitles.get(position));
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
