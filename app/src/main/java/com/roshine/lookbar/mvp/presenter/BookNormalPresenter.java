package com.roshine.lookbar.mvp.presenter;

import android.support.annotation.Nullable;

import com.roshine.lookbar.http.RetrofitClient;
import com.roshine.lookbar.http.RetrofitHelper;
import com.roshine.lookbar.http.RxSubUtil;
import com.roshine.lookbar.mvp.base.IBasePresenter;
import com.roshine.lookbar.mvp.bean.book.BookBean;
import com.roshine.lookbar.mvp.contract.ContractUtil;

/**
 * @author Roshine
 * @date 2017/8/25 22:51
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class BookNormalPresenter extends IBasePresenter<ContractUtil.IBookNormalView> implements ContractUtil.IBookNormalPresenter{


    @Nullable
    @Override
    public void loadSuccess(BookBean datas) {

    }

    @Override
    public void loadFail(String message) {

    }

    @Override
    public void getBooks(String tag, int start, int count) {
        compositeDisposable.add(RetrofitClient.getInstance()
        .getApiService()
        .getBookByTag(tag,start,count)
        .compose(RetrofitHelper.handleResult())
        .subscribeWith(new RxSubUtil<BookBean>(compositeDisposable) {
            @Override
            protected void onSuccess(BookBean bookBean) {
                getView().loadSuccess(bookBean);
            }

            @Override
            protected void onFail(String errorMsg) {
                getView().loadFail(errorMsg);
            }
        }));
    }
}
