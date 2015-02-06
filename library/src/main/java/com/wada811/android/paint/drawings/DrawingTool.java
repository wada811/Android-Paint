package com.wada811.android.paint.drawings;

import com.wada811.android.paint.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Defines drawing tools.
 */
public enum DrawingTool {
    //
    DRAWING_PATHLINE(R.string.drawing_pathline),
    //
    DRAWING_STRAIGHTLINE(R.string.drawing_straightline),
    //
    DRAWING_RECT(R.string.drawing_rect),
    //
    DRAWING_VERTICAL_STRIPED_RECT(R.string.drawing_vertical_striped_rect),
    //
    DRAWING_HORIZONTAL_STRIPED_RECT(R.string.drawing_horizontal_striped_rect),
    //
    DRAWING_SLANTED_STRIPED_RECT(R.string.drawing_slanted_striped_rect),
    //
    DRAWING_OVAL(R.string.drawing_oval),
    //
    DRAWING_CIRCLE(R.string.drawing_circle),
    //
    DRAWING_DOTS(R.string.drawing_dots),
    //
    DRAWING_ERASER(R.string.drawing_eraser),
    //
    ;

    public int nameId;

    DrawingTool(int nameId){
        this.nameId = nameId;
    }

    public static String[] names(){
        DrawingTool[] drawingTools = DrawingTool.values();
        List<String> nameList = new ArrayList<>(drawingTools.length);
        for(DrawingTool drawingTool : drawingTools){
            nameList.add(drawingTool.name());
        }
        return nameList.toArray(new String[drawingTools.length]);
    }
}
