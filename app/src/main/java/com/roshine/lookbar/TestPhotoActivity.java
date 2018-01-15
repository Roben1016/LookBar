package com.roshine.lookbar;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.roshine.lookbar.mvp.base.BaseActivity;

import butterknife.BindView;

/**
 * @author Roshine
 * @date 2018/1/15 20:46
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class TestPhotoActivity extends BaseActivity {
    @BindView(R.id.rv_test_photo)
    RecyclerView mRecycleView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_photo;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
    }
}
