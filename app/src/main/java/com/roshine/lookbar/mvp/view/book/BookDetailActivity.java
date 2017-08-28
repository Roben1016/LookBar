package com.roshine.lookbar.mvp.view.book;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.roshine.lookbar.R;
import com.roshine.lookbar.imageloader.ImageLoaderManager;
import com.roshine.lookbar.mvp.base.MvpBaseActivity;
import com.roshine.lookbar.mvp.bean.book.Books;
import com.roshine.lookbar.mvp.contract.ContractUtil;
import com.roshine.lookbar.mvp.presenter.BookDetailPresenter;
import com.roshine.lookbar.mvp.view.WebViewActivity;
import com.roshine.lookbar.utils.DisplayUtil;
import com.roshine.lookbar.utils.LogUtil;
import com.roshine.lookbar.utils.ThemeColorUtil;
import com.roshine.lookbar.wight.recyclerview.FullyLinearLayoutManager;
import com.roshine.lookbar.wight.recyclerview.base.SimpleRecyclertViewAdater;
import com.roshine.lookbar.wight.recyclerview.base.ViewHolder;
import com.roshine.lookbar.wight.recyclerview.normal.NormalRecyclertView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Roshine
 * @date 2017/8/26 11:45
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class BookDetailActivity extends MvpBaseActivity<ContractUtil.IBookDetailView, BookDetailPresenter> implements ContractUtil.IBookDetailView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tb_base_tool_bar)
    Toolbar tbBaseToolBar;
    @BindView(R.id.iv_book_pic)
    ImageView ivBookPic;
    @BindView(R.id.tv_book_name)
    TextView tvBookName;
    @BindView(R.id.tv_book_year)
    TextView tvBookYear;
    @BindView(R.id.tv_book_publisher)
    TextView tvBookPublisher;
    @BindView(R.id.tv_book_score)
    TextView tvBookScore;
    @BindView(R.id.tv_book_introduce)
    TextView tvBookIntroduce;
    @BindView(R.id.tv_author_introduce)
    TextView tvAuthorIntroduce;
    @BindView(R.id.recyclerview)
    NormalRecyclertView recyclerview;
    @BindView(R.id.btn_get_more)
    Button btnGetMore;
    @BindView(R.id.tv_book_author)
    TextView tvBookAuthor;
    @BindView(R.id.tv_catalog_null)
    TextView tvCatalogNull;
    private BookDetailPresenter presenter;
    private String id = "";
    private List<String> listData = new ArrayList<>();
    private SimpleRecyclertViewAdater<String> mAdapter;

    private Books currentData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            id = intent.getStringExtra("id");
            presenter.getBookDetail(id);
        }
    }

    @Override
    public BookDetailPresenter getPresenter() {
        presenter = new BookDetailPresenter();
        return presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        tbBaseToolBar.setBackgroundColor(getResources().getColor(ThemeColorUtil.getThemeColor()));
        btnGetMore.setBackgroundColor(getResources().getColor(ThemeColorUtil.getThemeColor()));
        initRecyclerView();
    }

    private void initRecyclerView() {
        //防止ScrollView和RecyclerView滑动冲突
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this){
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };
        FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        mAdapter = new SimpleRecyclertViewAdater<String>(this, listData, R.layout.item_book_detail_catalog) {
            @Override
            protected void onBindViewHolder(ViewHolder holder, int itemType, String itemBean, int position) {
                holder.setText(R.id.tv_item_book_catalog, itemBean);
            }
        };
        DividerItemDecoration decoration = new DividerItemDecoration(this,LinearLayoutManager.VERTICAL);
        recyclerview.addItemDecoration(decoration);
        recyclerview.setAdapter(mAdapter);
    }

    @Nullable
    @Override
    public void loadSuccess(Books datas) {
        if (datas != null) {
            currentData = datas;
            setDatas(datas);
        }

    }

    private void setDatas(Books datas) {
        tvTitle.setText(datas.getTitle());
        tvAuthorIntroduce.setText(datas.getAuthor_intro());
        tvBookIntroduce.setText(datas.getSummary());
        tvBookName.setText(datas.getTitle());
        tvBookPublisher.setText(datas.getPublisher());
        tvBookScore.setText(String.format(getResources().getString(R.string.book_score_text), datas.getRating().getAverage()));
        tvBookYear.setText(String.format(getResources().getString(R.string.book_publish_year), datas.getPubdate()));

        ViewGroup.LayoutParams params = ivBookPic.getLayoutParams();
        int ivHeight = DisplayUtil.dp2px(this, 150);
        params.height = ivHeight;
        double ivWidth = ivHeight * 300.0 / 444.0;
        params.width = (int) ivWidth;
        ivBookPic.setLayoutParams(params);
        ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions(ivBookPic, datas.getImages().getLarge()));

        initAuthors(datas);

        initCatalogs(datas);
    }

    private void initAuthors(Books datas) {
        List<String> author = datas.getAuthor();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < author.size(); i++) {
            stringBuffer.append(author.get(i)).append("、");
        }
        tvBookAuthor.setText(stringBuffer.length() >= 1 ? stringBuffer.substring(0, stringBuffer.length() - 1) : stringBuffer.toString());
    }

    private void initCatalogs(Books datas) {
        String catalog = datas.getCatalog();
        if(catalog != null && catalog.length() > 0){
            tvCatalogNull.setVisibility(View.GONE);
            recyclerview.setVisibility(View.VISIBLE);
            if(catalog.contains("\r\n")){
                String[] split = catalog.split("\r\n");
                for (int i = 0; i < split.length; i++) {
                    listData.add(split[i]);
                }
            } else if(catalog.contains("\n")){
                String[] split = catalog.split("\n");
                for (int i = 0; i < split.length; i++) {
                    listData.add(split[i]);
                }
            } else {
                listData.add(catalog);
            }
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }else{
            tvCatalogNull.setVisibility(View.VISIBLE);
            recyclerview.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadFail(String message) {
        LogUtil.showD("Roshine","加载失败");
        toast(message);
    }

    @OnClick({R.id.iv_back, R.id.btn_get_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_get_more:
                if (currentData != null) {
                    String url = currentData.getAlt();
                    String name = currentData.getTitle();
                    Bundle bundle = new Bundle();
                    bundle.putString("url",url);
                    bundle.putString("title",name);
                    startActivity(WebViewActivity.class,bundle);
                }
                break;
        }
    }
}

