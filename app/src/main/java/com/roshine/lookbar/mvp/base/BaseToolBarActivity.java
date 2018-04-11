package com.roshine.lookbar.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.roshine.lookbar.utils.StatusBarUtil;
import com.roshine.lookbar.utils.ThemeColorUtil;

/**
 * @author Roshine
 * @date 2017/7/29 16:28
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 带toolbar的activity,设置沉浸式状态栏
 */
public abstract class BaseToolBarActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(setStatusBar()){
            StatusBarUtil.setColorBar(this, getResources().getColor(ThemeColorUtil.getThemeColor()));
        }
    }

    protected boolean setStatusBar(){
        return true;
    }
}
