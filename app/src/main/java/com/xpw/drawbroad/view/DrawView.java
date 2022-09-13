package com.xpw.drawbroad.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
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

/**
 * @author sunkai
 * @date 2022/9/9 9:46
 * @read happy
 */
public class DrawView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private static final String TAG = "SurfaceView";
    private BGBitmap bgBitmap;
    private Resources resources;
    private MyPaint myPaint = new MyPaint();
    private Draw drawControl = new DrawImpl();
    private Canvas canvas;
    private DoublePath doublePath;
    private DoublePathList doublePathList;
    private DrawBitmap drawBitmap;

    private Rect mSrcRect = new Rect(0, 0, this.getWidth(), this.getHeight());


    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        resources = context.getResources();

    }

    /**
     * 初始化画板背景、画笔
     *
     * @param holder
     */
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

        bgBitmap = new BGBitmap();
        canvas = getHolder().lockCanvas();
        bgBitmap.setBG(resources, R.drawable.bg, this.getWidth(), this.getHeight());
        canvas.drawBitmap(bgBitmap.getBgBitmap(), 0, 0, myPaint);
        getHolder().unlockCanvasAndPost(canvas);

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        drawControl.setPaint(myPaint);

        switch ()
        return true;
    }
}
