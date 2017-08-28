package com.roshine.lookbar.mvp.view.movie;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.roshine.lookbar.R;
import com.roshine.lookbar.mvp.view.adapter.TabFragmentAdapter;
import com.roshine.lookbar.mvp.base.BasePageFragment;
import com.roshine.lookbar.utils.ThemeColorUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Roshine
 * @date 2017/8/23 19:16
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class HomeMovieFragment extends BasePageFragment {

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private MovieTop250Fragment movieTop250Fragment;
    private MovieLivingFragment movieLivingFragment;
    private MovieComingSoonFragment movieComingSoonFragment;

    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_movie;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        initViewPager();

    }

    private void initViewPager() {
        String[] tabTitle = getActivity().getResources().getStringArray(R.array.movie_tab);
        for (int i = 0; i < tabTitle.length; i++) {
            titles.add(tabTitle[i]);
        }
        movieTop250Fragment = MovieTop250Fragment.newInstanse();
        movieComingSoonFragment = MovieComingSoonFragment.newInstanse();
        movieLivingFragment = MovieLivingFragment.newInstanse();

        fragments.add(movieLivingFragment);
        fragments.add(movieComingSoonFragment);
        fragments.add(movieTop250Fragment);
        TabFragmentAdapter adapter = new TabFragmentAdapter(getChildFragmentManager(),fragments,titles);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(2);

        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        tablayout.setSelectedTabIndicatorColor(getActivity().getResources().getColor(ThemeColorUtil.getThemeColor()));
//        tablayout.setTabTextColors(getResources().getColor(R.color.text_gray_6),ThemeUtils.getThemeColor());
        tablayout.setTabTextColors(getResources().getColor(R.color.gray_lighter),getActivity().getResources().getColor(ThemeColorUtil.getThemeColor()));
        // 将TabLayout和ViewPager进行关联，让两者联动起来
        tablayout.setupWithViewPager(viewpager);
        // 设置Tablayout的Tab显示ViewPager的适配器中的getPageTitle函数获取到的标题
        tablayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    public void loadNetData() {
    }

    @Override
    public void onPageStart() {

    }

    @Override
    public void onPageEnd() {

    }

    public static Fragment newInstance() {
        return new HomeMovieFragment();
    }
}
