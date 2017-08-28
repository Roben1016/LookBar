package com.roshine.lookbar.http;

import com.roshine.lookbar.mvp.bean.book.BookBean;
import com.roshine.lookbar.mvp.bean.book.Books;
import com.roshine.lookbar.mvp.bean.movie.MovieBean;
import com.roshine.lookbar.mvp.bean.movie.MovieDetailBean;
import com.roshine.lookbar.mvp.bean.music.MusicBean;
import com.roshine.lookbar.mvp.bean.music.Musics;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Roshine
 * @date 2017/8/23 23:58
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public interface RetrofitService {

    /**
     * 热映中
     * @return
     */
    @GET("v2/movie/in_theaters")
    Flowable<MovieBean> getLiveMovie();


    /**
     * 即将上映
     * @return
     */
    @GET("/v2/movie/coming_soon")
    Flowable<MovieBean> getComingSoonMovie(@Query("start")int start, @Query("count")int count);

    /**
     * 获取top250
     * @param start
     * @param count
     * @return
     */
    @GET("v2/movie/top250")
    Flowable<MovieBean> getTop250(@Query("start")int start, @Query("count")int count);

    /**
     * 获取电影详情
     * @param id
     * @return
     */
    @GET("v2/movie/subject/{id}")
    Flowable<MovieDetailBean> getMovieDetail(@Path("id") String id);

    /**
     * 根据tag获取图书
     * @param tag
     * @return
     */

    @GET("v2/book/search")
    Flowable<BookBean> getBookByTag(@Query("tag")String tag,@Query("start")int start, @Query("count")int count);

    /**
     * 获取书籍详情
     * @param id
     * @return
     */
    @GET("v2/book/{id}")
    Flowable<Books> getBookDetail(@Path("id") String id);

    /**
     * 根据tag获取music
     * @param tag
     * @return
     */

    @GET("v2/music/search")
    Flowable<MusicBean> getMusicByTag(@Query("tag")String tag, @Query("start")int start, @Query("count")int count);

    @GET("v2/music/{id}")
    Flowable<Musics> getMusicDetail(@Path("id") String id);
}

