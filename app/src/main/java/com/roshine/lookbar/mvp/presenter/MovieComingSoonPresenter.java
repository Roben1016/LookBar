package com.roshine.lookbar.mvp.presenter;

import android.support.annotation.Nullable;

import com.roshine.lookbar.http.RetrofitClient;
import com.roshine.lookbar.http.RetrofitHelper;
import com.roshine.lookbar.http.RxSubUtil;
import com.roshine.lookbar.mvp.base.IBasePresenter;
import com.roshine.lookbar.mvp.bean.movie.MovieBean;
import com.roshine.lookbar.mvp.contract.ContractUtil;

/**
 * @author Roshine
 * @date 2017/8/25 11:45
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class MovieComingSoonPresenter extends IBasePresenter<ContractUtil.IComingSoonView> implements ContractUtil.IComingSoonPresenter {


    @Override
    public void getComingSoonMovie(int start, int count) {
        compositeDisposable.add(RetrofitClient.getInstance()
                .getApiService()
                .getComingSoonMovie(start,count)
                .compose(RetrofitHelper.handleResult())
                .subscribeWith(new RxSubUtil<MovieBean>(compositeDisposable) {
                    @Override
                    protected void onSuccess(MovieBean movieBean) {
                        getView().loadSuccess(movieBean);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        getView().loadFail(errorMsg);
                    }
                }));
    }

    @Nullable
    @Override
    public void loadSuccess(MovieBean datas) {

    }

    @Override
    public void loadFail(String message) {

    }
}
