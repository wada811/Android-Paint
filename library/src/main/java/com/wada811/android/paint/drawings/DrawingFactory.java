package com.wada811.android.paint.drawings;

/**
 * Factory class, used to generate drawing.
 */
public class DrawingFactory {

    private Drawing drawing = null;

    /**
     * @param id The id of the drawing.
     *
     * @return The Drawing instance with the id.
     */
    public Drawing createDrawing(DrawingTool id){
        switch(id){
            case DRAWING_PATHLINE:
                drawing = new PathLine();
                break;
            case DRAWING_STRAIGHTLINE:
                drawing = new StraightLine();
                break;
            case DRAWING_RECT:
                drawing = new Rect();
                break;
            case DRAWING_VERTICAL_STRIPED_RECT:
                drawing = new VerticalStripedRect();
                break;
            case DRAWING_HORIZONTAL_STRIPED_RECT:
                drawing = new HorizontalStripedRect();
                break;
            case DRAWING_SLANTED_STRIPED_RECT:
                drawing = new SlantedStripedRect();
                break;
            case DRAWING_OVAL:
                drawing = new Oval();
                break;
            case DRAWING_CIRCLE:
                drawing = new Circle();
                break;
            case DRAWING_DOTS:
                drawing = new Dots();
                break;
            case DRAWING_ERASER:
                drawing = new Eraser();
                break;
        }

        return drawing;
    }
}