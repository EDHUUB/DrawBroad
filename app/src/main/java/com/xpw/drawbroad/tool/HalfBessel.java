package com.xpw.drawbroad.tool;


import android.view.MotionEvent;

import com.xpw.drawbroad.pojo.DoublePath;
import com.xpw.drawbroad.pojo.DrawBitmap;
import com.xpw.drawbroad.pojo.MyPaint;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HalfBessel {

    private static final String TAG = "HalfBessel";
    private Map<Integer, DoublePath> pathMap;
    private MotionEvent event;
    private DrawBitmap drawBitmap;
    private MyPaint myPaint;
    private int with;
    private int height;


    public void fixPath(MotionEvent event, DoublePath doublePath,int i) {
        if (doublePath.getSlowX() != doublePath.getActualX()
                || doublePath.getSlowY() != doublePath.getActualY()) {
            doublePath.getSlowPath().quadTo(doublePath.getActualX(), doublePath.getActualY(),
                    (event.getX(i) + doublePath.getActualX()) / 2, (event.getY(i) + doublePath.getActualY()) / 2);
            doublePath.setSlowX(event.getX(i));
            doublePath.setSlowY(event.getY(i));
        }
        doublePath.setActualX(event.getX(i));
        doublePath.setActualY(event.getY(i));
        doublePath.getActualPath().moveTo(doublePath.getActualX(), doublePath.getActualY());
    }


}
