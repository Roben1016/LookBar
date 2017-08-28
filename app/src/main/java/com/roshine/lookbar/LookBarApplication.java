package com.roshine.lookbar;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.roshine.lookbar.http.RetrofitClient;
import com.roshine.lookbar.http.RetrofitService;
import com.roshine.lookbar.imageloader.ImageLoaderManager;
import com.squareup.leakcanary.LeakCanary;


/**
 * @author Roshine
 * @date 2017/7/18 18:10
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class LookBarApplication extends MultiDexApplication {
    private static Context applicationContext;
    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        ImageLoaderManager.getInstance().init(this);
//         安装LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    public static Context getContext(){
        return applicationContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
