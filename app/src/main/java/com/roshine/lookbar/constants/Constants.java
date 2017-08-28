package com.roshine.lookbar.constants;

import android.Manifest;

import com.roshine.lookbar.R;
import com.roshine.lookbar.mvp.bean.ThemeColorBean;

/**
 * @author Roshine
 * @date 2017/7/18 16:18
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 常量类
 */
public class Constants {

    public static class NormalConstants{
        public static final int TIME_OUT = 30;//请求超时时间
        public static final String CONTENT_TYPE = "text/xml";
    }

    public static class SharedPreferancesKeys{
        public static final String APP_THEME_COLOR = "appThemeColor";
        public static final String APP_THEME_COLOR_POSITION = "appThemeColorPosition";
        public static final String CONTAINER_TAGS = "containerTags";
        public static final String CONTAINER_TAGS_COUNT = "containerTagsCount";
        public static final String UNCONTAINER_TAGS = "uncontainerTags";
        public static final String UNCONTAINER_TAGS_COUNT = "uncontainerTagsCount";
    }

    public static class ThemeColors{
        public static final ThemeColorBean[] THEME_COLOR_LIST = {
                new ThemeColorBean(R.string.theme_blue_text, R.color.colorPrimary,R.drawable.pb_theme_colorprimary,R.color.nv_theme_tink_colorprimary),
                new ThemeColorBean(R.string.theme_green_text, R.color.theme_green,R.drawable.pb_theme_green,R.color.nv_theme_tink_green),
                new ThemeColorBean(R.string.theme_purple_text, R.color.theme_purple,R.drawable.pb_theme_purple,R.color.nv_theme_tink_purple),
                new ThemeColorBean(R.string.theme_yellow_text, R.color.theme_yellow,R.drawable.pb_theme_yellow,R.color.nv_theme_tink_yellow),
                new ThemeColorBean(R.string.theme_red_text, R.color.red,R.drawable.pb_theme_red,R.color.nv_theme_tink_red),
                new ThemeColorBean(R.string.theme_pink_text, R.color.theme_pink,R.drawable.pb_theme_pink,R.color.nv_theme_tink_pink),
                new ThemeColorBean(R.string.theme_brown_text, R.color.theme_brown,R.drawable.pb_theme_brown,R.color.nv_theme_tink_brown),
                new ThemeColorBean(R.string.theme_black_text, R.color.black,R.drawable.pb_theme_black,R.color.nv_theme_tink_black),
                new ThemeColorBean(R.string.theme_orange_text, R.color.theme_orange,R.drawable.pb_theme_orange,R.color.nv_theme_tink_orange),
                new ThemeColorBean(R.string.theme_blue_light_text, R.color.theme_blue_light,R.drawable.pb_theme_blue_light,R.color.nv_theme_tink_blue_light),
                new ThemeColorBean(R.string.theme_gray_text, R.color.theme_gray,R.drawable.pb_theme_gray,R.color.nv_theme_tink_gray),
                new ThemeColorBean(R.string.theme_green_light_text, R.color.theme_green_light,R.drawable.pb_theme_green_light,R.color.nv_theme_tink_green_light)
        };
    }

    public static class Urls{
        public static final String BASE_URL = "https://api.douban.com/";
    }

    public static class PermissionNames{
        public static final String PERMISSION_READ_SD = Manifest.permission.READ_EXTERNAL_STORAGE;
        public static final String PERMISSION_WRITE_SD = Manifest.permission.WRITE_APN_SETTINGS;
    }

    public static class Tag{
        public static final String[] DEFAULT_CONTAINER_TAGS = {"互联网","科技","散文","哲学","旅行","爱情","奇幻", "漫画","悬疑","艺术"};
        public static final String[] DEFAULT_UNCONTAINER_TAGS = { "科学", "科普", "通信", "交互", "王小波",  "生活", "励志", "成长",   "武侠", "韩寒",  "青春文学",
                "海明威","小说","中国文学", "村上春树", "余华", "鲁迅", "米兰·昆德拉", "外国文学", "经典", "童话", "儿童文学", "名著", "外国名著", "杜拉斯", "文学",  "诗歌", "张爱玲", "钱钟书", "诗词", "港台", "随笔", "日本文学", "杂文", "古典文学", "当代文学", "茨威格",
                "编程",  "算法", "web", "UE",   "用户体验",   "UCD",
                "绘本", "推理", "青春", "言情","科幻", "东野圭吾",  "日本漫画", "耽美", "亦舒", " 三毛", "安妮宝贝", "网络小说", "郭敬明", "推理小说", "穿越", "金庸", "轻小说", "几米", "阿加莎·克里斯蒂","张小娴", "幾米", "魔幻", "J.K.罗琳", "科幻小说", "高木直子", "古龙", "沧月", "蔡康永", "落落", "张悦然",
                "历史","心理学","传记","文化","社会学","设计","政治","社会","建筑","宗教","电影","数学","政治学","回忆录","思想","国学","中国历史","音乐","人文","戏剧","人物传记","绘画","艺术史","佛教","军事","西方哲学","近代史","二战","自由主义","考古","美术",
                "心理", "摄影", "女性", "职场", "美食", "教育", "游记", "灵修", "情感", "健康", "手工", "养生", "两性", "人际关系", "家居", "自助游",
                "经济学", "管理", "经济", "金融", "商业", "投资", "营销", "创业", "理财", "广告", "股票", "企业史", "策划" };
    }
}
