package com.xpw.drawbroad.pojo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;

import com.xpw.drawbroad.controller.impl.DrawImpl;

import java.lang.ref.WeakReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sunkai
 * @date 2022/9/9 13:25
 * @read happy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BGBitmap  {

    private static final String TAG = "decodeBitmapFromRes";
    private Bitmap bgBitmap ;
    private DrawImpl draw = new DrawImpl();

    public Bitmap convertToBitmap(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > w || height > h) {
            // 缩放
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int) scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(
                BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }

    public void setBG(Resources resources, int resId, int w, int h) {
        Log.d(TAG, "setBG: 234423442342");
        draw.setBG(resources, resId, w, h, this);
        Log.d(TAG, "setBG: "+this.toString());
    }


}
