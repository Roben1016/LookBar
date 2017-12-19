package com.roshine.lookbar.mvp.view.movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.roshine.lookbar.R;
import com.roshine.lookbar.imageloader.ImageLoaderManager;
import com.roshine.lookbar.mvp.base.MvpBaseActivity;
import com.roshine.lookbar.mvp.bean.movie.Casts;
import com.roshine.lookbar.mvp.bean.movie.Directors;
import com.roshine.lookbar.mvp.bean.movie.MovieDetailBean;
import com.roshine.lookbar.mvp.bean.movie.MoviePeople;
import com.roshine.lookbar.mvp.contract.ContractUtil;
import com.roshine.lookbar.mvp.presenter.MovieDetailPresenter;
import com.roshine.lookbar.mvp.view.WebViewActivity;
import com.roshine.lookbar.utils.DisplayUtil;
import com.roshine.lookbar.utils.ThemeColorUtil;
import com.roshine.lookbar.wight.recyclerview.FullyLinearLayoutManager;
import com.roshine.lookbar.wight.recyclerview.decoration.SpacesItemDecoration;
import com.roshine.lookbar.wight.recyclerview.base.SimpleRecyclertViewAdater;
import com.roshine.lookbar.wight.recyclerview.base.ViewHolder;
import com.roshine.lookbar.wight.recyclerview.interfaces.OnItemClickListener;
import com.roshine.lookbar.wight.recyclerview.normal.NormalRecyclertView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Roshine
 * @date 2017/8/25 14:54
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class MovieDetailActivity extends MvpBaseActivity<ContractUtil.IMovieDetailView,MovieDetailPresenter> implements ContractUtil.IMovieDetailView, OnItemClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tb_base_tool_bar)
    Toolbar tbBaseToolBar;
    @BindView(R.id.iv_movie_pic)
    ImageView ivMoviePic;
    @BindView(R.id.tv_movie_name)
    TextView tvMovieName;
    @BindView(R.id.tv_movie_real_name)
    TextView tvMovieRealName;
    @BindView(R.id.tv_movie_year)
    TextView tvMovieYear;
    @BindView(R.id.tv_movie_genres)
    TextView tvMovieGenres;
    @BindView(R.id.tv_movie_country)
    TextView tvMovieCountry;
    @BindView(R.id.tv_movie_score)
    TextView tvMovieScore;
    @BindView(R.id.tv_introduce)
    TextView tvIntroduce;
    @BindView(R.id.recyclerview)
    NormalRecyclertView recyclerview;
    @BindView(R.id.btn_get_more)
    Button btnGetMore;


    private MovieDetailBean currentData;

    private String id;
    private MovieDetailPresenter presenter;
    private List<MoviePeople> peoples = new ArrayList<>();
    private SimpleRecyclertViewAdater<MoviePeople> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent.hasExtra("id")){
            id = intent.getStringExtra("id");
        }
        presenter.getMovieDetail(id);
    }

    @Override
    public MovieDetailPresenter getPresenter() {
        presenter = new MovieDetailPresenter();
        return presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_detail;
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
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this){
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };
        FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        mAdapter = new SimpleRecyclertViewAdater<MoviePeople>(this,peoples,R.layout.item_movie_detail_people) {
            @Override
            protected void onBindViewHolder(ViewHolder holder, int itemType, MoviePeople itemBean, int position) {

                holder.setText(R.id.tv_people_type,itemBean.getType() == 1 ? getResources().getString(R.string.director_text_v2):getResources().getString(R.string.case_text_v2));
                holder.setText(R.id.tv_people_name,itemBean.getName());
                ImageView imageView = holder.getView(R.id.iv_people_pic);
                ViewGroup.LayoutParams params=imageView.getLayoutParams();
                int ivHeight=DisplayUtil.dp2px(MovieDetailActivity.this,80);
                params.height= ivHeight;
                double ivWidth =ivHeight * 300.0 / 444.0;
                params.width=(int)ivWidth;
                imageView.setLayoutParams(params);
                ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions(imageView,itemBean.getAvatars().getSmall()));
            }
        };
        SpacesItemDecoration spacesItemDecoration=new SpacesItemDecoration(0,DisplayUtil.dp2px(this,15),0,0);
        recyclerview.addItemDecoration(spacesItemDecoration);
        recyclerview.setAdapter(mAdapter);
        recyclerview.setOnItemClick(this);
    }

    @OnClick({R.id.iv_back, R.id.btn_get_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finishActivity();
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

    @Nullable
    @Override
    public void loadSuccess(MovieDetailBean datas) {
        if (datas != null) {
            currentData = datas;
            setDatas(datas);
        }
    }

    @Override
    public void loadFail(String message) {
        toast(message);
    }

    private void setDatas(MovieDetailBean datas) {
        tvMovieName.setText(datas.getTitle());
        tvMovieRealName.setText(String.format(getResources().getString(R.string.real_name_text),datas.getOriginal_title()));
        tvIntroduce.setText(datas.getSummary());
        StringBuffer sb = new StringBuffer();
        List<String> countries = datas.getCountries();
        for (int i = 0; i < countries.size(); i++) {
            sb.append(countries.get(i)).append("、");
        }
        tvMovieCountry.setText(sb.length() >= 1?sb.substring(0,sb.length()-1):sb.toString());
        tvMovieScore.setText(datas.getRating().getAverage() == 0?getResources().getString(R.string.score_null):String.format(getResources().getString(R.string.score_text_v2),String.valueOf(datas.getRating().getAverage())));
        tvMovieYear.setText(String.format(getResources().getString(R.string.movie_year_text),datas.getYear()));
        List<String> genres = datas.getGenres();
        StringBuffer sb2 = new StringBuffer();
        for (int i = 0; i < genres.size(); i++) {
            sb2.append(genres.get(i)).append("、");
        }
        tvMovieGenres.setText(sb2.length() >=1?sb2.substring(0,sb2.length()-1):sb2.toString());

        ViewGroup.LayoutParams params=ivMoviePic.getLayoutParams();
        int ivHeight=DisplayUtil.dp2px(this,150);
        params.height= ivHeight;
        double ivWidth =ivHeight * 300.0 / 444.0;
        params.width=(int)ivWidth;
        ivMoviePic.setLayoutParams(params);
        ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions(ivMoviePic,datas.getImages().getLarge()));

        tvTitle.setText(datas.getTitle());
        getPeopleList(datas);
    }

    private void getPeopleList(MovieDetailBean datas) {
        List<Directors> directors = datas.getDirectors();
        for (int i = 0; i < directors.size(); i++) {
            Directors director = directors.get(i);
            MoviePeople people = new MoviePeople();
            people.setAlt(director.getAlt());
            people.setAvatars(director.getAvatars());
            people.setId(director.getId());
            people.setName(director.getName());
            people.setType(1);
            peoples.add(people);
        }

        List<Casts> casts = datas.getCasts();
        for (int i = 0; i < casts.size(); i++) {
            Casts cast = casts.get(i);
            MoviePeople people = new MoviePeople();
            people.setAlt(cast.getAlt());
            people.setAvatars(cast.getAvatars());
            people.setId(cast.getId());
            people.setName(cast.getName());
            people.setType(2);
            peoples.add(people);
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void OnItemClick(int position, ViewHolder holder) {
        if(peoples != null && position < peoples.size()){
            MoviePeople people = peoples.get(position);
            String url = people.getAlt();
            String name = people.getName();
            Bundle bundle = new Bundle();
            bundle.putString("url",url);
            bundle.putString("title",name);
            startActivity(WebViewActivity.class,bundle);
        }
    }
}
