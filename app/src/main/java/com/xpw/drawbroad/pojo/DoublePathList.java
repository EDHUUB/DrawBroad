package com.xpw.drawbroad.pojo;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sunkai
 * @date 2022/9/9 11:43
 * @read happy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoublePathList {

    private List<DoublePath> doublePathList = new ArrayList<>();
}
