package com.xpw.drawbroad.controller.impl;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import com.xpw.drawbroad.controller.Draw;
import com.xpw.drawbroad.pojo.BGBitmap;
import com.xpw.drawbroad.pojo.DoublePath;
import com.xpw.drawbroad.pojo.DoublePathList;
import com.xpw.drawbroad.pojo.DrawBitmap;
import com.xpw.drawbroad.pojo.MyPaint;
import com.xpw.drawbroad.pojo.MyPaintList;
import com.xpw.drawbroad.tool.HalfBessel;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sunkai
 * @date 2022/9/9 11:26
 * @read happy
 */
public class DrawImpl  implements Draw {

    private static final String TAG = "DrawImpl";
    //多指操作屏幕时，记录各个手指编号
    private Map pathMap;
    private HalfBessel halfBessel;
    private DoublePath doublePath;





    @Override
    public void setBG(Resources resources, int resId, int w, int h, BGBitmap bgBitmap) {
//        bgBitmap.setBgBitmap(bgBitmap.convertToBitmap(path, w, h));
        this.decodeBitmapFromRes(resources, resId, w, h, bgBitmap);
    }

    @Override
    public void setPaint(MyPaint myPaint) {

        myPaint = new MyPaint(Color.BLUE, 20, Paint.Style.FILL, "paint");
    }

    /**
     * 多指绘图
     * <p>
     * 1、初始化画笔、pathMap
     * 2、down 创建临时doublePath,并存入pathMap
     * 3.1、move 记录double轨迹
     * 3.2、move 绘制轨迹
     * 3.3、up 将临时doublePath与对应的myPaint存入对应的List中，去除pathMap对应的临时doublePath
     *
     * @param myPaint
     * @param canvas
     * @param v
     * @param event
     */
    @Override
    public void onDraw(MyPaint myPaint, Canvas canvas, View v, MotionEvent event) {

        setPaint(myPaint);

        switch (event.getAction() & event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (!isEraser(myPaint) && !isClip(myPaint)) {

                    doublePath = new DoublePath();
                    pathMap = new HashMap();

                    doublePath.initPath(0, event);
                    pathMap.put(doublePath.getId(), null);

                }

                break;

            case MotionEvent.ACTION_MOVE:

                halfBessel = new HalfBessel(doublePath.getId(),doublePath,canvas,event,myPaint);
                halfBessel.draw();
                canvas.drawPath(doublePath.getActualPath(), myPaint);

                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                if (!isEraser(myPaint) && !isClip(myPaint)) {
                    int pointerCount = event.getPointerCount();
                    for (int i = 0; i < pointerCount; i++) {
                        if (!pathMap.containsKey(event.getPointerId(i))) {
                            DoublePath path = new DoublePath();
                            path.initPath(i, event);
                            pathMap.put(path.getId(), null);
                        }
                    }
                }
                break;


            case MotionEvent.ACTION_POINTER_UP:
                int removePathId = event.getPointerId(event.getActionIndex());
                Log.d(TAG, "移除的手指编号是: " + removePathId);
                pathMap.remove(removePathId);

                break;

            case MotionEvent.ACTION_UP:
                pathMap.clear();
                break;

        }

    }

    @Override
    public void saveDoublePath() {

    }

    @Override
    public void drawDoublePath() {

    }

    @Override
    public void removeDoublePath() {

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

    public Boolean isEraser(MyPaint myPaint) {
        return false;
    }

    public Boolean isClip(MyPaint myPaint) {
        return false;
    }

}
