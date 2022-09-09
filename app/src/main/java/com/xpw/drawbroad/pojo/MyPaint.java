package com.xpw.drawbroad.pojo;

import android.graphics.Paint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author sunkai
 * @date 2022/9/9 11:42
 * @read happy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MyPaint extends Paint {

    private int choseColor;
    private int choseStrokeWidth;
    private Paint.Style choseStyle;
    private String paintType;
}
