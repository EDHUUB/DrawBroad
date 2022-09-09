package com.xpw.drawbroad.controller;

import android.graphics.Paint;

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

    public void setBG(String path, int w, int h);

    public void onDraw();

    public void followPoint();

    public void clean();

    public void clip();


}
