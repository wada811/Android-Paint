package com.wada811.android.paint.drawings;

import android.graphics.Canvas;
import android.graphics.RectF;
import com.wada811.android.paint.tools.Brush;

public class Oval extends Drawing {

    protected RectF rect;

    public Oval(){
        rect = new RectF();
    }

    @Override
    public void draw(Canvas canvas){
        rect.set(startX, startY, stopX, stopY);
        rect.sort();
        canvas.drawOval(rect, Brush.getPen());
    }
}
