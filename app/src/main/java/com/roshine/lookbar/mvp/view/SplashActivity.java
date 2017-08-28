package com.roshine.lookbar.mvp.view;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;


import com.roshine.lookbar.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class SplashActivity extends AppCompatActivity {
    private ImageView ivBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ivBg = (ImageView) findViewById(R.id.iv_splesh_bg);
        initAnim();
    }

    private void initAnim() {
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        startAnim();
                    }
                });
    }

    private void startAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(ivBg, "scaleX", 1.0f, 1.5f);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(ivBg, "scaleY", 1.0f, 1.5f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(2000);
        set.playTogether(animator, animator1);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        });
    }
}
