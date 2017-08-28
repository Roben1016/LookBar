package com.roshine.lookbar.mvp.presenter;

import android.support.annotation.Nullable;

import com.roshine.lookbar.http.RetrofitClient;
import com.roshine.lookbar.http.RetrofitHelper;
import com.roshine.lookbar.http.RxSubUtil;
import com.roshine.lookbar.mvp.base.IBasePresenter;
import com.roshine.lookbar.mvp.bean.music.Musics;
import com.roshine.lookbar.mvp.contract.ContractUtil;

/**
 * @author Roshine
 * @date 2017/8/28 16:44
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class MusicDetailPresenter extends IBasePresenter<ContractUtil.IMusicDetailView> implements ContractUtil.IMusicDetailPresenter{
    @Override
    public void getMusicDetailById(String id) {
        compositeDisposable.add(RetrofitClient.getInstance().getApiService()
                .getMusicDetail(id)
                .compose(RetrofitHelper.handleResult())
                .subscribeWith(new RxSubUtil<Musics>(compositeDisposable) {
                    @Override
                    protected void onSuccess(Musics musics) {
                        getView().loadSuccess(musics);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        getView().loadFail(errorMsg);
                    }
                }));
    }

    @Nullable
    @Override
    public void loadSuccess(Musics datas) {

    }

    @Override
    public void loadFail(String message) {

    }
}
