package com.roshine.lookbar.mvp.bean;

/**
 * @author Roshine
 * @date 2017/7/31 14:09
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 主题颜色bean
 */
public class ThemeColorBean {

    private int colorName;
    private int colorValue;
    private int colorDrawable;

    private int colorTink;

    public ThemeColorBean(int colorName, int colorValue, int drawable,int colorTink){
        this.colorName = colorName;
        this.colorValue = colorValue;
        this.colorDrawable = drawable;
        this.colorTink = colorTink;
    }

    public int getColorName() {
        return colorName;
    }

    public void setColorName(int colorName) {
        this.colorName = colorName;
    }

    public int getColorValue() {
        return colorValue;
    }

    public void setColorValue(int colorValue) {
        this.colorValue = colorValue;
    }

    public int getColorDrawable() {
        return colorDrawable;
    }
    public int getColorTink() {
        return colorTink;
    }

    public void setColorTink(int colorTink) {
        this.colorTink = colorTink;
    }

    public void setColorDrawable(int colorDrawable) {
        this.colorDrawable = colorDrawable;
    }
}
