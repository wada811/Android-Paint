package com.wada811.android.paint.drawings;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import com.wada811.android.paint.tools.Brush;

/**
 * An earser, drawing the track line with the color of the bitmap's background
 * color.
 */
public class Eraser extends Drawing {

    private Path path;
    private static final float TOUCH_TOLERANCE = 4;
    private Paint eraser;

    public Eraser(){
        path = new Path();
        eraser = new Paint(Brush.getPen());
        eraser.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawPath(path, eraser);
    }

    @Override
    public void fingerDown(float x, float y, Canvas canvas){
        super.fingerDown(x, y, canvas);
        path.reset();
        path.moveTo(x, y);
    }

    @Override
    public void fingerMove(float x, float y, Canvas canvas){
        float dx = Math.abs(x - startX);
        float dy = Math.abs(y - startY);
        if(dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE){
            path.quadTo(startX, startY, (x + startX) / 2, (y + startY) / 2);
            startX = x;
            startY = y;
        }
        draw(canvas);
    }

    @Override
    public void fingerUp(float x, float y, Canvas canvas){
        path.lineTo(startX, startY);
        super.fingerUp(x, y, canvas);
    }
}
