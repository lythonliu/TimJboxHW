/*
 * Created by 动脑科技-Tim on 17-7-21 下午6:10
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 17-7-21 下午6:10
 */

package com.dn.tim.timjboxhw.widget;


import android.graphics.Canvas;
import android.graphics.Paint;

public class MyRect {

    //矩形图形的位置、宽高与角度
    private float x, y, w, h, angle;

    //矩形图形的初始化
    public MyRect(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void drawRect(Canvas canvas, Paint paint) {
        canvas.save();
        //绕物体中心选折一定角度
        canvas.rotate(angle, x+w/2, y+h/2);
        canvas.drawRect(x, y, x+w, y+h, paint);
        canvas.restore();

    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getW() {
        return w;
    }



    public float getH() {
        return h;
    }


    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
