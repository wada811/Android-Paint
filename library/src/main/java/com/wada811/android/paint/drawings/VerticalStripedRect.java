package com.wada811.android.paint.drawings;

import android.graphics.Canvas;
import android.graphics.RectF;
import com.wada811.android.paint.tools.Brush;

public class VerticalStripedRect extends Rect {

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        float stripePadding = 15.0f;
        drawVerticalStripes(canvas, rect, stripePadding);
    }

    private void drawVerticalStripes(Canvas canvas, RectF rect, float stripePadding){
        for(float stripeOffset = 0.0f, rectWidth = rect.width(); stripeOffset <= rectWidth; stripeOffset += stripePadding){
            canvas.drawLine(
                rect.left + stripeOffset,
                rect.top,
                rect.left + stripeOffset,
                rect.bottom,
                Brush.getPen()
            );
        }
    }
}
