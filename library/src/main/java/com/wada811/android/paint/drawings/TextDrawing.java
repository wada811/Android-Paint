package com.wada811.android.paint.drawings;

import android.graphics.Canvas;
import android.graphics.Paint;

public class TextDrawing extends Drawing {

    private String text;
    private float x;
    private float y;
    private Paint paint;

    public TextDrawing(String text, float x, float y, Paint paint){
        this.text = text;
        this.x = x;
        this.y = y;
        this.paint = paint;
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawText(text, x, y, paint);
    }

    @Override
    public void fingerDown(float x, float y, Canvas canvas){
        super.fingerDown(x, y, canvas);
    }

    @Override
    public void fingerMove(float x, float y, Canvas canvas){
        super.fingerMove(x, y, canvas);
    }

    @Override
    public void fingerUp(float x, float y, Canvas canvas){
        super.fingerUp(x, y, canvas);
    }
}
