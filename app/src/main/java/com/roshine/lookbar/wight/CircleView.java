package com.roshine.lookbar.wight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Roshine
 * @date 2017/8/23 20:03
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class CircleView extends View {
    private int mColor = Color.BLACK;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public CircleView(Context context) {
        super(context);
        init();
    }
    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    /**     * 初始化画笔的颜色     */
    private void init(){
        mPaint.setColor(mColor);
    }
    @Override
    public void setBackgroundColor(@ColorInt int color) {
//        super.setBackgroundColor(color);
        mColor = color;
        mPaint.setColor(color);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width,height) / 2;
        canvas.drawCircle(width / 2,height / 2,radius,mPaint);
    }
}
