package com.roshine.lookbar.mvp.presenter;

import android.support.annotation.Nullable;

import com.roshine.lookbar.http.RetrofitClient;
import com.roshine.lookbar.http.RetrofitHelper;
import com.roshine.lookbar.http.RxSubUtil;
import com.roshine.lookbar.mvp.base.IBasePresenter;
import com.roshine.lookbar.mvp.bean.book.Books;
import com.roshine.lookbar.mvp.contract.ContractUtil;

/**
 * @author Roshine
 * @date 2017/8/26 11:46
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class BookDetailPresenter extends IBasePresenter<ContractUtil.IBookDetailView> implements ContractUtil.IBookDetailPresenter{

    @Override
    public void getBookDetail(String id) {
        compositeDisposable.add(RetrofitClient.getInstance().getApiService()
                .getBookDetail(id)
                .compose(RetrofitHelper.handleResult())
                .subscribeWith(new RxSubUtil<Books>(compositeDisposable) {
                    @Override
                    protected void onSuccess(Books books) {
                        getView().loadSuccess(books);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        getView().loadFail(errorMsg);
                    }
                }));
    }

    @Nullable
    @Override
    public void loadSuccess(Books datas) {

    }

    @Override
    public void loadFail(String message) {

    }
}
