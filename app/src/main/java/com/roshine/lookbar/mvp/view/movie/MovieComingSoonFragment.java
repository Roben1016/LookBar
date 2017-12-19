package com.roshine.lookbar.mvp.view.movie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.roshine.lookbar.R;
import com.roshine.lookbar.imageloader.ImageLoaderManager;
import com.roshine.lookbar.mvp.base.MvpBaseFragment;
import com.roshine.lookbar.mvp.bean.movie.Casts;
import com.roshine.lookbar.mvp.bean.movie.Directors;
import com.roshine.lookbar.mvp.bean.movie.MovieBean;
import com.roshine.lookbar.mvp.bean.movie.Subjects;
import com.roshine.lookbar.mvp.contract.ContractUtil;
import com.roshine.lookbar.mvp.presenter.MovieComingSoonPresenter;
import com.roshine.lookbar.utils.DisplayUtil;
import com.roshine.lookbar.utils.ThemeColorUtil;
import com.roshine.lookbar.wight.recyclerview.decoration.SpacesItemDecoration;
import com.roshine.lookbar.wight.recyclerview.base.SimpleRecyclertViewAdater;
import com.roshine.lookbar.wight.recyclerview.base.ViewHolder;
import com.roshine.lookbar.wight.recyclerview.interfaces.OnItemClickListener;
import com.roshine.lookbar.wight.recyclerview.interfaces.OnLoadMoreListener;
import com.roshine.lookbar.wight.recyclerview.interfaces.OnRefreshListener;
import com.roshine.lookbar.wight.recyclerview.refreshandload.LSAutoRecyclertView;
import com.roshine.lookbar.wight.recyclerview.refreshandload.SwipeRecyclertView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Roshine
 * @date 2017/8/25 11:44
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 即将上映电影
 */
public class MovieComingSoonFragment extends MvpBaseFragment<ContractUtil.IComingSoonView, MovieComingSoonPresenter> implements OnRefreshListener, OnLoadMoreListener, OnItemClickListener,ContractUtil.IComingSoonView {
    @BindView(R.id.swip_recycler_view)
    SwipeRecyclertView swipRecyclerView;
    private MovieComingSoonPresenter presenter;
    private int start = 0;
    private int count = 15;
    private LSAutoRecyclertView recyclertView;
    private SimpleRecyclertViewAdater<Subjects> mAdapter;
    private List<Subjects> listData = new ArrayList<>();
    private int currentStart;

    @Override
    public MovieComingSoonPresenter getPresenter() {
        presenter = new MovieComingSoonPresenter();
        return presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_living_movie;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        swipRecyclerView.setColorSchemeColors(getActivity().getResources().getColor(ThemeColorUtil.getThemeColor()));
        swipRecyclerView.setLoadMoreProgressBarDrawbale(getActivity().getResources().getDrawable(ThemeColorUtil.getThemeDrawable()));
        swipRecyclerView.setOnRefreshListener(this);
        swipRecyclerView.setOnloadMoreListener(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclertView = swipRecyclerView.getRecyclertView();
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        recyclertView.setLayoutManager(gridLayoutManager);
        mAdapter = new SimpleRecyclertViewAdater<Subjects>(getActivity(),listData,R.layout.item_coming_soon_movie_layout) {
            @Override
            protected void onBindViewHolder(ViewHolder holder, int itemType, Subjects itemBean, int position) {
                List<String> genres = itemBean.getGenres();
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < genres.size(); i++) {
                    sb.append(genres.get(i)).append("、");
                }
                holder.setText(R.id.tv_movie_chinese_name,itemBean.getTitle());
                holder.setText(R.id.tv_movie_genres,sb.length() >= 1 ?sb.substring(0,sb.length()-1):sb.toString());
                List<Directors> directors = itemBean.getDirectors();
                StringBuffer sb2 = new StringBuffer();
                for (int i = 0; i < directors.size(); i++) {
                    sb2.append(directors.get(i).getName()).append("　");
                }
                holder.setText(R.id.tv_director,sb2.length() >= 1 ?sb2.substring(0,sb2.length()-1):sb2.toString());

                List<Casts> casts = itemBean.getCasts();
                StringBuffer sb3 = new StringBuffer();
                for (int i = 0; i < casts.size(); i++) {
                    sb3.append(casts.get(i).getName()).append("　");
                }
                holder.setText(R.id.tv_cast,sb3.length() >= 1 ?sb3.substring(0,sb3.length()-1):sb3.toString());

                ImageView imageView = holder.getView(R.id.iv_movie_pic);
                ViewGroup.LayoutParams params=imageView.getLayoutParams();
                int ivHeight=(DisplayUtil.dp2px(getActivity(),150)- DisplayUtil.dp2px(getActivity(),10));
                params.height= ivHeight;
                double ivWidth =ivHeight * 300.0 / 444.0;
                params.width=(int)ivWidth;
                imageView.setLayoutParams(params);
                ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions(imageView,itemBean.getImages().getLarge()));
            }
        };
        SpacesItemDecoration spacesItemDecoration=new SpacesItemDecoration(DisplayUtil.dp2px(getActivity(),10),DisplayUtil.dp2px(getActivity(),15),DisplayUtil.dp2px(getActivity(),10),0);
        recyclertView.addItemDecoration(spacesItemDecoration);
        recyclertView.setAdapter(mAdapter);
        recyclertView.setOnItemClick(this);
    }

    @Override
    public void loadNetData() {
        swipRecyclerView.setRefreshing(true);
        getListData(start, count);
    }

    private void getListData(int start, int count) {
        presenter.getComingSoonMovie(start, count);
    }

    @Override
    public void onRefresh() {
        start = 0 ;
        getListData(start,count);
    }

    @Override
    public void onLoadMore() {
        ++start;
        currentStart = start;
        getListData(start * count,count);
    }

    @Override
    public void onReLoadMore() {
        getListData(currentStart * count,count);
    }

    @Override
    public void OnItemClick(int position, ViewHolder holder) {
        if(listData != null && position < listData.size()){
            String id = listData.get(position).getId();
            Bundle bundle = new Bundle();
            bundle.putString("id",id);
            Pair<View, String> imagePair = Pair.create(holder.getView(R.id.iv_movie_pic), getString(R.string.transition_name_image));
            Pair<View, String> textPair = Pair.create(holder.getView(R.id.tv_movie_chinese_name), getString(R.string.transition_name_text));
            Pair<View, String> genresPair = Pair.create(holder.getView(R.id.tv_movie_genres), getString(R.string.transition_name_genres));
            Pair<View, String> cardPair = Pair.create(holder.getView(R.id.card_view), getString(R.string.transition_name_card));
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imagePair, textPair,genresPair,cardPair);
            startActivityWithTransname(MovieDetailActivity.class,bundle,compat);
        }
    }

    public static MovieComingSoonFragment newInstanse() {
        return new MovieComingSoonFragment();
    }

    @Nullable
    @Override
    public void loadSuccess(MovieBean datas) {
        swipRecyclerView.setRefreshing(false);
        if(datas != null && datas.getSubjects()!= null ){
            if(datas.getSubjects().size() < count){
                swipRecyclerView.setLoadMoreFinish(SwipeRecyclertView.LOAD_NO_MORE);
            }else{
                swipRecyclerView.setLoadMoreFinish(SwipeRecyclertView.LOAD_MORE_SUC);
            }
            if(start == 0){
                listData.clear();
            }
            listData.addAll(datas.getSubjects());
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }else{
            swipRecyclerView.setLoadMoreFinish(SwipeRecyclertView.LOAD_NO_MORE);
        }
    }

    @Override
    public void loadFail(String message) {
        swipRecyclerView.setRefreshing(false);
        swipRecyclerView.setLoadMoreFinish(SwipeRecyclertView.LOAD_MORE_FAIL);
        toast(message);
    }
}
