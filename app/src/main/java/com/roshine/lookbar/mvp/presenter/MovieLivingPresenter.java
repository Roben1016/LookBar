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
 * @date 2017/8/24 21:18
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class MovieLivingPresenter extends IBasePresenter<ContractUtil.ILivingMovieView> implements ContractUtil.ILivingMoviePresenter {
    @Nullable
    @Override
    public void loadSuccess(MovieBean datas) {

    }

    @Override
    public void loadFail(String message) {

    }

    @Override
    public void getLivingMovie() {
        compositeDisposable.add(RetrofitClient.getInstance()
                .getApiService()
                .getLiveMovie()
                .compose(RetrofitHelper.handleResult())
                .subscribeWith(new RxSubUtil<MovieBean>(compositeDisposable) {
                    @Override
                    protected void onSuccess(MovieBean movieLivingBean) {
                        getView().loadSuccess(movieLivingBean);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        getView().loadFail(errorMsg);
                    }
                }));
    }
}
