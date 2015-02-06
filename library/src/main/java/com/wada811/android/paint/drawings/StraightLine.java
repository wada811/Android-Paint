package com.wada811.android.paint.drawings;

import android.graphics.Canvas;
import com.wada811.android.paint.tools.Brush;

/**
 * A straight line.
 */
public class StraightLine extends Drawing {

    @Override
    public void draw(Canvas canvas){
        canvas.drawLine(startX, startY, stopX, stopY, Brush.getPen());
    }
}
