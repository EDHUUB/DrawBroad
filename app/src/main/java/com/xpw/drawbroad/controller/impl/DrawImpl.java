package com.xpw.drawbroad.controller.impl;

import android.graphics.BitmapFactory;

import com.xpw.drawbroad.controller.Draw;
import com.xpw.drawbroad.pojo.BGBitmap;

/**
 * @author sunkai
 * @date 2022/9/9 11:26
 * @read happy
 */
public class DrawImpl implements Draw {
    @Override
    public void setBG(String path, int w, int h) {

        BGBitmap bgBitmap = new BGBitmap();
        bgBitmap.setBgBitmap(bgBitmap.convertToBitmap(path,w,h));

    }

    @Override
    public void onDraw() {

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
}
