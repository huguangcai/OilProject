package com.ysxsoft.grainandoil.widget.banner;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * 描述： TODO
 * 日期： 2018/10/30 0030 17:12
 * 作者： 胡
 * 公司：郑州亿生信科技有限公司
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
        Glide.with(context.getApplicationContext())
                .load(path)
                .into(imageView);
    }
}
