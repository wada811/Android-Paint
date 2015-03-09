package com.wada811.android.paint.sample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.Toast;
import com.wada811.android.paint.PaintView;
import com.wada811.android.paint.drawings.Drawing;
import com.wada811.android.paint.drawings.DrawingFactory;
import com.wada811.android.paint.drawings.DrawingTool;
import com.wada811.android.paint.drawings.NullDrawing;
import com.wada811.android.paint.drawings.TextDrawing;
import com.wada811.android.paint.sample.settings.SettingsActivity;
import com.wada811.android.paint.tools.Brush;

public class PaintActivity extends ActionBarActivity implements OnSharedPreferenceChangeListener, OnTouchListener {

    private FrameLayout container;
    private PaintView paintView;
    private Drawing drawing;
    private DrawingFactory drawingFactory;

    // Define a Dialog id
    private static final int DIALOG_WHAT_TO_DRAW = 1;
    private static final int DIALOG_SAVE_IT_OR_NOT = 2;

    public static final int REQUEST_SETTING = 1;

    // A handle to an instance of SharedPreferences
    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);

        container = (FrameLayout)findViewById(R.id.container);
        paintView = new PaintView(this);
        paintView.setBitmap(Bitmap.createBitmap(640, 640, Config.ARGB_8888));
        paintView.setOnTouchListener(this);
        container.addView(paintView);

        setDefaultDrawing();

        Brush.getPen().reset();

        // Get the handle to the instance of settings
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        prefs.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event){
        if(event.getAction() != MotionEvent.ACTION_DOWN){
            return false;
        }
        EditText editText = (EditText)container.findViewWithTag("EditText");
        if(editText == null){
            editText = new EditText(this);
            editText.setLines(1);
            editText.setMaxLines(1);
            editText.setMinWidth(300);
            editText.setTag("EditText");
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            float x = event.getX();
            float y = event.getY();
            params.topMargin = (int)y;
            params.leftMargin = (int)x;
            container.addView(editText, params);
            editText.setOnFocusChangeListener(
                new OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus){
                        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_FORCED);
                    }
                }
            );
            editText.requestFocus();
            return true;
        }else{
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            editText.setOnFocusChangeListener(null);
            editText.clearFocus();
            Paint paint = new Paint();
            paint.setTextSize(editText.getTextSize());
            float dx = editText.getX() + editText.getPaddingLeft();
            float dy = editText.getY() + editText.getPaddingTop() - paint.getFontMetrics().top;
            TextDrawing textDrawing = new TextDrawing(editText.getText().toString(), dx, dy, paint);
            paintView.setDrawing(textDrawing);
            container.removeView(editText);
            paintView.onTouchEvent(event);
            return false;
        }
    }

    /**
     * Set the default drawing
     */
    private void setDefaultDrawing(){
        drawingFactory = new DrawingFactory();
        drawing = drawingFactory.createDrawing(DrawingTool.DRAWING_PATHLINE);
        drawing = new NullDrawing();
        paintView.setDrawing(drawing);
    }

    /**
     * When the up key is pressed, we pop up a dialog for the use to select
     * shapes.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        switch(keyCode){
            case KeyEvent.KEYCODE_DPAD_UP:
                showDialog(PaintActivity.DIALOG_WHAT_TO_DRAW);
                return true;
            case KeyEvent.KEYCODE_BACK:
                showDialog(PaintActivity.DIALOG_SAVE_IT_OR_NOT);
                return true;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                //TODO
                //paintView.saveBitmap();
                finish();
                return true;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                paintView.clearCanvas();
                return true;
        }
        return false;
    }

    /**
     * @return A dialog contents all the shapes available.
     */
    private Dialog showWhatToDrawDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(
            PaintActivity.this
        );

        builder.setTitle(
            getResources().getString(
                R.string.dialog_title_what_to_draw
            )
        );
        builder.setSingleChoiceItems(
            DrawingTool.names(), 0, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which){
                    setWhatToDraw(which);
                    dialog.dismiss();
                }
            }
        );

        builder.setNegativeButton(
            R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton){
                    dialog.dismiss();
                }
            }
        );

        AlertDialog alert = builder.create();
        return alert;
    }

    /**
     * @return A dialog contents all the shapes available.
     */
    private Dialog showSaveItOrNotDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(
            PaintActivity.this
        );

        builder.setTitle(
            getResources().getString(
                R.string.dialog_title_save_it_or_not
            )
        );

        builder.setPositiveButton(
            R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which){
                    //TODO
                    //paintView.saveBitmap();
                    finish();
                }
            }
        );

        builder.setNegativeButton(
            R.string.alert_dialog_no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton){
                    finish();
                }
            }
        );

        AlertDialog alert = builder.create();
        return alert;
    }

    @Override
    protected Dialog onCreateDialog(int id){
        switch(id){
            case PaintActivity.DIALOG_WHAT_TO_DRAW:
                return showWhatToDrawDialog();
            case PaintActivity.DIALOG_SAVE_IT_OR_NOT:
                return showSaveItOrNotDialog();
        }
        return null;
    }

    @Override
    protected void onDestroy(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("pen_style_key", false);
        editor.commit();
        super.onDestroy();
    }

    /**
     * In this method, we call the factory method to create an instance of
     * drawing.
     *
     * @param which The drawing's id, identify the current drawing.
     */
    private void setWhatToDraw(int which){
        DrawingTool drawingTool = DrawingTool.values()[which];
        // TODO drawingTool's name
        Toast.makeText(
            PaintActivity.this, getResources().getString(R.string.tip_current_is_drawing) + drawingTool, Toast.LENGTH_SHORT
        ).show();

        drawing = drawingFactory.createDrawing(drawingTool);

        if(drawing != null){
            paintView.setDrawing(drawing);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_paintpadactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_id_what_to_draw:
                showDialog(PaintActivity.DIALOG_WHAT_TO_DRAW);
                break;
            case R.id.menu_id_setting:
                startSettingsActivity();
                break;
            case R.id.menu_id_save:
                //TODO
                //paintView.saveBitmap();
                break;
            case R.id.menu_id_clear_screen:
                paintView.clearCanvas();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQUEST_SETTING:
                break;
        }
    }

    /**
     * To start the setting Activity, the provide some functions to setting the
     * brush.
     */
    private void startSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, PaintActivity.REQUEST_SETTING);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key){
        if(key.equals("pen_style_key")){
            setPenStyle(sharedPreferences.getBoolean("pen_style_key", false));
        }else if(key.equals("pen_antialias_key")){
            Brush.getPen().setAntiAlias(
                sharedPreferences.getBoolean(key, false)
            );
        }
    }

    /**
     * Set the property of the shapes.
     *
     * @param flag Fill the shapes or not
     */
    public void setPenStyle(boolean flag){
        if(flag){
            Brush.getPen().setStyle(Style.FILL);
        }else{
            Brush.getPen().setStyle(Style.STROKE);
        }
    }
}
