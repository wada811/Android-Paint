package com.wada811.android.paint.drawings;

import android.graphics.Canvas;

/**
 * Abstract shape. All the shapes in this application extends this class.
 */
public abstract class Drawing {

    protected float startX;
    protected float startY;
    protected float stopX;
    protected float stopY;

    public void reset(){
        startX = 0;
        startY = 0;
        stopX = 0;
        stopY = 0;
    }

    /**
     * A abstract method, that all the shapes must implement.
     *
     * @param canvas A canvas to draw on.
     */
    public abstract void draw(Canvas canvas);

    public void fingerDown(float x, float y, Canvas canvas){
        reset();
        startX = x;
        startY = y;
    }

    public void fingerMove(float x, float y, Canvas canvas){
        stopX = x;
        stopY = y;
    }

    public void fingerUp(float x, float y, Canvas canvas){
        stopX = x;
        stopY = y;

        draw(canvas);
        reset();
    }
}
