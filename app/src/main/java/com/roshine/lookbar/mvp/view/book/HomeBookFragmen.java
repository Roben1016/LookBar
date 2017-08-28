package com.roshine.lookbar.mvp.view.book;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.roshine.lookbar.R;
import com.roshine.lookbar.constants.Constants;
import com.roshine.lookbar.mvp.base.BasePageFragment;
import com.roshine.lookbar.mvp.view.adapter.TabFragmentAdapterV2;
import com.roshine.lookbar.utils.SPUtil;
import com.roshine.lookbar.utils.ThemeColorUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Roshine
 * @date 2017/8/24 16:59
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class HomeBookFragmen extends BasePageFragment {

    private static final int GET_TAG_REQUEST_CODE = 100;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    private List<String> listContainerTags = new ArrayList<>();
    private List<String> listUnContainerTags = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    private TabFragmentAdapterV2 adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_book;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        floatingActionButton.setBackgroundTintList(getActivity().getResources().getColorStateList(ThemeColorUtil.getNavigationViewItemColor()));
        checkDatas();
    }

    private void checkDatas() {
        if ((int) SPUtil.getParam(Constants.SharedPreferancesKeys.CONTAINER_TAGS_COUNT, 0) == 0) {
            StringBuffer stringBuffer = new StringBuffer();
            StringBuffer stringBuffer1 = new StringBuffer();
            String[] defaultContainerTags = Constants.Tag.DEFAULT_CONTAINER_TAGS;
            String[] defaultUncontainerTags = Constants.Tag.DEFAULT_UNCONTAINER_TAGS;
            for (int i = 0; i < defaultContainerTags.length; i++) {
                listContainerTags.add(defaultContainerTags[i]);
                stringBuffer.append(defaultContainerTags[i]).append(",");
            }
            for (int i = 0; i < defaultUncontainerTags.length; i++) {
                listUnContainerTags.add(defaultUncontainerTags[i]);
                stringBuffer1.append(defaultUncontainerTags[i]).append(",");
            }
            SPUtil.setParam(Constants.SharedPreferancesKeys.CONTAINER_TAGS, stringBuffer.subSequence(0, stringBuffer.length() - 1));
            SPUtil.setParam(Constants.SharedPreferancesKeys.UNCONTAINER_TAGS, stringBuffer1.subSequence(0, stringBuffer1.length() - 1));
            SPUtil.setParam(Constants.SharedPreferancesKeys.CONTAINER_TAGS_COUNT, defaultContainerTags.length);
            SPUtil.setParam(Constants.SharedPreferancesKeys.UNCONTAINER_TAGS_COUNT, defaultUncontainerTags.length);
        } else if ((int) SPUtil.getParam(Constants.SharedPreferancesKeys.CONTAINER_TAGS_COUNT, 0) == 1) {
            String param = (String) SPUtil.getParam(Constants.SharedPreferancesKeys.CONTAINER_TAGS, "");
            listContainerTags.add(param);
        } else {
            String params = (String) SPUtil.getParam(Constants.SharedPreferancesKeys.CONTAINER_TAGS, "");
            String[] strings = params.split(",");
            for (int i = 0; i < strings.length; i++) {
                listContainerTags.add(strings[i]);
            }
        }
        initTabLayout();
    }

    private void initTabLayout() {
        adapter = new TabFragmentAdapterV2(getChildFragmentManager(),listContainerTags);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(listContainerTags.size());

        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tablayout.setSelectedTabIndicatorColor(getActivity().getResources().getColor(ThemeColorUtil.getThemeColor()));
        tablayout.setTabTextColors(getResources().getColor(R.color.gray_lighter), getActivity().getResources().getColor(ThemeColorUtil.getThemeColor()));
        // 将TabLayout和ViewPager进行关联，让两者联动起来
        tablayout.setupWithViewPager(viewpager);
        // 设置Tablayout的Tab显示ViewPager的适配器中的getPageTitle函数获取到的标题
//        tablayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GET_TAG_REQUEST_CODE){
            if(resultCode == AppCompatActivity.RESULT_OK){
                if (data != null) {
                    if (listContainerTags != null && fragments != null) {
                        listContainerTags.clear();
                        fragments.clear();
                        listContainerTags.addAll((List<String>)data.getSerializableExtra("containerTags"));
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    private void initFragments() {
        for (int i = 0; i < listContainerTags.size(); i++) {
            fragments.add(BookNormalFragment.newInstanse(listContainerTags.get(i)));
        }
    }

    @Override
    public void onPageStart() {

    }

    @Override
    public void onPageEnd() {

    }

    @Override
    public void loadNetData() {

    }

    public static Fragment newInstance() {
        return new HomeBookFragmen();
    }

    @OnClick(R.id.floatingActionButton)
    public void onViewClicked() {
        startActivityForResult(BookTagActivity.class,null,GET_TAG_REQUEST_CODE);
    }
}
