package com.roshine.lookbar.mvp.presenter;

import android.support.annotation.Nullable;

import com.roshine.lookbar.http.RetrofitClient;
import com.roshine.lookbar.http.RetrofitHelper;
import com.roshine.lookbar.http.RxSubUtil;
import com.roshine.lookbar.mvp.bean.movie.MovieBean;
import com.roshine.lookbar.mvp.contract.ContractUtil;
import com.roshine.lookbar.mvp.base.IBasePresenter;

/**
 * @author Roshine
 * @date 2017/8/24 15:26
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class MovieTop250Presenter extends IBasePresenter<ContractUtil.ITop250View> implements ContractUtil.ITop250Presenter {

    public MovieTop250Presenter() {
    }

    @Nullable
    @Override
    public void loadSuccess(MovieBean datas) {
        getView().loadSuccess(datas);
    }

    @Override
    public void loadFail(String message) {
        getView().loadFail(message);
    }

    @Override
    public void getTop250Movie(int start, int count) {
        compositeDisposable.add(
                RetrofitClient
                        .getInstance()
                        .getApiService()
                        .getTop250(start, count)
                        .compose(RetrofitHelper.handleResult())
                        .subscribeWith(new RxSubUtil<MovieBean>(compositeDisposable) {
                            @Override
                            protected void onSuccess(MovieBean movieTop250Bean) {
                                loadSuccess(movieTop250Bean);
                            }

                            @Override
                            protected void onFail(String errorMsg) {
                                loadFail(errorMsg);
                            }
                        }));
    }
}
