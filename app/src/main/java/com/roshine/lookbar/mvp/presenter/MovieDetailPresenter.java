package com.roshine.lookbar.mvp.presenter;

import android.support.annotation.Nullable;

import com.roshine.lookbar.http.RetrofitClient;
import com.roshine.lookbar.http.RetrofitHelper;
import com.roshine.lookbar.http.RxSubUtil;
import com.roshine.lookbar.mvp.base.IBasePresenter;
import com.roshine.lookbar.mvp.bean.movie.MovieDetailBean;
import com.roshine.lookbar.mvp.contract.ContractUtil;

/**
 * @author Roshine
 * @date 2017/8/25 17:17
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class MovieDetailPresenter extends IBasePresenter<ContractUtil.IMovieDetailView> implements ContractUtil.IMovieDetailPresenter {
    @Override
    public void getMovieDetail(String id) {
        compositeDisposable.add(RetrofitClient.getInstance()
                .getApiService()
                .getMovieDetail(id)
                .compose(RetrofitHelper.handleResult())
                .subscribeWith(new RxSubUtil<MovieDetailBean>(compositeDisposable) {
                    @Override
                    protected void onSuccess(MovieDetailBean movieDetailBean) {
                        getView().loadSuccess(movieDetailBean);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        getView().loadFail(errorMsg);
                    }
                }));
    }

    @Nullable
    @Override
    public void loadSuccess(MovieDetailBean datas) {

    }

    @Override
    public void loadFail(String message) {

    }
}
