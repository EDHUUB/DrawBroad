package com.xpw.drawbroad.pojo;

import java.nio.file.Path;

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
}
