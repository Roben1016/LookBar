package com.roshine.lookbar.mvp.view.music;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.roshine.lookbar.R;
import com.roshine.lookbar.imageloader.ImageLoaderManager;
import com.roshine.lookbar.mvp.base.MvpBaseFragment;
import com.roshine.lookbar.mvp.bean.book.Books;
import com.roshine.lookbar.mvp.bean.music.Author;
import com.roshine.lookbar.mvp.bean.music.MusicBean;
import com.roshine.lookbar.mvp.bean.music.Musics;
import com.roshine.lookbar.mvp.contract.ContractUtil;
import com.roshine.lookbar.mvp.presenter.MusicNormalPresenter;
import com.roshine.lookbar.mvp.view.book.BookDetailActivity;
import com.roshine.lookbar.utils.DisplayUtil;
import com.roshine.lookbar.utils.LogUtil;
import com.roshine.lookbar.utils.ThemeColorUtil;
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
 * @date 2017/8/28 14:34
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 音乐列表界面
 */
public class MusicNormalFragment extends MvpBaseFragment<ContractUtil.IMusicNormalView,MusicNormalPresenter> implements OnRefreshListener, OnLoadMoreListener, OnItemClickListener,ContractUtil.IMusicNormalView{
    @BindView(R.id.swip_recycler_view)
    SwipeRecyclertView swipRecyclerView;

    private LSAutoRecyclertView recyclertView;

    private List<Musics> listData = new ArrayList<>();
    private SimpleRecyclertViewAdater<Musics> mAdapter;
    private MusicNormalPresenter presenter;
    private String tag = "";
    private int start = 0;
    private int count = 18;
    private int currentLoadIndex;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            tag = arguments.getString("tag");
        }
    }

    @Override
    public MusicNormalPresenter getPresenter() {
        presenter = new MusicNormalPresenter();
        return presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_normal;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        swipRecyclerView.setColorSchemeColors(getActivity().getResources().getColor(ThemeColorUtil.getThemeColor()));
        swipRecyclerView.setLoadMoreProgressBarDrawbale(getActivity().getResources().getDrawable(ThemeColorUtil.getThemeDrawable()));
        swipRecyclerView.setOnRefreshListener(this);
        swipRecyclerView.setOnloadMoreListener(this);
        recyclertView = swipRecyclerView.getRecyclertView();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2, GridLayoutManager.VERTICAL,false);
        recyclertView.setLayoutManager(gridLayoutManager);
        mAdapter = new SimpleRecyclertViewAdater<Musics>(getActivity(),listData,R.layout.item_music_normal_layout) {
            @Override
            protected void onBindViewHolder(ViewHolder holder, int itemType, Musics itemBean, int position) {
                holder.setText(R.id.tv_item_music_score,String.format(mContext.getResources().getString(R.string.score_text_v2),itemBean.getRating().getAverage()));
                holder.setText(R.id.tv_item_music_name,itemBean.getTitle());

                List<Author> author = itemBean.getAuthor();
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < author.size(); i++) {
                    sb.append(author.get(i).getName()).append("、");
                }
                holder.setText(R.id.tv_item_music_author,sb.length()>1?sb.substring(0,sb.length()-1):sb.toString());
                ImageView imageView = holder.getView(R.id.iv_item_music_pic);
                ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions(imageView,itemBean.getImage()));
            }
        };
        recyclertView.setAdapter(mAdapter);
        recyclertView.setOnItemClick(this);
    }

    @Override
    public void loadNetData() {
        swipRecyclerView.setRefreshing(true);
        getMusicList(start,count);
    }

    private void getMusicList(int start, int count) {
        presenter.getMusics(tag,start*count,count);
    }

    public static Fragment newInstanse(String tag) {
        MusicNormalFragment fragment = new MusicNormalFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tag",tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onRefresh() {
        start = 0;
        getMusicList(start,count);
    }

    @Override
    public void onLoadMore() {
        ++start;
        currentLoadIndex = start;
        getMusicList(start,count);
    }

    @Override
    public void onReLoadMore() {
        getMusicList(currentLoadIndex,count);
    }

    @Override
    public void OnItemClick(int position, ViewHolder holder) {
        if (listData != null && position < listData.size()) {
            Pair<View, String> imagePair = Pair.create(holder.getView(R.id.iv_item_music_pic), getString(R.string.transition_name_image));
            Pair<View, String> textPair = Pair.create(holder.getView(R.id.tv_item_music_name), getString(R.string.transition_name_text));
            Pair<View, String> authorPair = Pair.create(holder.getView(R.id.tv_item_music_author), getString(R.string.transition_name_author));
            Pair<View, String> scorePair = Pair.create(holder.getView(R.id.tv_item_music_score), getString(R.string.transition_name_score));
            Pair<View, String> cardPair = Pair.create(holder.getView(R.id.card_view), getString(R.string.transition_name_card));
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imagePair, textPair,authorPair,scorePair,cardPair);
            Bundle bundle = new Bundle();
            bundle.putString("id",listData.get(position).getId());
            startActivityWithTransname(MusicDetailActivity.class,bundle,compat);
        }
    }

    @Nullable
    @Override
    public void loadSuccess(MusicBean datas) {
        swipRecyclerView.setRefreshing(false);
        if(datas != null && datas.getMusics()!= null ){
            if(datas.getMusics().size() < count){
                swipRecyclerView.setLoadMoreFinish(SwipeRecyclertView.LOAD_NO_MORE);
            }else{
                swipRecyclerView.setLoadMoreFinish(SwipeRecyclertView.LOAD_MORE_SUC);
            }
            if(start == 0){
                listData.clear();
            }
            listData.addAll(datas.getMusics());
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
