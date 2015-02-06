package com.wada811.android.paint.sample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.wada811.android.paint.PaintView;
import com.wada811.android.paint.drawings.Drawing;
import com.wada811.android.paint.drawings.DrawingFactory;
import com.wada811.android.paint.drawings.DrawingTool;
import com.wada811.android.paint.sample.settings.SettingsActivity;
import com.wada811.android.paint.tools.Brush;


public class PaintActivity extends ActionBarActivity implements OnSharedPreferenceChangeListener {

    private static final String TAG = PaintActivity.class.getSimpleName();
    private static final boolean DEBUG = false;

    private PaintView paintView;
    private Drawing drawing;
    private Paint paint;
    private DrawingFactory drawingFactory;
    private boolean isFullScreen;

    // Define a Dialog id
    private static final int DIALOG_WHAT_TO_DRAW = 1;
    private static final int DIALOG_SAVE_IT_OR_NOT = 2;

    public static final int REQUEST_SETTING = 1;

    // A handle to an instance of SharedPreferences
    private SharedPreferences prefs;
    private int mCount;

    private MyHandler mMyHandler;

    private final int TIME_TO_START_OVER_AGAIN = 1000;
    private final int TIME_BEFORE_EXIT = TIME_TO_START_OVER_AGAIN + 500;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        paintView = new PaintView(this);
        setContentView(paintView);

        setDefaultDrawing();

        Brush.getPen().reset();

        // Get the handle to the instance of settings
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        prefs.registerOnSharedPreferenceChangeListener(this);

        isFullScreen = prefs.getBoolean("check_full_screen", false);

        if(isFullScreen){
            makeFullScreen();
        }

        mMyHandler = new MyHandler();
    }

    @Override
    protected void onStart(){
        super.onStart();
        Toast.makeText(
            this, getResources().getString(R.string.tip_touch_to_draw), Toast.LENGTH_SHORT
        ).show();
    }

    private void makeFullScreen(){
        //        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * Set the default drawing
     */
    private void setDefaultDrawing(){
        drawingFactory = new DrawingFactory();
        drawing = drawingFactory.createDrawing(DrawingTool.DRAWING_PATHLINE);
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
                // handleBackKeyDown();
                showDialog(PaintActivity.DIALOG_SAVE_IT_OR_NOT);
                return true;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                paintView.saveBitmap();
                finish();
                return true;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                paintView.clearCanvas();
                return true;
        }
        return false;
    }

    /**
     * Handler back key down event. </br> If back key is pressed once in 1
     * second, exit. Otherwise, save the bitmap before exiting.
     */
    private void handleBackKeyDown(){
        if(mCount > 0){
            mCount = 0;
            paintView.saveBitmap();
        }else{
            mCount++;

            Toast.makeText(
                this, getResources().getString(
                    R.string.tip_press_again_to_save_bitmap_before_leaving
                ), Toast.LENGTH_SHORT
            ).show();

            Message msg_reset = Message.obtain();
            msg_reset.what = MyHandler.MSG_RESET;
            mMyHandler.sendMessageDelayed(msg_reset, TIME_TO_START_OVER_AGAIN);

            Message msg_exit = Message.obtain();
            msg_exit.what = MyHandler.MSG_EXIT;
            mMyHandler.sendMessageDelayed(msg_exit, TIME_BEFORE_EXIT);

        }

        // showDialog(DIALOG_WHAT_TO_DRAW);
    }

    private class MyHandler extends Handler {

        public static final int MSG_RESET = 0;
        public static final int MSG_EXIT = 1;

        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case MSG_RESET:
                    mCount = 0;
                    break;
                case MSG_EXIT:
                    finish();
                    break;
                default:
                    break;
            }
        }
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
                    paintView.saveBitmap();
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
            PaintActivity.this,
            getResources().getString(R.string.tip_current_is_drawing) + drawingTool,
            Toast.LENGTH_SHORT
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
                paintView.saveBitmap();
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

    public void onSharedPreferenceChanged(
        SharedPreferences sharedPreferences, String key
    ){
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
