package com.xpw.drawbroad.controller;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.xpw.drawbroad.pojo.BGBitmap;
import com.xpw.drawbroad.pojo.DoublePath;
import com.xpw.drawbroad.pojo.DoublePathList;
import com.xpw.drawbroad.pojo.DrawBitmap;
import com.xpw.drawbroad.pojo.MyPaint;
import com.xpw.drawbroad.pojo.MyPaintList;

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

    public void onDraw(MyPaint myPaint, DoublePath doublePath, Canvas canvas, DrawBitmap drawBitmap);

    public void drawAll(MyPaintList myPaintList,DoublePathList doublePathList, Canvas canvas, DrawBitmap drawBitmap);

    public void followPoint();

    public void clean();

    public void clip();


}
