package com.wada811.android.paint.drawings;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.wada811.android.paint.tools.Brush;

/**
 * Some dots.
 */
public class Dots extends Drawing {

    private Paint paint;

    public Dots(){
        paint = new Paint(Brush.getPen());
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawCircle(stopX, stopY, Brush.getPen().getStrokeWidth() + 1, paint);
    }

    @Override
    public void fingerDown(float x, float y, Canvas canvas){
        canvas.drawCircle(x, y, Brush.getPen().getStrokeWidth() + 1, paint);
    }

    @Override
    public void fingerMove(float x, float y, Canvas canvas){
        canvas.drawCircle(x, y, Brush.getPen().getStrokeWidth() + 1, paint);
    }
}