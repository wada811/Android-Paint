package com.wada811.android.paint.drawings;

import android.graphics.Canvas;
import android.graphics.RectF;
import com.wada811.android.paint.tools.Brush;

public class SlantedStripedRect extends Rect {

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        float stripePadding = 15.0f;
        drawSlantedStripes(canvas, rect, stripePadding);
    }

    private void drawSlantedStripes(Canvas canvas, RectF rect, float stripePadding){
        float stripeOffset = 0.0f;
        int stripeVerticallyCount = (int)(rect.width() / stripePadding);
        int stripeHorizontallyCount = (int)(rect.height() / stripePadding);
        int stripeMinCount = Math.min(stripeVerticallyCount, stripeHorizontallyCount);
        int stripeMaxCount = Math.max(stripeVerticallyCount, stripeHorizontallyCount);
        float half_circumferential = rect.width() + rect.height();
        for(int stripeIndex = 0; stripeOffset <= half_circumferential; stripeIndex++, stripeOffset += stripePadding){
            if(stripeIndex <= stripeMinCount){
                canvas.drawLine(
                    rect.left,
                    rect.top + stripeOffset,
                    rect.left + stripeOffset,
                    rect.top,
                    Brush.getPen()
                );
            }else if(stripeIndex <= stripeMaxCount){
                if(stripeMinCount == stripeVerticallyCount){ // 縦長長方形
                    canvas.drawLine(
                        rect.left,
                        rect.top + stripeOffset,
                        rect.right,
                        rect.top + stripeOffset - rect.width(),
                        Brush.getPen()
                    );
                }else{ // 横長長方形
                    canvas.drawLine(
                        rect.left + stripeOffset - rect.height(),
                        rect.bottom,
                        rect.left + stripeOffset,
                        rect.top,
                        Brush.getPen()
                    );
                }
            }else{
                if(stripeMinCount == stripeVerticallyCount){ // 縦長長方形
                    canvas.drawLine(
                        rect.left + stripeOffset - rect.height(),
                        rect.bottom,
                        rect.right,
                        rect.top + stripeOffset - rect.width(),
                        Brush.getPen()
                    );
                }else{ // 横長長方形
                    canvas.drawLine(
                        rect.left + stripeOffset - rect.height(),
                        rect.bottom,
                        rect.right,
                        rect.top + stripeOffset - rect.width(),
                        Brush.getPen()
                    );
                }
            }
        }
    }
}
