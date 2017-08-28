package com.roshine.lookbar.mvp.view.movie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.roshine.lookbar.R;
import com.roshine.lookbar.imageloader.ImageLoaderManager;
import com.roshine.lookbar.mvp.base.MvpBaseFragment;
import com.roshine.lookbar.mvp.bean.movie.MovieBean;
import com.roshine.lookbar.mvp.bean.movie.Subjects;
import com.roshine.lookbar.mvp.contract.ContractUtil;
import com.roshine.lookbar.mvp.presenter.MovieLivingPresenter;
import com.roshine.lookbar.utils.DisplayUtil;
import com.roshine.lookbar.utils.ThemeColorUtil;
import com.roshine.lookbar.wight.recyclerview.decoration.SpacesItemDecoration;
import com.roshine.lookbar.wight.recyclerview.base.SimpleRecyclertViewAdater;
import com.roshine.lookbar.wight.recyclerview.base.ViewHolder;
import com.roshine.lookbar.wight.recyclerview.interfaces.OnItemClickListener;
import com.roshine.lookbar.wight.recyclerview.interfaces.OnRefreshListener;
import com.roshine.lookbar.wight.recyclerview.refreshandload.LSAutoRecyclertView;
import com.roshine.lookbar.wight.recyclerview.refreshandload.SwipeRecyclertView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Roshine
 * @date 2017/8/24 14:42
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class MovieLivingFragment extends MvpBaseFragment<ContractUtil.ILivingMovieView, MovieLivingPresenter> implements ContractUtil.ILivingMovieView, OnRefreshListener, OnItemClickListener {
    @BindView(R.id.swip_recycler_view)
    SwipeRecyclertView swipRecyclerView;
    private MovieLivingPresenter presenter;

    private LSAutoRecyclertView recyclertView;
    private SimpleRecyclertViewAdater<Subjects> mAdapter;
    private List<Subjects> listData = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_living_movie;
    }

    @Override
    public MovieLivingPresenter getPresenter() {
        presenter = new MovieLivingPresenter();
        return presenter;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        swipRecyclerView.setColorSchemeColors(getActivity().getResources().getColor(ThemeColorUtil.getThemeColor()));
        swipRecyclerView.setLoadMoreProgressBarDrawbale(getActivity().getResources().getDrawable(ThemeColorUtil.getThemeDrawable()));
        swipRecyclerView.setLoadMoreEnable(false);
        swipRecyclerView.setOnRefreshListener(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclertView = swipRecyclerView.getRecyclertView();
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        recyclertView.setLayoutManager(gridLayoutManager);
        mAdapter = new SimpleRecyclertViewAdater<Subjects>(getActivity(),listData,R.layout.item_living_movie_layout) {
            @Override
            protected void onBindViewHolder(ViewHolder holder, int itemType, Subjects itemBean, int position) {
                List<String> genres = itemBean.getGenres();
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < genres.size(); i++) {
                    sb.append(genres.get(i)).append("、");
                }
                holder.setText(R.id.tv_movie_chinese_name,itemBean.getTitle());
                holder.setText(R.id.tv_movie_genres,sb.substring(0,sb.length()-1));
                holder.setText(R.id.tv_score,String.valueOf(itemBean.getRating().getAverage()));
                AppCompatRatingBar ratingBar = holder.getView(R.id.rb_score);
                double average = itemBean.getRating().getAverage();
                double rating = (average / 10)*5;
                ratingBar.setRating((float) rating);

                ImageView imageView = holder.getView(R.id.iv_movie_pic);
                ViewGroup.LayoutParams params=imageView.getLayoutParams();
                int ivHeight=DisplayUtil.dp2px(getActivity(),150);
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
        getListData();
    }

    private void getListData() {
        presenter.getLivingMovie();
    }

    public static MovieLivingFragment newInstanse() {
        return new MovieLivingFragment();
    }

    @Nullable
    @Override
    public void loadSuccess(MovieBean datas) {
        swipRecyclerView.setRefreshing(false);
        if(datas != null && datas.getSubjects()!= null ){
            if(listData != null){
                listData.clear();
            }
            listData.addAll(datas.getSubjects());
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void loadFail(String message) {
        swipRecyclerView.setRefreshing(false);
        toast(message);
    }

    @Override
    public void onRefresh() {
        getListData();
    }
    @Override
    public void OnItemClick(int position, ViewHolder holder) {
        if(listData != null && position < listData.size()){
            String id = listData.get(position).getId();
            Bundle bundle = new Bundle();
            bundle.putString("id",id);
            startActivity(MovieDetailActivity.class,bundle);
        }
    }
}
