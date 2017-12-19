package com.roshine.lookbar.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.roshine.lookbar.R;
import com.roshine.lookbar.mvp.base.BaseActivity;
import com.roshine.lookbar.utils.LogUtil;
import com.roshine.lookbar.utils.StatusBarUtil;
import com.roshine.lookbar.utils.ThemeColorUtil;
import com.roshine.lookbar.utils.Util;

import butterknife.BindView;

/**
 * @author Roshine
 * @date 2017/8/28 21:18
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 关于
 */
public class AboutActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.tv_mail)
    TextView tvMail;
    @BindView(R.id.tv_github)
    TextView tvGithub;
    @BindView(R.id.tv_blog)
    TextView tvBlog;
    @BindView(R.id.rootLayout)
    CoordinatorLayout rootLayout;
    @BindView(R.id.iv_me_icon)
    ImageView ivMeIcon;
    @BindView(R.id.tv_my_name)
    TextView tvMyName;
    @BindView(R.id.tv_my_title)
    TextView tvLike;
//    @BindView(R.id.iv_back)
//    ImageView ivBack;
//    @BindView(R.id.tv_title)
//    TextView tvTitle;
//    @BindView(R.id.rl_tool_bar)
//    RelativeLayout rlToolBar;
    private int mMaxScrollSize;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setColorBar(this,getResources().getColor(ThemeColorUtil.getThemeColor()));
        Util.KITKAT(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
//        tvTitle.setVisibility(View.VISIBLE);
//        ivBack.setVisibility(View.VISIBLE);
//        tvTitle.setText("关于");
//        rlToolBar.setBackgroundColor(getResources().getColor(ThemeColorUtil.getThemeColor()));
//        rlToolBar.setAlpha(0);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.iv_back));
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mMaxScrollSize == 0) {
            mMaxScrollSize = appBarLayout.getTotalScrollRange();
        }
        int currentScrollPercentage = (Math.abs(verticalOffset)) * 100
                / mMaxScrollSize;
        float alpha = (float) (1 - currentScrollPercentage / 100.0);
        tvMyName.setAlpha(alpha);
        ivMeIcon.setAlpha(alpha);
        tvLike.setAlpha(alpha);
//        collapsingToolbarLayout.setAlpha(alpha);
//        rlToolBar.setAlpha(1 - alpha);
//        LogUtil.showD("Roshine","alpha:"+alpha);
//        attentionNumTv.setAlpha(alpha);
//        detailTv.setAlpha(alpha);
    }
}
