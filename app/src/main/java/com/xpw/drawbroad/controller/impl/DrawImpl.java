package com.xpw.drawbroad.controller.impl;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.xpw.drawbroad.controller.Draw;
import com.xpw.drawbroad.pojo.BGBitmap;
import com.xpw.drawbroad.pojo.DoublePath;
import com.xpw.drawbroad.pojo.DoublePathList;
import com.xpw.drawbroad.pojo.DrawBitmap;
import com.xpw.drawbroad.pojo.MyPaint;
import com.xpw.drawbroad.pojo.MyPaintList;
import com.xpw.drawbroad.tool.HalfBessel;

import java.util.List;
import java.util.Map;

/**
 * @author sunkai
 * @date 2022/9/9 11:26
 * @read happy
 */
public class DrawImpl implements Draw {

    private Canvas canvasTemp;

    private static final String TAG = "DrawImpl";


    @Override
    public void setBG(Resources resources, int resId, int w, int h, BGBitmap bgBitmap) {
//        bgBitmap.setBgBitmap(bgBitmap.convertToBitmap(path, w, h));
        this.decodeBitmapFromRes(resources, resId, w, h, bgBitmap);
    }

    @Override
    public void setPaint(MyPaint myPaint) {
        myPaint.setPaint(new Paint());
        myPaint.getPaint().setColor(Color.WHITE);
        myPaint.setPaintType("paint");
        myPaint.getPaint().setAntiAlias(true);
        myPaint.getPaint().setStrokeWidth(5);
        myPaint.getPaint().setStyle(Paint.Style.STROKE);
        myPaint.getPaint().setStrokeCap(Paint.Cap.ROUND);
        myPaint.getPaint().setStrokeJoin(Paint.Join.ROUND);
        myPaint.getPaint().setAlpha(100);
        myPaint.getPaint().setFilterBitmap(true);
        Log.d(TAG, "setPaint: 001");

    }


    /**
     * @param event
     * @param doublePath
     * @param doublePathList
     * @param drawBitmap
     * @param pathMap
     */
    @Override
    public void setDoublePath(MotionEvent event, DoublePath doublePath, DoublePathList doublePathList, DrawBitmap drawBitmap, Map<Integer, DoublePath> pathMap) {
        int pointerCount = event.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            Log.d(TAG, "setDoublePath: event.getPointerId(i)= " + event.getPointerId(i));
            if (!pathMap.containsKey(event.getPointerId(i))) {
                doublePath = new DoublePath();
                doublePath.initPath(i, event);
                pathMap.put(i, doublePath);
                //todo drawBitmap 是否有存在在改方法中的必要？
            }
        }
    }

    /**
     * 识别当前的手指id，并修改对应的path
     *
     * @param event
     * @param doublePath
     * @param doublePathList
     * @param drawBitmap
     * @param pathMap
     */
    @Override
    public void changeDoublePath(MotionEvent event, DoublePath doublePath, DoublePathList doublePathList, DrawBitmap drawBitmap,
                                 Map<Integer, DoublePath> pathMap, HalfBessel halfBessel, MyPaint myPaint) {
        int i = 0;
        if (!pathMap.isEmpty()) {
            canvasTemp = new Canvas(drawBitmap.getDrawBitmap());
            for (Map.Entry<Integer, DoublePath> m : pathMap.entrySet()) {
                halfBessel.fixPath(event, m.getValue(), i);
                canvasTemp.drawPath(m.getValue().getSlowPath(), myPaint.getPaint());
                i++;
            }
        }
    }

    @Override
    public void saveDoublePath(MotionEvent event, DoublePath doublePath,MyPaint myPaint, DoublePathList doublePathList,MyPaintList myPaintList, Map<Integer, DoublePath> pathMap) {
        int id = event.getPointerId(event.getActionIndex());
        doublePathList.getDoublePathList().add(pathMap.get(id));
        myPaintList.getMyPaintList().add(myPaint);

    }


    @Override
    public void removeDoublePath(MotionEvent event, Map<Integer, DoublePath> pathMap) {
        int id = event.getPointerId(event.getActionIndex());
        pathMap.remove(id);

    }

    @Override
    public List<Path> drawDoublePath(Map<Integer, DoublePath> pathMap, Canvas canvas, DrawBitmap drawBitmap, BGBitmap bgBitmap, MyPaint myPaint) {
        myPaint.getPaint().setAntiAlias(true);
        myPaint.getPaint().setColor(Color.YELLOW);
        if (!pathMap.isEmpty()) {
            for (Map.Entry<Integer, DoublePath> map : pathMap.entrySet()) {
                canvas.drawPath(map.getValue().getSlowPath(), myPaint.getPaint());
//                canvas.drawBitmap(drawBitmap.getDrawBitmap(), 0, 0, myPaint.getPaint());
            }
        }

        myPaint.getPaint().setColor(Color.WHITE);
        return null;

    }


    @Override
    public void clearPathMap(Map<Integer, DoublePath> pathMap) {
        pathMap.clear();
    }

    /**
     * 1、清空drawBitMap
     * 2、新建canvas1，绑定drawBitmap
     * 3、遍历myPaintList与doublePathList重绘
     *
     * @param myPaintList
     * @param doublePathList
     * @param canvas
     * @param drawBitmap
     */
    @Override
    public void drawAll(MyPaintList myPaintList, DoublePathList doublePathList, Canvas canvas, DrawBitmap drawBitmap) {
        if (drawBitmap.getDrawBitmap() != null) {
//            drawBitmap.getDrawBitmap().recycle();
            drawBitmap.setDrawBitmap( Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888));
        }
        Canvas canvas1 = new Canvas(drawBitmap.getDrawBitmap());
        int i = 0;
        for (DoublePath d : doublePathList.getDoublePathList()) {
            canvas1.drawPath(d.getSlowPath(), myPaintList.getMyPaintList().get(i).getPaint());
            i++;
        }
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
                                    int requestHeight,
                                    BGBitmap bgBitmap) {
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
