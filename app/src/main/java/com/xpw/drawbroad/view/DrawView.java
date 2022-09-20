package com.xpw.drawbroad.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import com.xpw.drawbroad.R;
import com.xpw.drawbroad.controller.Draw;
import com.xpw.drawbroad.controller.impl.DrawImpl;
import com.xpw.drawbroad.pojo.BGBitmap;
import com.xpw.drawbroad.pojo.DoublePath;
import com.xpw.drawbroad.pojo.DoublePathList;
import com.xpw.drawbroad.pojo.DrawBitmap;
import com.xpw.drawbroad.pojo.MyPaint;
import com.xpw.drawbroad.pojo.MyPaintList;
import com.xpw.drawbroad.tool.HalfBessel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sunkai
 * @date 2022/9/9 9:46
 * @read happy
 */
public class DrawView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener, Runnable {

    private static final String TAG = "SurfaceView";
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private BGBitmap bgBitmap;
    private DrawBitmap drawBitmap;
    private MyPaint myPaint;
    private MyPaintList myPaintList;
    private DoublePath doublePath;
    private DoublePathList doublePathList;
    private Map<Integer, DoublePath> pathMap;
    private Draw drawControl;
    private boolean mIsDrawing;
    private final HalfBessel halfBessel = new HalfBessel();
    public Boolean isSetBG;
    private Bitmap bitmapCache;


    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        setOnTouchListener(this);

    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        mIsDrawing = true;
        initView();
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        recordPath(event);
        return true;
    }


    @Override
    public void run() {
        while (mIsDrawing) {
            try {
                canvas = surfaceHolder.lockCanvas();
                //todo:绘制操作
//                canvas.drawColor(Color.GRAY);

                if (bitmapCache == null) {
                    bitmapCache = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas1 = new Canvas(bitmapCache);
                    drawControl.setBG(getResources(), R.drawable.bg, canvas.getWidth(), getHeight(), bgBitmap);
                    canvas.drawBitmap(bitmapCache, 0, 0, myPaint.getPaint());
                    drawControl.drawDoublePath(pathMap, canvas1, drawBitmap, bgBitmap, myPaint);
                    canvas.drawBitmap(bitmapCache, 0, 0, myPaint.getPaint());
                    canvas.drawCircle(1200,100,50,myPaint.getPaint());


                } else {
                    Canvas canvas1 = new Canvas(bitmapCache);
                    canvas1.drawBitmap(bgBitmap.getBgBitmap(), 0, 0, myPaint.getPaint());
                    canvas.drawBitmap(bitmapCache, 0, 0, myPaint.getPaint());
                    canvas1.drawBitmap(drawBitmap.getDrawBitmap(),0,0,myPaint.getPaint());
                    canvas.drawBitmap(bitmapCache, 0, 0, myPaint.getPaint());
                    canvas.drawCircle(1200,100,50,myPaint.getPaint());
                }

            } catch (Exception e) {
                Log.e(TAG, "run: e ", e);
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

        }
    }

    private void initView() {

        isSetBG = false;
        surfaceHolder = getHolder();
        canvas = surfaceHolder.lockCanvas();
        drawBitmap = new DrawBitmap();
        drawBitmap.setDrawBitmap(Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888));
        bgBitmap = new BGBitmap();
        bgBitmap.setBgBitmap(Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888));
        myPaint = new MyPaint();
        drawControl = new DrawImpl();
        pathMap = new HashMap<>();
        doublePath = new DoublePath();
        doublePathList = new DoublePathList();
        drawControl.setPaint(myPaint);
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    /**
     * 捕获手指运动轨迹
     * 使用pathMap记录多指触控中各个手指id
     * 记录对应的doublePath、doublePathList、drawBitmap
     * 记录对应的myPaintList
     *
     * @param event
     */
    private void recordPath(MotionEvent event) {
        switch (event.getAction() & event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                drawControl.setDoublePath(event, doublePath, doublePathList, drawBitmap, pathMap);
                break;
            case MotionEvent.ACTION_MOVE:
                drawControl.changeDoublePath(event, doublePath, doublePathList, drawBitmap, pathMap, halfBessel, myPaint);
                break;

            case MotionEvent.ACTION_POINTER_UP:
                drawControl.saveDoublePath(event, doublePath, doublePathList, pathMap);
                drawControl.removeDoublePath(event, pathMap);
                break;
            case MotionEvent.ACTION_UP:
                drawControl.saveDoublePath(event, doublePath, doublePathList, pathMap);
                drawControl.removeDoublePath(event, pathMap);
                drawControl.clearPathMap(pathMap);
                break;
        }
    }

}
