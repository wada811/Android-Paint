package com.wada811.android.paint.sample.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.wada811.android.paint.tools.Brush;

/**
 * Use this class to set the width of the brush.
 */
public class SeekBarDialog extends AlertDialog {

    private static final int MAX_WIDTH = 20;
    private TextView textView = null;
    private SeekBar seekBar = null;

    public SeekBarDialog(Context context){
        this(context, android.R.style.Theme_Panel);
    }

    public SeekBarDialog(Context context, int theme){
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        seekBar = new SeekBar(getContext());
        setSeekBar();

        setView(seekBar);

        super.onCreate(savedInstanceState);
    }

    private void setSeekBar(){
        seekBar.setMax(MAX_WIDTH);
        seekBar.setProgress((int)Brush.getPen().getStrokeWidth());
        seekBar.setOnSeekBarChangeListener(seekBarListener);
    }

    OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener() {
        public void onStopTrackingTouch(SeekBar seekBar){
        }

        public void onStartTrackingTouch(SeekBar seekBar){
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
            Brush.getPen().setStrokeWidth(progress);
            setTitle(Brush.getPen().getStrokeWidth() + " pixel");
        }
    };

    /**
     * @param max the max width of the brush
     */
    public void setSeekBarMax(int max){
        seekBar.setMax(max);
    }

    public int getSeekBarMax(){
        return seekBar.getMax();
    }
}
