package com.xpw.drawbroad.controller;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

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
 * @date 2022/9/9 11:29
 * @read happy
 */
public interface Draw {

    /**
     * 1、设置背景
     * 2、绘图
     * 3、跟随原点
     * 4、擦除
     * 5、裁剪
     */

    public void setBG(Resources resources, int resId, int w, int h, BGBitmap bgBitmap);

    public void setPaint(MyPaint myPaint);

    public void setDoublePath(MotionEvent event, DoublePath doublePath, DoublePathList doublePathList, DrawBitmap drawBitmap, Map<Integer,DoublePath> pathMap);

    public void changeDoublePath(MotionEvent event, DoublePath doublePath, DoublePathList doublePathList, DrawBitmap drawBitmap, Map<Integer,DoublePath> pathMap,HalfBessel halfBessel,MyPaint myPaint);

    public void saveDoublePath(MotionEvent event, DoublePath doublePath, DoublePathList doublePathList,Map<Integer, DoublePath> pathMap);

    public void removeDoublePath(MotionEvent event, Map<Integer, DoublePath> pathMap);


    public void clearPathMap(Map<Integer,DoublePath> pathMap);

    public List<Path> drawDoublePath(Map<Integer,DoublePath> pathMap,Canvas canvas , DrawBitmap drawBitmap, BGBitmap bgBitmap, MyPaint myPaint);

    public void drawAll(MyPaintList myPaintList,DoublePathList doublePathList, Canvas canvas, DrawBitmap drawBitmap);

    public void followPoint();

    public void clean();

    public void clip();


}
