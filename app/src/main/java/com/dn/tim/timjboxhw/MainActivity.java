package com.dn.tim.timjboxhw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.dn.tim.timjboxhw.widget.MainGameView;

public class MainActivity extends com.lythonliu.LinkAppCompatActivity {
    @Override
    public String getAppName(){
        return BuildConfig.APP_NAME;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new MainGameView(this));
        Toast.makeText(this, "请点击屏幕，可以有小囧掉落", 2000).show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        return super.onTouchEvent(event);
    }
}
