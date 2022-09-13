package com.xpw.drawbroad.tool;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.xpw.drawbroad.pojo.DoublePath;
import com.xpw.drawbroad.pojo.MyPaint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HalfBessel {

    private int id;
    private DoublePath doublePath;
    private Canvas canvas;
    private MotionEvent event;
    private MyPaint myPaint;

    /**
     *
     */
    public void draw() {

        doublePath.setActualX(event.getX(id));
        doublePath.setActualY(event.getY(id));
        doublePath.getActualPath().moveTo(event.getX(id), event.getY(id));

//        if (doublePath.getActualX() == doublePath.getSlowX() && doublePath.getActualY() == doublePath.getSlowY()) {
//            doublePath.setActualX(event.getX(id));
//            doublePath.setActualY(event.getY(id));
//            doublePath.getActualPath().moveTo(event.getX(id), event.getY(id));
//        } else {
//            doublePath.getSlowPath().quadTo(doublePath.getActualX(), doublePath.getActualY(),
//                    (doublePath.getActualX() + event.getX(id)) / 2, (doublePath.getActualY() + event.getY(id)) / 2);
//        }
//        canvas.drawPath(doublePath.getActualPath(), myPaint);
    }

}
