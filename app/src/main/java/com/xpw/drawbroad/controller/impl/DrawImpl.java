package com.xpw.drawbroad.controller.impl;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import com.xpw.drawbroad.controller.Draw;
import com.xpw.drawbroad.pojo.BGBitmap;
import com.xpw.drawbroad.pojo.DoublePath;
import com.xpw.drawbroad.pojo.DoublePathList;
import com.xpw.drawbroad.pojo.DrawBitmap;
import com.xpw.drawbroad.pojo.MyPaint;
import com.xpw.drawbroad.pojo.MyPaintList;

/**
 * @author sunkai
 * @date 2022/9/9 11:26
 * @read happy
 */
public class DrawImpl implements Draw {

    private static final String TAG = "DrawImpl";


    @Override
    public void setBG(Resources resources, int resId, int w, int h, BGBitmap bgBitmap) {
//        bgBitmap.setBgBitmap(bgBitmap.convertToBitmap(path, w, h));
        this.decodeBitmapFromRes(resources, resId, w, h, bgBitmap);
    }

    /**
     * 基础绘图
     * 支持多笔绘图
     * 非全局绘图
     *
     * @param myPaint
     */
    @Override
    public void onDraw(MyPaint myPaint, DoublePath doublePath, Canvas canvas, DrawBitmap drawBitmap) {

    }

    @Override
    public void drawAll(MyPaintList myPaintList, DoublePathList doublePathList, Canvas canvas, DrawBitmap drawBitmap) {

        canvas = new Canvas(drawBitmap.getDrawBitmap());

        //清空画布
        //绘制背景
        //绘制绘画内容


    }

    @Override
    public void followPoint() {

    }

    @Override
    public void clean() {

    }

    @Override
    public void clip() {

    }


    // 从res中加载bitmap
    public void decodeBitmapFromRes(Resources res, int resId,
                                    int requestWidth,
                                    int requestHeight, BGBitmap bgBitmap) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        //设置采样率
        options.inSampleSize = calculateInSampleSize(options, requestWidth, requestHeight);

        options.inJustDecodeBounds = false;

        bgBitmap.setBgBitmap(BitmapFactory.decodeResource(res, resId, options));
    }

    //计算采样率
    private int calculateInSampleSize(BitmapFactory.Options options,
                                      int requestWidth,
                                      int requestHeight) {
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        int inSampleSize = 1;

        if (outHeight > requestHeight || outWidth > requestWidth) {
            int halfHeight = outHeight / 2;
            int halfWidth = outWidth / 2;

            while ((halfHeight / inSampleSize) >= requestHeight
                    && (halfWidth / inSampleSize) >= requestWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
