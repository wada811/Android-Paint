package com.wada811.android.paint.drawings;

import android.graphics.Canvas;
import android.graphics.RectF;
import com.wada811.android.paint.tools.Brush;

public class HorizontalStripedRect extends Rect {

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        float stripePadding = 15.0f;
        drawHorizontalStripes(canvas, rect, stripePadding);
    }

    private void drawHorizontalStripes(Canvas canvas, RectF rect, float stripePadding){
        for(float stripeOffset = 0.0f, rectHeight = rect.height(); stripeOffset <= rectHeight; stripeOffset += stripePadding){
            canvas.drawLine(
                rect.left,
                rect.top + stripeOffset,
                rect.right,
                rect.top + stripeOffset,
                Brush.getPen()
            );
        }
    }
}
