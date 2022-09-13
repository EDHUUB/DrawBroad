package com.xpw.drawbroad.pojo;

import android.graphics.Path;
import android.view.MotionEvent;


import lombok.Data;

/**
 * @author sunkai
 * @date 2022/9/9 9:46
 * @read happy
 */
@Data
public class DoublePath {

    private int id;
    private Path actualPath;
    private Path slowPath;
    private float actualX;
    private float actualY;
    private float slowX;
    private float slowY;

    public void initPath(int id, MotionEvent event) {
        this.id = id;
        this.slowX = event.getX(id);
        this.actualX = event.getX(id);
        this.slowY = event.getY(id);
        this.actualY = event.getY(id);
        this.slowPath = new Path();
        this.slowPath.moveTo(slowX, slowY);
        this.actualPath = new Path();
        this.actualPath.moveTo(actualX, actualY);
    }
}
