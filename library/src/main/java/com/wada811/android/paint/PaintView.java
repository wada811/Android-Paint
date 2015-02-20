package com.wada811.android.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.wada811.android.paint.drawings.Drawing;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This is our main View class.
 */
public class PaintView extends View {

    private Context context;
    private Bitmap bitmap;
    private Canvas canvas;
    private boolean isMoving;
    private Drawing drawing;
    private float penX, penY;
    private int bgColor;

    public PaintView(Context context){
        super(context);
        this.context = context;
    }

    public PaintView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        // Draw the bitmap
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.DITHER_FLAG));

        // Call the drawing's draw() method.
        if(drawing != null && isMoving){
            drawing.draw(canvas);
        }

        //if(!(drawing instanceof Eraser)){
        //    // Drawing a brush icon in this view.
        //    Bitmap pen = BitmapFactory.decodeResource(
        //        getResources(), R.drawable.pen
        //    );
        //    canvas.drawBitmap(
        //        pen, penX, penY - pen.getHeight(), new Paint(Paint.DITHER_FLAG)
        //    );
        //}
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                fingerDown(x, y);
                reDraw();
                break;
            case MotionEvent.ACTION_MOVE:
                fingerMove(x, y);
                reDraw();
                break;
            case MotionEvent.ACTION_UP:
                fingerUp(x, y);
                reDraw();
                break;
        }

        return true;
    }

    /**
     * Refresh the view, the view's onDraw() method will be called.
     */
    private void reDraw(){
        invalidate();
    }

    /**
     * Handles the action of finger up.
     *
     * @param x coordinate
     * @param y coordinate
     */
    private void fingerUp(float x, float y){
        penX = 0;
        penY = 0;

        drawing.fingerUp(x, y, canvas);
        isMoving = false;
    }

    /**
     * Handles the action of finger Move.
     *
     * @param x coordinate
     * @param y coordinate
     */
    private void fingerMove(float x, float y){
        penX = x;
        penY = y;
        isMoving = true;

        drawing.fingerMove(x, y, canvas);
    }

    /**
     * Handles the action of finger down.
     *
     * @param x coordinate
     * @param y coordinate
     */
    private void fingerDown(float x, float y){
        isMoving = false;
        drawing.fingerDown(x, y, canvas);
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
        canvas = new Canvas(bitmap);
        // Set the background color
        canvas.drawColor(getResources().getColor(R.color.transparent));

        isMoving = false;
    }

    public Bitmap getBitmap(){
        return bitmap.copy(Config.ARGB_8888, true);
    }

    /**
     * Set the shape that is drawing.
     *
     * @param drawing Which shape to drawing current.
     */
    public void setDrawing(Drawing drawing){
        this.drawing = drawing;
    }

    /**
     * Clear the drawing in the canvas.
     */
    public void clearCanvas(){
        canvas.drawColor(getResources().getColor(R.color.transparent));
        reDraw();
    }

    public void changeBgColor(int color){
        canvas.drawColor(color);
        reDraw();
    }

}
