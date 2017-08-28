package com.roshine.lookbar.mvp.contract;

import com.roshine.lookbar.mvp.bean.book.BookBean;
import com.roshine.lookbar.mvp.bean.book.Books;
import com.roshine.lookbar.mvp.bean.movie.MovieBean;
import com.roshine.lookbar.mvp.base.IBaseView;
import com.roshine.lookbar.mvp.bean.movie.MovieDetailBean;
import com.roshine.lookbar.mvp.bean.music.MusicBean;
import com.roshine.lookbar.mvp.bean.music.Musics;

/**
 * @author Roshine
 * @date 2017/8/24 14:24
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public interface ContractUtil {

    //获取Top250 电影 契约
    interface ITop250Presenter extends IBaseView<MovieBean>{
        void getTop250Movie(int start, int count);
    }
    interface ITop250View extends IBaseView<MovieBean>{
    }

    //正在热映电影 契约

    interface ILivingMoviePresenter extends IBaseView<MovieBean>{
        void getLivingMovie();
    }
    interface  ILivingMovieView extends IBaseView<MovieBean>{
    }

    //即将上映电影 契约
    interface IComingSoonPresenter extends IBaseView<MovieBean>{
        void getComingSoonMovie(int start,int count);
    }
    interface IComingSoonView extends IBaseView<MovieBean>{

    }

    //电影详情 契约
    interface IMovieDetailPresenter extends IBaseView<MovieDetailBean>{
        void getMovieDetail(String id);
    }
    interface IMovieDetailView extends IBaseView<MovieDetailBean>{

    }

    //图书广场 契约
    interface IBookNormalPresenter extends IBaseView<BookBean>{
        void getBooks(String tag,int start,int count);
    }
    interface IBookNormalView extends IBaseView<BookBean>{
    }

    //书籍详情 契约
    interface IBookDetailPresenter extends IBaseView<Books>{
        void getBookDetail(String id);
    }
    interface IBookDetailView extends IBaseView<Books>{}

    //音乐列表 契约
    interface IMusicNormalPresenter extends IBaseView<MusicBean>{
        void getMusics(String tag,int start,int count);
    }
    interface IMusicNormalView extends IBaseView<MusicBean>{}

    //音乐详情 契约
    interface IMusicDetailPresenter extends IBaseView<Musics>{
        void getMusicDetailById(String id);
    }
    interface IMusicDetailView extends IBaseView<Musics>{}
}
